package password;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Todo: 弱口令扫描
 * 基于字典：默认字典和自定义字典
 * 基于规则：用户名+数字，用户名+常见年份，与用户名相同，用户名翻转
 * 基于密码长度：包含字母，数字，特殊字符，指定字符组合
 *
 * @author $ czwei
 * @create 2018/12/17
 */
public class TestPassword {

    private static final byte[] a = {1, 35, 69, 103, -119, -85, -51, -17};// oracle
    // DES固定key
    private static SecretKey b = new SecretKeySpec(a, "DES");
    private static final IvParameterSpec c = new IvParameterSpec(new byte[8]);

    private static final byte[] cc = {48, 0, 0, 0, 0, 0, 0, 0};
    private static SecretKey d = new SecretKeySpec(cc, "DES");
    private static final IvParameterSpec f = new IvParameterSpec(new byte[]{32, 33, 35, 36, 37, 38, 39, 40});

    public static void main(String[] args) throws UnsupportedEncodingException,
            InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        /*
         * 1,mysql数据库查询用户与加密的密码 if (this.i.l().startsWith("5.7"))
         * 5.7版本以上 return "select user, plugin,authentication_string from mysql.user where user != '' and user!='mysql.sys'"
         * 5.7以下  return "select user, password from mysql.user where user != ''";
         */
        mysql("Ceshi123"); // 测试mysql密码加密-SHA1
        /*
         * 2,oracle数据库查询用户与加密的密码
         *  PASSWORD为DES加密保存的密码
         *  spare4为sha1加密方式保存的密码
         *  SELECT
         *      u.NAME AS NAME,u.PASSWORD AS PASSWORD,u.spare4 AS spare4,u.astatus AS STATUS,
         *      d.ACCOUNT_STATUS AS statusText,u.ctime AS create_date,u.ltimeAS lock_date,NULL AS lock_time
         *  FROM
         *      sys.user$ u JOIN dba_users d ON
         *      u.NAME = d.username
         *       SELECT name,password,spare4 FROM sys.user$
         *      如果PASSWORD字段没有值或者长度不等于16则采用sha1方式
         */
//        oracleDES("system", ",52");// 测试oracle密码DES加密
//        oracleSHA1("system", "Ceshi123");// 测试oracle密码SHA1加密
        /*
         * 3,sqlserver支持2000 2005 2008版本的密码匹配
         * 2000：select x.name,master.dbo.fn_varbintohexstr(x.password) as password_hash from
         * master.dbo.syslogins s, master.dbo.sysxlogins x where s.isntgroup = 0
         * and s.isntuser = 0 and x.name = s.name
         * 2005and2008:
         * 2012,2014,2017: select name,password_hash, is_disabled from sys.sql_logins where name is not null
         *  需要连数据库调用方法返回匹配结果
         */
        sqlserver("2000", "Ceshi123");// 测试sqlserver2000密码
        sqlserver("2005", "Ceshi123");// 测试sqlserver2005密码
        sqlserver("2008", "Ceshi123");// 测试sqlserver2008密码
        sqlserver("2012", "Ceshi123");// 测试sqlserver2012密码
        sqlserver("2014", "Ceshi123");// 测试sqlserver2014密码
        sqlserver("2016", "Ceshi123");// 测试sqlserver2016密码
        sqlserver("2017", "Ceshi123");// 测试sqlserver2017密码
        /*
         * 4,postgresql数据库查询用户与加密的密码 如果密码字段不是以md5开头则密码没有加密的版本
         * SELECT usename,passwd, valuntil FROM pg_shadow
         */
//        pgsql("postgres", "Ceshi123");// 测试pgsql密码
        /*
         * 5,sysbase数据库查询用户与加密的密码 SHA-256加密
         * select name, len(password) as length,password,status from master..syslogins
         * SELECT name,password FROM master..syslogins
         */
//        sysbase("Ceshi123");// 测试sysbase密码 暂时设置密码为空效果 仅支持15.0.2及以上版本-16.0
        /*
         * 6,达梦数据库查询用户与加密的密码
         * DM7 : select USERNAME, PASSWORD from DBA_USERS
         * DM6 : select ID, NAME AS USERNAME, PASSWORD from SYSLOGINS
         */
//        dmdbms("SYSDBA", "123456");// 测试达梦密码 支持达梦7,达梦6
        /*
         * 7,人大金仓数据库
         * select t.username, t.passwd from sys_catalog.sys_pwdht t
         * where timestamp = (select max(timestamp) from sys_catalog.sys_pwdht
         * where username = t.username)
         */
//        kingbase("root", "Ceshi123");//暂无环境测试
        /*
         * 8,南大通用数据库
         * String var1 =
         * "SELECT TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = \'user\' AND TABLE_SCHEMA IN (\'system\', \'gbase\') ORDER BY CREATE_TIME LIMIT 1"
         * ; String var2 =
         * "select user, password from %SystemSchema%.user where user != \'\'";
         * if(!(var5 = this.b.createStatement().executeQuery(var1)).next()) {
         * throw new SQLException("找不到用户表!"); } else { var2 =
         * var2.replace("%SystemSchema%", var5.getString(1)); var5 =
         * this.b.createStatement().executeQuery(var2);
         */
        gbase("Ceshi123");//暂无环境测试
        /**
         * Todo:
         * @Author:$czwei
         * @Date: 2018/12/26 10:25
         */
        db2();//11.1.3版本-8.2版本
    }

    /**
     * Todo:
     *
     * @Author:$czwei
     * @Date: 2018/12/26 10:26
     */
    private static void db2() {
    }

    /**
     * 南大通用测试密码
     *
     * @throws NoSuchAlgorithmException
     */
    public static String gbase(String pwd) throws NoSuchAlgorithmException {
        String pwdjiami = "";// 南大通用数据库保存的密码
//        byte[] jiamiByte = pwdjiamiToByte(pwdjiami.replaceFirst("[*]", ""));
//        System.out.println("南大通用-SHA1加密后:" + new String(jiamiByte));
        byte[] tmp = jiami("SHA1", pwd.getBytes());
        byte[] jiamipwd = jiami("SHA1", tmp);
        System.out.println("南大通用-SHA1加密后:" + new String(jiamipwd));
        String rs = byteToString(jiamipwd);
        return rs;
    }

    /**
     * 人大金仓测试密码
     *
     * @throws NoSuchAlgorithmException
     */
    public static String kingbase(String name, String pwd)
            throws NoSuchAlgorithmException {
//        String pwdjiami = "";// 人大金仓数据库保存的密码
//        byte[] jiamiByte = pwdjiamiToByte(pwdjiami.substring(3));
//        System.out.println("人大金仓-MD5加密原始:" + new String(jiamiByte));
        byte[] var2 = (pwd + name).getBytes();
        byte[] jiamipwd = jiami("MD5", var2);
//        System.out.println("人大金仓-MD5加密后:" + new String(jiamipwd));
        String rs = byteToString(jiamipwd);
        return rs;
    }

    /**
     * 达梦测试密码加密效果对比
     *
     * @param name,pwd
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String dmdbms(String name, String pwd)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        String pwdjiami = "__0Sk+wY\\R7Du]Ni^&Vp~V|/w{3V~R3~5Bo-Pqs1uNT2&Q91";// 达梦数据库保存的密码
        byte[] jiamiByte = initDmJiami(pwdjiami);
//        System.out.println("dm-加密原始:" + new String(jiamiByte));
        // 加密原始密码
        byte[] pwdByte = b(pwd, name);
        String rs = pwdByte.toString();
//        System.out.println("dm-加密后:" + new String(pwdByte));
        return rs;
    }

    /**
     * 对达梦原始密码进行加密
     *
     * @param pwd
     * @param name
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] b(String pwd, String name) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        int i = 0;
        char[] c = name.toCharArray();
        int a = c.length;
        for (int j = 0; j < a; ++j) {
            char var7 = c[j];
            i += var7;
        }
        int k = i + pwd.length();
        ByteBuffer byteBuffer = ByteBuffer.allocate(60);
        byteBuffer.put(pwd.getBytes()).put(String.valueOf(k).getBytes());
        a = 10 - String.valueOf(k).length();
        while (byteBuffer.position() < 48) {
            for (int var10 = 0; var10 < a; ++var10) {
                byteBuffer.put((byte) 0);
            }
            k += 10;
            byteBuffer.put(String.valueOf(k).getBytes());
        }
        byte[] tmpByte = new byte[48];
        System.arraycopy(byteBuffer.array(), 0, tmpByte, 0, 48);
        byte[] pwdjiami = b(tmpByte, d);
        for (i = 0; i < pwdjiami.length; ++i) {
            pwdjiami[i] = (byte) ((pwdjiami[i] & 0xFF) % 94 + 33);
            if (pwdjiami[i] == 39) {
                pwdjiami[i] = 97;
            }
            if (pwdjiami[i] == 34) {
                pwdjiami[i] = 97;
            }
        }
        return pwdjiami;
    }

    /**
     * 达梦 DES加密
     *
     * @param arrayOfByte
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static byte[] b(byte[] arrayOfByte, Key key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher localCipher;
        localCipher = Cipher.getInstance("DES/CFB/NoPadding");
        localCipher.init(1, key, f);
        byte[] a = localCipher.doFinal(arrayOfByte);
        return a;
    }

    /**
     * 初始化达梦加密的密码转成字符数组
     *
     * @param pwdjiami
     */
    private static byte[] initDmJiami(String pwdjiami) {
        byte[] arrayOfByte = new byte[pwdjiami.length()];
        for (int i = 0; i < pwdjiami.length(); i++) {
            arrayOfByte[i] = ((byte) pwdjiami.charAt(i));
        }
        return arrayOfByte;
    }

    /**
     * sysbase测试密码加密效果对比
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String sysbase(String pwd)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pwdjiami = "c0077852792bd3e354ac68ff7472459d0f76260074c4b7e0ec1c4f21dd4c224adbb9a71fa4621eabd452";// 十六进制
        // 两位代表一个字符，前2到10位为变值第11位开始时有效密码加密即从68ff74....
        byte[] jiamiByte = initSysbaseJiami(pwdjiami);
//        System.out.println("sysbase-加密原始:" + new String(jiamiByte));
        byte[] key = initKey(pwdjiami);
        byte[] pwdbyte = pwd.getBytes("UTF-16BE");
        byte[] arrayOfByte = new byte[518];
        for (int i = 0; i < 510; i++) {
            if (i < pwdbyte.length) {
                arrayOfByte[i] = pwdbyte[i];
            } else {
                arrayOfByte[i] = 0;
            }
        }
        for (int j = 0; j < key.length; j++) {
            arrayOfByte[j + 510] = key[j];
        }
        byte[] pwdafter = jiami("SHA-256", arrayOfByte);
//        System.out.println("sysbase-SHA-256加密后:" + new String(pwdafter));

        String rs = byteToString(pwdafter);
        return rs;
    }

    /**
     * 获得2到10位的变值key
     *
     * @param pwdjiami
     * @return
     */
    public static final byte[] initKey(String pwdjiami) {
        int i = pwdjiami.length() / 2;
        byte[] arrayOfByte1 = null;
        if (i == 42) {
            byte[] aa = pwdjiamiToByte(pwdjiami);
            arrayOfByte1 = new byte[8];
            if (aa.length > 10) {
                System.arraycopy(aa, 2, arrayOfByte1, 0, 8);
            }
        } else if (i == 72) {
            byte[] bb = pwdjiamiToByte(pwdjiami);
            byte[] arrayOfByte2 = new byte[42];
            System.arraycopy(bb, 30, arrayOfByte2, 0, 42);
            arrayOfByte1 = new byte[8];
            System.arraycopy(arrayOfByte2, 2, arrayOfByte1, 0, 8);
        }
        if (arrayOfByte1 == null) {
            return null;
        }
        return arrayOfByte1;
    }

    /**
     * 初始数据库的十六进制密码 获取10位之后的信息
     *
     * @param jiami
     * @return
     */
    public static final byte[] initSysbaseJiami(String jiami) {
        int i = (jiami.length()) / 2;
        byte[] arrayOfByte = null;
        if (i == 42) {
            arrayOfByte = pwdjiamiToByte(jiami);
        } else if (i == 72) {
            byte[] aa = pwdjiamiToByte(jiami);
            arrayOfByte = new byte[42];
            System.arraycopy(aa, 30, arrayOfByte, 0, 42);
        }
        if (arrayOfByte == null) {
            return null;
        }
        byte[] bb = null;
        if (arrayOfByte.length > 10) {
            bb = new byte[i = arrayOfByte.length - 10];
            System.arraycopy(arrayOfByte, 10, bb, 0, i);
        }
        return bb;
    }

    /**
     * postgresql加密测试效果与对比
     *
     * @param name
     * @param pwd
     * @throws NoSuchAlgorithmException
     */
    public static String pgsql(String name, String pwd)
            throws NoSuchAlgorithmException {
        String pwdjiami = "md5068d0965bec91a3274a534bd05f7d861"; // 对密码+用户名进行md5加密
        byte[] a = pwdjiamiToByte(pwdjiami.substring(3));
//        System.out.println("pgsql-加密原始:" + new String(a));

        byte[] b = (pwd + name).getBytes();
        byte[] c = jiami("MD5", b);
//        System.out.println("pgsql-加密后:" + new String(c));
        String rs = byteToString(c);
        return rs;
    }

    /**
     * sqlserver测试密码
     *
     * @param pwd
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static void sqlserver(String version, String pwd)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if ("2012".equals(version) || "2014".equals(version)) {
            testSqlServer2012Or2014(pwd);
        } else if ("2000".equals(version)) {
            testSqlServer2000(pwd);
        } else if ("2016".equals(version)) {
            testSqlServer2016(pwd);
        } else if ("2005".equals(version) || "2008".equals(version)) {
            testSqlServer2005Or2008(pwd);
        }
    }

    /**
     * sqlserver2000加密
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String testSqlServer2000(String pwd)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pwdjiami = "0x0100b360d03cd9916d5e4ba68c6889d338ec24807a18f2ed39d9d9916d5e4ba68c6889d338ec24807a18f2ed39d9";
        String str1 = pwdjiami.replaceAll("\\-", "");
        int a = (str1.length() - 14) / 2;
        byte[] b = pwdjiamiToByte(str1.substring(14, a + 14));
//        System.out.println("sqlserver2000-加密原始:" + new String(b));
        String str2 = pwdjiami.replaceAll("\\-", "").substring(6, 14);
        byte[] jiamiafter = sqlserverjiami(str2, pwd);
//        System.out.println("sqlserver2000-加密后:" + new String(jiamiafter));
        String rs = byteToString(jiamiafter);
        return rs;
    }

    /**
     * sqlserver2005和2008加密
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String testSqlServer2005Or2008(String pwd)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // String pwdjiami =
        // "01004086CEB628AA51DD7E821560D52C6A6B5DC187421C6E8057";//2005 123456
        String pwdjiami = "010056049B0EEC4467EE48EEA24126BA0AB59DBD26BBC19CA6CB";// 2008
        // 123456789
        byte[] a = pwdjiamiToByte(pwdjiami.replaceAll("\\-", "").substring(12));
//        System.out.println("sqlserver2005-加密原始:" + new String(a));
        String str1 = pwdjiami.replaceAll("\\-", "").substring(4, 12);
        byte[] jiamiafter = sqlserverjiami(str1, pwd);
//        System.out.println("sqlserver2005-加密后:" + new String(jiamiafter));
        String rs = byteToString(jiamiafter);
        return rs;
    }

    /**
     * Todo: sql server 2016-2017加密
     *
     * @Author:$czwei
     * @Date: 2018/12/19 14:21
     */
    private static void testSqlServer2016(String pwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pwdjiami = "0x0100b360d03cd9916d5e4ba68c6889d338ec24807a18f2ed39d9d9916d5e4ba68c6889d338ec24807a18f2ed39d9";
        String str1 = pwdjiami.replaceAll("\\-", "");
        int a = (str1.length() - 14) / 2;
        byte[] b = pwdjiamiToByte(str1.substring(14, a + 14));
        System.out.println("sqlserver2016-加密原始:" + new String(b));
        String str2 = pwdjiami.replaceAll("\\-", "").substring(6, 14);
        byte[] jiamiafter = sqlserverjiami(str2, pwd);
        System.out.println("sqlserver2016-加密后:" + new String(jiamiafter));
        byteToString(jiamiafter);
    }


    /**
     * sqlserver加密方法SHA-1
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static byte[] sqlserverjiami(String str2, String pwd)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] pwdByte = pwd.getBytes("UTF-16LE");
        byte[] var4 = pwdjiamiToByte(str2);
        byte[] var2 = new byte[pwdByte.length + var4.length];
        System.arraycopy(pwdByte, 0, var2, 0, pwdByte.length);
        System.arraycopy(var4, 0, var2, pwdByte.length, var4.length);
        return jiami("SHA-1", var2);
    }

    /**
     * sqlserver2012和2014加密验证 找出与传入值密码相同的用户
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static void testSqlServer2012Or2014(String pwd) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
            String url = "jdbc:microsoft:sqlserver://172.19.1.237:62012;DatabaseName=master";
//            String url = "jdbc:microsoft:sqlserver://172.19.1.237:62014;DatabaseName=master";
            //mydb为数据库
            String user = "sa";
            String password = "Ceshi123";
            conn = DriverManager.getConnection(url, user, password);
            // 内置密码比较方法
            String sql = "SELECT name FROM sys.sql_logins WHERE PWDCOMPARE(?, password_hash) = 1";
            // 返回密码为pwd的用户名
            ps = conn.prepareStatement(sql);
            ps.setString(1, pwd);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("name") + ":" + pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * oracleSHA1加密测试效果与对比
     *
     * @param name
     * @param pwd
     * @throws NoSuchAlgorithmException
     */
    public static String oracleSHA1(String name, String pwd)
            throws NoSuchAlgorithmException {
        String spare4 = "S:F87BE4C1463EECA763BCA7497EDDAA988B461764258521A6AA4D5F14D1E5";// S:无用信息,42位之后为随机值
        byte[] a = pwdjiamiToByte(spare4.substring(2, 42));
//        System.out.println("oracle-SHA1加密原始" + new String(a));
        String str = spare4.substring(42, spare4.length());// 获得随机值
        byte[] paramString1 = pwd.getBytes();
        byte[] paramString2 = pwdjiamiToByte(str);
        byte[] arrayOfByte = new byte[paramString1.length + paramString2.length];
        System.arraycopy(paramString1, 0, arrayOfByte, 0, paramString1.length);
        System.arraycopy(paramString2, 0, arrayOfByte, paramString1.length,
                paramString2.length);
        byte[] b = jiami("SHA-1", arrayOfByte);
//        System.out.println("oracle-SHA1加密后" + new String(b));
        String sr = byteToString(b);
        return sr;
    }

    /**
     * oracle DES方式加密效果与对比
     *
     * @param name
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String oracleDES(String name, String pwd)
            throws UnsupportedEncodingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
//        byte[] a = pwdjiamiToByte("3601B8BAF35B3946");//scott 用户
//        byte[] a = pwdjiamiToByte("ED97A77CCF02CE91");// normal用户
//        System.out.println("oracle-DES加密原始" + new String(a));
        byte[] aa = (name + pwd).toUpperCase().getBytes("utf-16be");
        byte[] paramString1 = Arrays.copyOf(aa, (aa.length + 7) / 8 << 3);
        byte[] paramString2 = a(paramString1, b);

        SecretKey sks = new SecretKeySpec(paramString2,
                paramString2.length - 8, 8, "DES");
        byte[] bb = a(paramString1, sks);
        byte[] cc = new byte[8];
        System.arraycopy(bb, bb.length - 8, cc, 0, 8);
//        System.out.println("oracle-DES加密原始:" + new String(cc));
        String sr = byteToString(cc);
        return sr;
    }

    /**
     * oracle DES加密
     *
     * @param arg1
     * @param paramKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
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

    /**
     * mysql加密测试
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     */
    public static String mysql(String pwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] b = pwdjiamiToByte("6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9"); // 两次sha1加密
//        byte[] b1 = pwdjiamiToByte("B923AB8476BEB39254BFB1D62D8F5871AB85DC7D");// 5.0
//        System.out.println("mysql-SHA1加密原始：" + new String(b, "utf-8"));
        byte[] a = jiami("SHA1", pwd.getBytes());//5.0-5.7 SHA1
        byte[] c = jiami("SHA1", a);
//            System.out.println("mysql-SHA1加密后：" + new String(c, "utf-8"));
        String rs = byteToString(c);
        return rs;
    }

    /**
     * 对原始加密后的字符数组进行十六进制转化显示，用来和数据库保存的密码比较
     *
     * @param a
     */
    private static String byteToString(byte[] a) {
        StringBuilder sb = new StringBuilder();
        int v = 0;
        for (int i = 0; i < a.length; i++) {
            v = a[i];
            if (v < 0) {
                v += 256;
            }
            sb.append(Integer.toHexString(v));
        }
//        System.out.println(sb.toString().toUpperCase());
        String rs = sb.toString().toUpperCase();
        return rs;
    }

    /**
     * 对数据库中的十六进制密码转化成字符数组
     *
     * @param paramString
     * @return
     */
    public static byte[] pwdjiamiToByte(String paramString) {
        if (paramString == null) {
            return null;
        }
        int i = paramString.length() % 2;
        if (i == 1) {
            return null;
        }
        byte[] arrayOfByte = new byte[i /= 2];
        for (int j = 0; j != i; j++) {
            int k = 0;
            try {
                k = Integer.parseInt(
                        paramString.substring(j << 1, (j << 1) + 2), 16);
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

    /**
     * java自带的加密方法
     *
     * @param way
     * @param arrayOfByte
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] jiami(String way, byte[] arrayOfByte)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(way);
        md.reset();
        md.update(arrayOfByte);
        byte[] b = md.digest();
        return b;
    }
}
