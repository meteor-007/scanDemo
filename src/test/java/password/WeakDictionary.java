package password;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Todo:弱口令扫描：
 * 基于用户规则初始化字典
 * 基于用户的密码长度（数字，字符，制定字符）
 *
 * @author $ czwei
 * @create 2018/12/21
 */
public class WeakDictionary {

    private static final byte[] a = {1, 35, 69, 103, -119, -85, -51, -17};// oracle
    // DES�̶�key
    private static SecretKey b = new SecretKeySpec(a, "DES");
    private static final IvParameterSpec c = new IvParameterSpec(new byte[8]);
    private static final byte[] cc = {48, 0, 0, 0, 0, 0, 0, 0};
    private static SecretKey d = new SecretKeySpec(cc, "DES");
    private static final IvParameterSpec f = new IvParameterSpec(new byte[]{
            32, 33, 35, 36, 37, 38, 39, 40});

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        /*
         * 1,mysql���ݿ��ѯ�û�����ܵ����� if (this.i.l().startsWith("5.7")) { 5.7�汾 return
         * "select user, plugin,authentication_string from mysql.user where user != '' and user!='mysql.sys'"
         * ; } 5.7���� return
         * "select user, password from mysql.user where user != ''";
         */

        /*
         * 2,oracle���ݿ��ѯ�û�����ܵ����� PASSWORDΪDES���ܱ�������� spare4Ϊsha1���ܷ�ʽ��������� SELECT
         * u.NAME AS NAME,u.PASSWORD AS PASSWORD,u.spare4 AS spare4,u.astatus AS
         * STATUS,d.ACCOUNT_STATUS AS statusText,u.ctime AS create_date,u.ltime
         * AS lock_date,NULL AS lock_time FROM sys.user$ u JOIN dba_users d ON
         * u.NAME = d.username ���PASSWORD�ֶ�û��ֵ���߳��Ȳ�����16�����sha1��ʽ
         */
        //oracleDES("system", "ankki");// ����oracle����DES����
        //oracleSHA1("system", "ankki");// ����oracle����SHA1����

        /*
         * 3,sqlserver֧��2000 2005 2008�汾������ƥ�� 2000��select x.name,
         * master.dbo.fn_varbintohexstr(x.password) as password_hash from
         * master.dbo.syslogins s, master.dbo.sysxlogins x where s.isntgroup = 0
         * and s.isntuser = 0 and x.name = s.name 2005and2008:select name,
         * password_hash, is_disabled from sys.sql_logins where name is not null
         * 2012 2014��Ҫ�����ݿ���÷�������ƥ����
         */
        //sqlserver("2000","123456");// ����sqlserver2000����
        sqlserver("2005", "ankki");
//		 sqlserver("2008","Ceshi123");// ����sqlserver2000����
                // sqlserver("2012","ankki");// ����sqlserver2012��2014����
                /*
                 * 4,postgresql���ݿ��ѯ�û�����ܵ����� ��������ֶβ�����md5��ͷ������û�м��ܵİ汾 SELECT
                 * usename,passwd, valuntil FROM pg_shadow
                 */
                // pgsql("postgres", "Ceshi123");// ����pgsql����
                /*
                 * 5,sysbase���ݿ��ѯ�û�����ܵ����� SHA-256���� select name, len(password) as
                 * length,password,status from master..syslogins
                 */
                // sysbase("");// ����sysbase���� ��ʱ��������Ϊ��Ч�� ��֧��15.0.2�����ϰ汾
                /*
                 * 6,�������ݿ��ѯ�û�����ܵ����� DM7: select USERNAME, PASSWORD from DBA_USERS DM6 :
                 * select ID, NAME AS USERNAME, PASSWORD from SYSLOGINS
                 */
                // dmdbms("SYSDBA","123456");// ���Դ������� ��ʱ֧�ִ���7 ����6�޻���
                /*
                 * 7,�˴������ݿ� select t.username, t.passwd from sys_catalog.sys_pwdht t
                 * where timestamp = (select max(timestamp) from sys_catalog.sys_pwdht
                 * where username = t.username)
                 */
                // kingbase("name","pwd");//���޻�������
                /*
                 * 8,�ϴ�ͨ�����ݿ� String var1 =
                 * "SELECT TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = \'user\' AND TABLE_SCHEMA IN (\'system\', \'gbase\') ORDER BY CREATE_TIME LIMIT 1"
                 * ; String var2 =
                 * "select user, password from %SystemSchema%.user where user != \'\'";
                 * if(!(var5 = this.b.createStatement().executeQuery(var1)).next()) {
                 * throw new SQLException("�Ҳ����û���!"); } else { var2 =
                 * var2.replace("%SystemSchema%", var5.getString(1)); var5 =
                 * this.b.createStatement().executeQuery(var2);
                 */
                // gbase("pwd");//���޻�������
    }
    /**
     * �ϴ�ͨ�ò�������
     *
     * @throws NoSuchAlgorithmException
     */
    private static void gbase(String pwd) throws NoSuchAlgorithmException {
        String pwdjiami = "";// �ϴ�ͨ�����ݿⱣ�������
        byte[] jiamiByte = pwdjiamiToByte(pwdjiami.replaceFirst("[*]", ""));
        System.out.println(new String(jiamiByte));

        byte[] tmp = jiami("SHA1", pwd.getBytes());
        byte[] jiamipwd = jiami("SHA1", tmp);
        System.out.println(new String(jiamipwd));
        byteToString(jiamipwd);
    }

    /**
     * �˴��ֲ�������
     *
     * @throws NoSuchAlgorithmException
     */
    private static void kingbase(String name, String pwd)
            throws NoSuchAlgorithmException {
        String pwdjiami = "";// �˴������ݿⱣ�������
        byte[] jiamiByte = pwdjiamiToByte(pwdjiami.substring(3));
        System.out.println(new String(jiamiByte));

        byte[] var2 = (pwd + name).getBytes();
        byte[] jiamipwd = jiami("MD5", var2);
        System.out.println(new String(jiamipwd));
        byteToString(jiamipwd);

    }

    /**
     * ���β����������Ч���Ա�
     *
     * @param
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static void dmdbms(String name, String pwd)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        String pwdjiami = "__0Sk+wY\\R7Du]Ni^&Vp~V|/w{3V~R3~5Bo-Pqs1uNT2&Q91";// �������ݿⱣ�������
        byte[] jiamiByte = initDmJiami(pwdjiami);
        System.out.println(new String(jiamiByte));
        byte[] pwdByte = b(pwd, name);// ����ԭʼ����
        System.out.println(new String(pwdByte));
    }

    /**
     * �Դ���ԭʼ������м���
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
     * ���� DES����
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
     * ��ʼ�����μ��ܵ�����ת���ַ�����
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
     * sysbase�����������Ч���Ա�
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static void sysbase(String pwd)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pwdjiami = "c0077852792bd3e354ac68ff7472459d0f76260074c4b7e0ec1c4f21dd4c224adbb9a71fa4621eabd452";// ʮ������
        // ��λ����һ���ַ���ǰ2��10λΪ��ֵ��11λ��ʼʱ��Ч������ܼ���68ff74....
        byte[] jiamiByte = initSysbaseJiami(pwdjiami);
        System.out.println(new String(jiamiByte));

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
        System.out.println(new String(pwdafter));
        byteToString(pwdafter);

    }

    /**
     * ���2��10λ�ı�ֵkey
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
     * ��ʼ���ݿ��ʮ���������� ��ȡ10λ֮�����Ϣ
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
     * postgresql���ܲ���Ч����Ա�
     *
     * @param name
     * @param pwd
     * @throws NoSuchAlgorithmException
     */
    private static void pgsql(String name, String pwd)
            throws NoSuchAlgorithmException {
        String pwdjiami = "md5068d0965bec91a3274a534bd05f7d861"; // ������+�û�������md5����
        byte[] a = pwdjiamiToByte(pwdjiami.substring(3));
        System.out.println(new String(a));

        byte[] b = (pwd + name).getBytes();
        byte[] c = jiami("MD5", b);
        System.out.println(new String(c));
        byteToString(c);
    }

    /**
     * sqlserver��������
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
        } else if ("2005".equals(version) || "2008".equals(version)) {
            testSqlServer2005Or2008(pwd);
        }

    }

    /**
     * sqlserver2000����
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static void testSqlServer2000(String pwd)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pwdjiami = "0x0100b360d03cd9916d5e4ba68c6889d338ec24807a18f2ed39d9d9916d5e4ba68c6889d338ec24807a18f2ed39d9";
        String str1 = pwdjiami.replaceAll("\\-", "");
        int a = (str1.length() - 14) / 2;
        byte[] b = pwdjiamiToByte(str1.substring(14, a + 14));
        System.out.println(new String(b));

        String str2 = pwdjiami.replaceAll("\\-", "").substring(6, 14);
        byte[] jiamiafter = sqlserverjiami(str2, pwd);
        System.out.println(new String(jiamiafter));
        byteToString(jiamiafter);
    }

    /**
     * sqlserver2005��2008����
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static void testSqlServer2005Or2008(String pwd)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //String pwdjiami = "01004086CEB628AA51DD7E821560D52C6A6B5DC187421C6E8057";//2005 123456
        //String pwdjiami = "010056049B0EEC4467EE48EEA24126BA0AB59DBD26BBC19CA6CB";// 2008// 123456789
//        String pwdjiami = "01004086CEB616CB813DA6870A6FCD0CF43F3B1D5D40713A8A31";// 2008// Ceshi123
        String pwdjiami = "01008231DA1DE5F19A83243916269168E13572D0DB33FA3954D0";// 2005// ankki123

        byte[] a = pwdjiamiToByte(pwdjiami.replaceAll("\\-", "").substring(12));
//        System.out.println(new String(a));

        String str1 = pwdjiami.replaceAll("\\-", "").substring(4, 12);
        byte[] jiamiafter = sqlserverjiami(str1, pwd);
        System.out.println(new String(jiamiafter));
        byteToString(jiamiafter);

//        System.out.print(pwdjiami.length());

    }

    /**
     * sqlserver���ܷ���SHA-1
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
     * sqlserver2012��2014������֤ �ҳ��봫��ֵ������ͬ���û�
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static void testSqlServer2012Or2014(String pwd) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = SqlServerDbUtils.getConn();
//            String sql = "SELECT name FROM sys.sql_logins WHERE PWDCOMPARE(?, password_hash) = 1"; // ��������ȽϷ���
//            // ��������Ϊpwd���û���
//            ps = conn.prepareStatement(sql);
//            ps.setString(1, pwd);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getString("name") + ":" + pwd);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * oracleSHA1���ܲ���Ч����Ա�
     *
     * @param name
     * @param pwd
     * @throws NoSuchAlgorithmException
     */
    private static void oracleSHA1(String name, String pwd)
            throws NoSuchAlgorithmException {
        String spare4 = "S:F87BE4C1463EECA763BCA7497EDDAA988B461764258521A6AA4D5F14D1E5";// S:������Ϣ,42λ֮��Ϊ���ֵ
        byte[] a = pwdjiamiToByte(spare4.substring(2, 42));
        System.out.println(new String(a));
        String str = spare4.substring(42, spare4.length());// ������ֵ

        byte[] paramString1 = pwd.getBytes();
        byte[] paramString2 = pwdjiamiToByte(str);
        byte[] arrayOfByte = new byte[paramString1.length + paramString2.length];
        System.arraycopy(paramString1, 0, arrayOfByte, 0, paramString1.length);
        System.arraycopy(paramString2, 0, arrayOfByte, paramString1.length,
                paramString2.length);
        byte[] b = jiami("SHA-1", arrayOfByte);
        System.out.println(new String(b));
        byteToString(b);

    }

    /**
     * java�Դ��ļ��ܷ���
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

    /**
     * oracle DES��ʽ����Ч����Ա�
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
    public static void oracleDES(String name, String pwd)
            throws UnsupportedEncodingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] a = pwdjiamiToByte("ED97A77CCF02CE91");
        System.out.println(new String(a));
        byte[] aa = (name + pwd).toUpperCase().getBytes("utf-16be");
        byte[] paramString1 = Arrays.copyOf(aa, (aa.length + 7) / 8 << 3);
        byte[] paramString2 = a(paramString1, b);

        SecretKey sks = new SecretKeySpec(paramString2,
                paramString2.length - 8, 8, "DES");
        byte[] bb = a(paramString1, sks);
        byte[] cc = new byte[8];
        System.arraycopy(bb, bb.length - 8, cc, 0, 8);
        System.out.println(new String(cc));
        byteToString(cc);
    }

    /**
     * oracle DES����
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
     * mysql���ܲ���
     *
     * @param pwd
     * @throws UnsupportedEncodingException
     */
    private static void mysql(String pwd) throws UnsupportedEncodingException {
        //byte[] b = pwdjiamiToByte("6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9"); // ����sha1����

        //System.out.println(new String(b, "utf-8"));
        try {
            byte[] a = jiami("SHA1", pwd.getBytes());
            byte[] c = jiami("SHA1", a);
            //System.out.println(new String(c, "utf-8"));
            byteToString(c);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ԭʼ���ܺ���ַ��������ʮ������ת����ʾ�����������ݿⱣ�������Ƚ�
     *
     * @param a
     */
    private static void byteToString(byte[] a) {
        StringBuilder sb = new StringBuilder();
        int v = 0;
        for (int i = 0; i < a.length; i++) {
            v = a[i];
            if (v < 0) {
                v += 256;
            }
            sb.append(Integer.toHexString(v));
        }
        System.out.println(sb.toString().toUpperCase());
    }

    /**
     * �����ݿ��е�ʮ����������ת�����ַ�����
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
     * ����������û��������ʼ���ֵ�
     *
     * @param name
     * @param a
     * @return
     */
    public static String[] userNameRule(String name, int a) { //
        int var5 = 0;
        switch (a) {
            case 3: // ���û�����ͬ
                return new String[]{name, name.toLowerCase(), name.toUpperCase()};
            case 4:// �û�+�������
                ArrayList var9 = new ArrayList();

                int var7;
                for (var7 = 0; var7 <= 1000; ++var7) {
                    var9.add(name.toUpperCase() + var7);
                }

                for (var7 = 0; var7 <= 1000; ++var7) {
                    var9.add(name + var7);
                }

                for (var7 = 0; var7 <= 1000; ++var7) {
                    var9.add(name.toLowerCase() + var7);
                }

                return (String[]) var9.toArray(new String[0]);
            case 5:// �û���+�������
                ArrayList var10 = new ArrayList();

                for (var5 = 1950; var5 <= 2050; ++var5) {
                    var10.add(name.toUpperCase() + var5);
                }

                for (var5 = 1950; var5 <= 2050; ++var5) {
                    var10.add(name + var5);
                }

                for (var5 = 1950; var5 <= 2050; ++var5) {
                    var10.add(name.toLowerCase() + var5);
                }

                return (String[]) var10.toArray(new String[0]);
            case 6:// �û�����ת
                StringBuffer var2 = new StringBuffer();

                for (var5 = name.length() - 1; var5 >= 0; --var5) {
                    var2.append(name.charAt(var5));
                }

                StringBuffer var6 = new StringBuffer();

                for (int var3 = name.length() - 1; var3 >= 0; --var3) {
                    var6.append(name.toUpperCase().charAt(var3));
                }

                StringBuffer var8 = new StringBuffer();

                for (int var4 = name.length() - 1; var4 >= 0; --var4) {
                    var8.append(name.toLowerCase().charAt(var4));
                }

                return new String[]{var2.toString(), var6.toString(),
                        var8.toString()};
            default:
                return new String[0];
        }
    }

    private static StringBuffer l = new StringBuffer();
    private static int r = 4;// ���벻����λ��
    private static final char[] shuzi = new char[]{'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'};
    private static final char[] zimu = new char[]{'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] teshuzifu = new char[]{'`', '~', '!', '@',
            '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '{',
            '[', '}', ']', ';', ':', '\'', '\"', '\\', '|', ',', '<', '.', '>',
            '/', '?', ' '};
    private static char[] q = new char[]{'a', 'n', 'k', 'i'}; // �����û�ѡ�����֣���ĸ�������ַ����Լ�ָ���ַ��ϲ��ɵ��ַ�����
    // ���ڲ���Ϊankki

    /**
     * ������������볤�Ƚ��г�ʼ���ֵ�
     */
    private static void f() {
        boolean var1 = l.length() != r - 1;
        char[] var5 = q;
        int var4 = q.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            char var2 = var5[var3];
            l.append(var2);
            System.out.println(l.toString());
            // �������ƥ�����룬�����ƥ�䵽�ͼ�¼�Ѿ�ƥ�䵽���û���ɾ��
            // �ο�cn.schina.dbs.service.scan.password.o.f()
            if (var1) {
                f();
            }

            l.deleteCharAt(l.length() - 1);

        }

    }

}
