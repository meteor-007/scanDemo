package cnvd.weakpwdscan;

import cnvd.weakpwdscan.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * 弱口令扫描工具类
 *
 * @author zml
 * @date 2018-5-25
 */
public class WeakPwdScanUtil {

    private static final Logger log = LoggerFactory.getLogger(WeakPwdScanUtil.class);

    /**
     * 保存MySQL的数据字典信息
     */
    public static Set<String> mysqlDictionarySet = null;

    /**
     * 保存数据字典信息，直接从文件中读取的数据——SQL Server、Oracle、Postgres共用
     */
    public static Set<String> dictionarySet = null;

    /**
     * 获取数据字典的工具方法
     *
     * @param dbType 数据库类型
     */
    public static void readDictionaryFile(String dbType) {
        String fileUrl = "D:\\dictionary\\dictionary.txt";
        try {
            FileReader fileReader = new FileReader(fileUrl);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String string = bufferedReader.readLine();
            while (string != null) {
                if (DatabaseEnum.MYSQL.getKey().equals(dbType)) {
                    // MySQL 使用统一的加密方式，因此从文件中获取数据字典后对其进行加密保存
                    byte[] strings = encodePwd("SHA1", string.getBytes());
                    strings = encodePwd("SHA1", strings);
                    string = "*" + byte2HexString(strings);
                    mysqlDictionarySet.add(string);
                }
                if (DatabaseEnum.SQL_SERVER.getKey().equals(dbType)||
                        DatabaseEnum.ORACLE.getKey().equals(dbType)||
                        DatabaseEnum.POSTGRES.getKey().equals(dbType)) {
                    // 直接保存从文件中获取的数据
                    dictionarySet.add(string);
                }
                string = bufferedReader.readLine();
            }
        } catch (IOException e) {
            log.error("获取{}类型数据库的数据字典时发生未知错误", dbType, e);
        }
    }

    /**
     * 密码加密工具方法
     *
     * @param way 加密方式
     * @param pwd 密码
     * @return
     */
    public static byte[] encodePwd(String way, byte[] pwd) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(way);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.reset();
        md.update(pwd);
        byte[] b = md.digest();
        return b;
    }
    /**
     * 将byte数组转为16进制字符串
     *
     * @param strings 待转换的byte数组
     * @return 16进制字符串
     */
    public static String byte2HexString(byte[] strings) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            stringBuffer.append(Integer.toHexString((strings[i] & 0x000000FF) | 0xFFFFFF00).substring(6));
        }
        return stringBuffer.toString().toUpperCase();
    }

    /**
     * SQL Server 加密方法
     *
     * @param param 加密参数
     * @param string 待加密的字符串
     */
    public static byte[] sqlServerEncodePwd(String param, String string) {
        byte[] stringByte = new byte[0];
        try {
            stringByte = string.getBytes("UTF-16LE");
        } catch (UnsupportedEncodingException e) {
            log.error("对{}字符串进行加密时发生未知错误", string, e);
        }
        byte[] var4 = pwdToByte(param);
        byte[] var2 = new byte[stringByte.length + var4.length];
        System.arraycopy(stringByte, 0, var2, 0, stringByte.length);
        System.arraycopy(var4, 0, var2, stringByte.length, var4.length);
        return encodePwd("SHA-1", var2);
    }

    /**
     * 将数据库中的16进制密码转化成字符数组
     *
     * @param paramString
     * @return
     */
    public static byte[] pwdToByte(String paramString) {
        if (paramString == null) {
            return null;
        }
        int i = paramString.length();

        if (i % 2 == 1) {
            return null;
        }
        byte[] arrayOfByte = new byte[i /= 2];
        for (int j = 0; j != i; j++) {
            int k = 0;
            try {
                k = Integer.parseInt(paramString.substring(j << 1, (j << 1) + 2), 16);
            } catch (Exception localException2) {
                localException2.printStackTrace();
            }
            if (k >= 128) {
                k -= 256;
            }
            arrayOfByte[j] = ((byte) k);
        }
        return arrayOfByte;
    }
}
