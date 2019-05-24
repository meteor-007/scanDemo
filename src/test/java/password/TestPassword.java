package password;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        mysql("!"); // 测试mysql密码加密-SHA1
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
//        oracleDES("system", "Ceshi123");// 测试oracle密码DES加密
        oracleSHA1("system", "Ceshi123");// 测试oracle密码SHA1加密
        /*
         * 3,sqlserver支持2000 2005 2008版本的密码匹配
         * 2000：select x.name,master.dbo.fn_varbintohexstr(x.password) as password_hash from
         * master.dbo.syslogins s, master.dbo.sysxlogins x where s.isntgroup = 0
         * and s.isntuser = 0 and x.name = s.name
         * 2005and2008:
         * 2012,2014,2017: select name,password_hash, is_disabled from sys.sql_logins where name is not null
         *  需要连数据库调用方法返回匹配结果
         */
//        sqlserver("2000", "ankki123");// 测试sqlserver2000密码
//        sqlserver("2000", "ANKKI123");// 测试sqlserver2005密码
//        testSqlServer2005Or2008("Ceshi123");
//        testSqlServer2012Or2014();
//        sqlserver("2008", "Ceshi123");// 测试sqlserver2008密码
//        sqlserver("2012", "Ceshi123");// 测试sqlserver2012密码
//        sqlserver("2014", "Ceshi123");// 测试sqlserver2014密码
//        sqlserver("2016", "Ceshi123");// 测试sqlserver2016密码
//        sqlserver("2017", "Ceshi123");// 测试sqlserver2017密码
//        testSqlServer2012Or2014();
//        testSqlServer2000("Ceshi123");
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
//        kingbase("SYSTEM", "Ceshi123");//暂无环境测试
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
//        gbase("Ceshi123");//暂无环境测试
        /**
         * Todo:
         * @Author:$czwei
         * @Date: 2018/12/26 10:25
         */
//        db2();//11.1.3版本-8.2版本
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
        // 十六进制
        String pwdjiami = "c0077852792bd3e354ac68ff7472459d0f76260074c4b7e0ec1c4f21dd4c224adbb9a71fa4621eabd452";
        // 两位代表一个字符，前2到10位为变值第11位开始时有效密码加密即从68ff74....
        byte[] jiamiByte = initSysbaseJiami(pwdjiami);
        System.out.println("sysbase-加密原始:" + new String(jiamiByte));
        byte[] key = initKey(pwdjiami);
        byte[] pwdbyte = pwd.getBytes(StandardCharsets.UTF_16BE);
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
        System.out.println("sysbase-SHA-256加密后:" + new String(pwdafter));

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
//            testSqlServer2012Or2014(pwd);
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
//        String pwdjiami = "0x0100b360d03cd9916d5e4ba68c6889d338ec24807a18f2ed39d9d9916d5e4ba68c6889d338ec24807a18f2ed39d9";
//        String pwdjiami = "0x01004e6576605ebad2be331a15028c01912bedf2e4c528dff2b983cfd455a4008b8b1046eb6180383c2479963535";
//        String pwdjiami = "0100B3382E17944260ADEF936899C43C0FCDD571E70BBBC1E1F8";// 2005-ab
        String pwdjiami = "01008231DA1DE5F19A83243916269168E13572D0DB33FA3954D0";// 2005-ankki123


        String str1 = pwdjiami.replaceAll("\\-", "");
        int a = (str1.length() - 14) / 2;
        byte[] b = pwdjiamiToByte(str1.substring(14, a + 14));
//        System.out.println("sqlserver2000-加密原始:" + new String(b));
        String str2 = pwdjiami.replaceAll("\\-", "").substring(6, 14);
        byte[] jiamiafter = sqlserverjiami(str2, pwd);
        System.out.println("sqlserver2000-加密后:" + new String(jiamiafter));
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
        //01004086CEB616CB813DA6870A6FCD0CF43F3B1D5D40713A8A31
        //String pwdjiami = "01004086CEB628AA51DD7E821560D52C6A6B5DC187421C6E8057";// 2005-123456
//        String pwdjiami = "01004086CEB616CB813DA6870A6FCD0CF43F3B1D5D40713A8A31";// 2005-Ceshi123
//        String pwdjiami = "01002067528F7E0D7D502E9678961065E62E9C708F2D06431FE1";// 2005-123
        //String pwdjiami = "010090E8E3F4D8AA0839481B0FB677146DF8745721BB5C53A718";// 2005-user12006
        String pwdjiami = "01008231DA1DE5F19A83243916269168E13572D0DB33FA3954D0";// 2005-ankki123

        byte[] a = pwdjiamiToByte(pwdjiami.replaceAll("\\-", "").substring(12));
        System.out.println("sqlserver2005-加密原始:" + new String(a));
        String str1 = pwdjiami.replaceAll("\\-", "").substring(4, 12);
        byte[] jiamiafter = sqlserverjiami(str1, pwd);
        System.out.println("sqlserver2005-加密后:" + new String(jiamiafter));
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
        byte[] pwdByte = pwd.getBytes("gb2312");
        byte[] var4 = pwdjiamiToByte(str2);
        byte[] var2 = new byte[pwdByte.length + var4.length];
        System.arraycopy(pwdByte, 0, var2, 0, pwdByte.length);
        System.arraycopy(var4, 0, var2, pwdByte.length, var4.length);
        return jiami("SHA-1", var2);
    }

    /**
     * sqlserver2012和2014加密验证 找出与传入值密码相同的用户
     *
     */
    private static void testSqlServer2012Or2014() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://172.19.1.143:52008;DatabaseName=master";
            //[{password_hash=01004086CEB616CB813DA6870A6FCD0CF43F3B1D5D40713A8A31, is_disabled=0, name=sa},
            // {password_hash=010090E8E3F4D8AA0839481B0FB677146DF8745721BB5C53A718, is_disabled=0, name=user1},
            // {password_hash=01006153ADF5BE638F0326FA7CCAD7549ED11FFFADF53865703B, is_disabled=0, name=user2},
            // {password_hash=01002E54302170E84C192AADEA3509F248A765FCC48DCFCB2439, is_disabled=0, name=qwertyuioplkjhgfdsazxcvbnm},
            // {password_hash=01009EC3ECB3BC286270F822E339B2BF1185C028FCE151C78249, is_disabled=0, name=ankki123},
            // {password_hash=01004C383B3835404F00466AAFA303A3002E6DFF15FBFE46D5F1, is_disabled=0, name=ankki},
            // {password_hash=0100B3382E17944260ADEF936899C43C0FCDD571E70BBBC1E1F8, is_disabled=0, name=user3},
            // {password_hash=01002067528F7E0D7D502E9678961065E62E9C708F2D06431FE1, is_disabled=0, name=user4},
            // {password_hash=01001012013451B24C9B7065E02EB7504241737DF1EC3674D205, is_disabled=0, name=user5},
            // {password_hash=01004AB6E8288F4C43338EF7934E8434C81D63BCA6BD93D7103C, is_disabled=0, name=user6}]



            //[{password_hash=01004086CEB616CB813DA6870A6FCD0CF43F3B1D5D40713A8A31, is_disabled=0, name=sa}]
//            String url = "jdbc:sqlserver://172.19.1.143:52005;DatabaseName=master";

            //[{password_hash=010056049B0EF702BE8D5F2BD4301BD5AD2C9B0F6A815B5A9A3A, is_disabled=0, name=sa},
            // {password_hash=01003869D680ADF63DB291C6737F1EFB8E4A481B02284215913F, is_disabled=1, name=##MS_PolicyEventProcessingLogin##},
            // {password_hash=01008D22A249DF5EF3B79ED321563A1DCCDC9CFC5FF954DD2D0F, is_disabled=1, name=##MS_PolicyTsqlExecutionLogin##}]
//            String url = "jdbc:sqlserver://172.19.1.143:52008;DatabaseName=master";

            //[{password_hash=02009C2CB99952B3BBA4BC90F10B05351362731E6734F8204B01B79BD3D4FFAE05357789A73E121767B8FEFF8360087AE828965B0AA20E22C25FAB323B4DB983203EC6CE765C, is_disabled=0, name=sa},
            // {password_hash=0200E74DDACE488B93BE3B3330D10586BEE0EB0940F6B6B3964983BBFB272B809C41E3B666200F28A1942DC0B3FA7339BFF90E51F9AA90F5F627778612117DD336A01194FE7C, is_disabled=1, name=##MS_PolicyTsqlExecutionLogin##},
            // {password_hash=0200BD9BD73C23BC2DDA205F2E60139818B2E54CFB35A13B3FAC951534EE00E2D65F602F90614BBE3AC55B8E8EFF96E06C1F91634D1A9394373EFA1D2A99EC53E828A6A949E6, is_disabled=1, name=##MS_PolicyEventProcessingLogin##}]
//            String url = "jdbc:sqlserver://172.19.1.143:52012;DatabaseName=master";

            //[{password_hash=0200814097FD737E549BD6BC53C0DFA1752C0BC9EECDFC1F6CE31850AFEA228F2E87E2A167D035277F2254111FD971E8D70C49CF120966E3EEFAFF03264297E2A3D960BB49CF, is_disabled=0, name=sa},
            // {password_hash=020079DBCEB15B9ADBC4DA3001D913553D80619CAD943124AC60A54AA052C9393D6F23427476AE26BDE1DC6526BB4BCFFB3A971C612F3C387BF49020F6CD042C09EE57C5D7A7, is_disabled=1, name=##MS_PolicyTsqlExecutionLogin##},
            // {password_hash=0200207FC1F14CE24636801865AE59F6492CFBF54D0022F66B3DE0AB7508F3BC2FAC3A57416EE1E47DEF0319A00968547BB58804E27B5B1072BAAF64DBC566C54067A78149C1, is_disabled=1, name=##MS_PolicyEventProcessingLogin##}]
//            String url = "jdbc:sqlserver://172.19.1.143:52014;DatabaseName=master";

            //[{password_hash=0200BFBA17FC4B30CCACCE9C74BAD0B378E6D4D4F7A8A6433231B4B3865D02A7C14F46D0AF41BD1D31141B2C4995DD7E62D8AFE39176F54EBD6885BB0CEC9F04D657A2D7F6A9, is_disabled=0, name=sa},
            // {password_hash=02007AFFDC72C3D805177EB0E558BD43A6C730FB8ECCFE1990C9D04FCDF8EF724C5C37D75EE4E28BED03011F9459A985E8AA6F82E8867FEAAFBDFCFEABCCB96FD506ABFF2CDD, is_disabled=1, name=##MS_PolicyEventProcessingLogin##},
            // {password_hash=0200F9858D861A868979200C6F51F7E1F33A8A69FDC192F7949F37ACBA1E619CB9BB8280AE759245984C34254369737D5C782F1D8511A614ED7B484A0CB1D484C5076F673A08, is_disabled=1, name=##MS_PolicyTsqlExecutionLogin##}]
//            String url = "jdbc:sqlserver://172.19.1.143:52016;DatabaseName=master";

            //[{password_hash=0200A3AF98D97D623F2BE065FD692308B9BB454B64DD543F0A61FF8962562CC17333784B60B413E363BE8C41C6199A5C10B8ADD47E8669A3474E5F8257CF6984511E55CDC467, is_disabled=0, name=sa},
            // {password_hash=0200B332BC3F194D01C00D9E40AD0E216B798AF28D0B1ADC8887E4F43FB6CEB0E580F7C474A09381C084C38A838F20565E3EC732ED044F820438742C3D40883A39A2D53C6A18, is_disabled=1, name=##MS_PolicyEventProcessingLogin##},
            // {password_hash=0200BF0D92009DBD8A986EFF8EFB9231F9BD9E308D4CB7FD4AC269E932EA91BC7D30DFBF768F1A2E87F81C8D68E8C84D4E0A35E4193E2825BE096D1B13499286CDE4D5F18B44, is_disabled=1, name=##MS_PolicyTsqlExecutionLogin##}]
//            String url = "jdbc:sqlserver://172.19.1.143:52017;DatabaseName=master";


            //[{password_hash=0200303EA0B42AC26FCFB5DC48AF61BDF14CF1079427996B5EC7545C860ED58EB614F77F1DEF6216DE36434E3D35E84F666477279CF5203A6316B53D12F50FED464CC32A7B03, name=sa},
            // {password_hash=0200D2DBB5C34924986D03470F3DAA88E2411C0647AF9B5D628539F4CB8781613F5A69578B6279197A2F4924626948E9EAD551C9529BDC7A5D041CB2492774473A5F7DAC1536, name=##MS_PolicyEventProcessingLogin##}, {password_hash=020026FB3E989CE465663A0CE3C975DE3D97C89BD6CFA9E74646EF265E03E7275DDD8D182E71B9394B98DC4C2170F945C8CE16445444277C9BA9E0FCDC7634CE1B65E908C1D7, name=##MS_PolicyTsqlExecutionLogin##},
            // \{password_hash=020058E79E9E3FCBC5E95897F1F9C8D90F65DAD620C6633A4A15F336657EF5DAB5122FB52CCA255F61AC5174F53271B372D2F5BDBD70D70A384628C980A74A84D6C9E5D044BA, name=test}]
//            String url = "jdbc:sqlserver://172.19.1.211:1433;DatabaseName=master";
            //mydb为数据库
            String user = "sa";
            String password = "Ceshi123";
            conn = DriverManager.getConnection(url, user, password);
            // 内置密码比较方法
//            String sql = "SELECT name FROM sys.sql_logins WHERE PWDCOMPARE(?, password_hash) = 1";
//            String sql = "select x.name,master.dbo.fn_varbintohexstr(x.password) as password_hash from\n" +
//                    " master.dbo.syslogins s, master.dbo.sysxlogins x where s.isntgroup = 0\n" +
//                    "  and s.isntuser = 0 and x.name = s.name";
//            String sql = "select name,password_hash, is_disabled from sys.sql_logins where name is not null";

            String sql = "select name,password_hash, is_disabled from sys.sql_logins where name is not null";
            // 返回密码为pwd的用户名
            ps = conn.prepareStatement(sql);
//            ps.setString(1, pwd);
            rs = ps.executeQuery();
            List<Map<String, Object>> list = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>(16);
                String name = rs.getString("name");
                String passwords = rs.getString("password_hash");
                String is_disabled = rs.getString("is_disabled");
                map.put("name", name);
                map.put("password_hash", passwords);
                map.put("is_disabled", is_disabled);
                list.add(map);
            }
            System.out.println(list);
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
//        byte[] a = pwdjiamiToByte(spare4.substring(2, 42));
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
//        byte[] b = pwdjiamiToByte("6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9"); // 两次sha1加密
//        byte[] b1 = pwdjiamiToByte("B923AB8476BEB39254BFB1D62D8F5871AB85DC7D");// 5.0
//        System.out.println("mysql-SHA1加密原始：" + new String(b, "utf-8"));
        byte[] a = jiami("SHA1", pwd.getBytes());//5.0-5.7 SHA1
        byte[] c = jiami("SHA1", a);
//            System.out.println("mysql-SHA1加密后：" + new String(c, StandardCharsets.UTF_8));
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
            if (v < 16) {
                sb.append(Integer.toHexString(0));
            }
            sb.append(Integer.toHexString(v));
        }
        System.out.println(sb.toString().toLowerCase());
//        String rs = sb.toString().toUpperCase();
        String rs = sb.toString().toLowerCase();
        return  rs;
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
        int i = paramString.length();
        if (i % 2 == 1) {
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
