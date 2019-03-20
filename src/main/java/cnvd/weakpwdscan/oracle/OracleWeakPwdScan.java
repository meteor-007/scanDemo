package cnvd.weakpwdscan.oracle;

import cnvd.weakpwdscan.DatabaseConfig;
import cnvd.weakpwdscan.SpringUtils;
import cnvd.weakpwdscan.SysUtils;
import cnvd.weakpwdscan.WeakPwdScanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

/**
 * Oracle弱口令扫描类
 *
 * @author zml
 * @date 2018-5-25
 */
public class OracleWeakPwdScan {
    private static final Logger log = LoggerFactory.getLogger(OracleWeakPwdScan.class);

    private static final byte[] a = {1, 35, 69, 103, -119, -85, -51, -17};// oracle
    // DES固定key
    private static SecretKey b = new SecretKeySpec(a, "DES");
    private static final IvParameterSpec c = new IvParameterSpec(new byte[8]);

    /**
     * Oracle弱口令扫描入口
     * @param
     */
    public static  void main() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        String url="jdbc:oracle:thin:@172.23.1.70:1521:orcl8i";
        String user="System";
        String password="Ceshi123";
        Connection conn= DriverManager.getConnection(url,user,password);
        String sqlText = "SELECT name,password,spare4 FROM sys.user$ WHERE name IN " +
                "(SELECT username FROM dba_users WHERE authentication_type='PASSWORD')";
        Map<String, String> userName2PwdMap = new HashMap<>(16);
        Map<String, String> userName2SpareMap = new HashMap<>(16);
        try ( Statement statement=conn.createStatement();) {
            try (ResultSet resultSet = statement.executeQuery(sqlText)) {
                while (resultSet.next()) {
                    String userName = resultSet.getString("name");
                    String userPwd = resultSet.getString("password");
                    String userSpare = resultSet.getString("spare4");
                    if (SpringUtils.hasLength(userPwd)) {
                        userName2PwdMap.put(userName, userPwd);
                    } else {
                        userName2SpareMap.put(userName, userSpare);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("查询{}数据库的用户名密码时发生未知错误", e);
            return;
        }
        // 保存密码较弱的用户名
        List<String> warnNameList = new ArrayList<>();
        for (Map.Entry<String, String> entry : userName2PwdMap.entrySet()) {
            String userName = entry.getKey();
            String pwd = entry.getValue();
            for (String string : WeakPwdScanUtil.dictionarySet) {
                byte[] aa = new byte[0];
                try {
                    aa = (userName + string).toUpperCase().getBytes("utf-16be");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                byte[] paramString1 = Arrays.copyOf(aa, (aa.length + 7) / 8 << 3);
                byte[] paramString2 = new byte[0];
                try {
                    paramString2 = a(paramString1, b);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
                SecretKey sks = new SecretKeySpec(paramString2,paramString2.length - 8, 8, "DES");
                byte[] bb = new byte[0];
                try {
                    bb = a(paramString1, sks);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
                byte[] cc = new byte[8];
                System.arraycopy(bb, bb.length - 8, cc, 0, 8);
                // 数据字典中加密后的密文
                String string2Pwd = WeakPwdScanUtil.byte2HexString(cc);
                // 加密后的密文与数据库中存储的密文一致，证明该密码在我们的数据字典中，则该密码较弱
                if (string2Pwd.equals(pwd.substring(12))) {
                    warnNameList.add(userName);
                    break;
                }
            }
        }
        for (Map.Entry<String, String> entry : userName2SpareMap.entrySet()) {
            String userName = entry.getKey();
            String pwd = entry.getValue();
            if (pwd == null) {
                continue;
            }
            for (String string : WeakPwdScanUtil.dictionarySet) {
                byte[] a = WeakPwdScanUtil.pwdToByte(pwd.substring(2, 42));
                String aPwd = WeakPwdScanUtil.byte2HexString(a);
                String str = pwd.substring(42, pwd.length());// 获得随机值
                byte[] paramString1 = string.getBytes();
                byte[] paramString2 = WeakPwdScanUtil.pwdToByte(str);
                byte[] arrayOfByte = new byte[paramString1.length + paramString2.length];
                System.arraycopy(paramString1, 0, arrayOfByte, 0, paramString1.length);
                System.arraycopy(paramString2, 0, arrayOfByte, paramString1.length,
                        paramString2.length);
                byte[] b = WeakPwdScanUtil.encodePwd("SHA-1", arrayOfByte);
                String bPwd = WeakPwdScanUtil.byte2HexString(b);
                // 加密后的密文与数据库中存储的密文一致，证明该密码在我们的数据字典中，则该密码较弱
                if (aPwd.equals(bPwd.substring(12))) {
                    warnNameList.add(userName);
                    break;
                }
            }
        }
    }

    protected final static byte[] a(byte[] arg1, SecretKey paramKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher localCipher;
        localCipher = Cipher.getInstance("DES/CBC/NoPadding");
        localCipher.init(1, paramKey, c);
        byte[] b = localCipher.doFinal(arg1);
        return b;
    }
}
