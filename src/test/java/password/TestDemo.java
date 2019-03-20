package password;

import util.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Todo:弱口令检测类
 *
 * @author $ czwei
 * @create 2018/12/18
 */

public class TestDemo {

    public static void main(String[] args) throws NoSuchPaddingException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        System.out.println("this is a test code");

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
//
//        String[] testPass = readTxt("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\mysql.txt");
        String[] testPass = FileUtils.toArrayByFileReader1("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\password-4-data4.txt");
//        System.out.println(testPass.length);
//        System.out.println(testPass1.length);
        String[] ar = new String[testPass.length];

        for (int i = 0; i < testPass.length; i++) {
            ar[i] = TestPassword.mysql(testPass[i]);
            ar[i] = TestPassword.oracleDES("system", testPass[i]);
            ar[i] = TestPassword.oracleSHA1("system", testPass[i]);
            ar[i] = TestPassword.pgsql("postgres", testPass[i]);
            ar[i] = TestPassword.sysbase(testPass[i]);
            ar[i] = TestPassword.dmdbms("SYSDBA",testPass[i]);// F_4O^@{mLlQ.,[DIi&Hc0iEy5KeVr!(nCDJQ@/7#W]RR{V+g = [B@16c0663d
            ar[i] = TestPassword.kingbase("root",testPass[i]);
            ar[i] = TestPassword.gbase(testPass[i]);
            ar[i] = TestPassword.testSqlServer2000(testPass[i]);
            ar[i] = TestPassword.testSqlServer2005Or2008(testPass[i]);
//            ar[i] = TestPassword.sqlserver("2010",testPass[i]);
//            ar[i] = TestPassword.sqlserver("2012",testPass[i]);
//            ar[i] = TestPassword.sqlserver("2014",testPass[i]);
//            ar[i] = TestPassword.sqlserver("2016",testPass[i]);
//            ar[i] = TestPassword.sqlserver("2017",testPass[i]);

//            System.out.println(testPass[i]);
        }
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\mysql-4-data10.txt"));
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\oracle-DES-4-data10.txt"));
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\oracle-SHA1-4-data10.txt"));
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\postgresql-4-data10.txt"));
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\sysbase-4-data10.txt"));
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\dmdbms-4-data10.txt"));
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\kingbase-4-data10.txt"));
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\gbase-4-data10.txt"));
        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\sqlserver-2000-4-data4.txt"));
//        fos = new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\4位\\sqlserver-4-data1.txt"));
//        // 写入中文字符时解决中文乱码问题
        osw = new OutputStreamWriter(fos, "UTF-8");
        bw = new BufferedWriter(osw);
        for (String arr : ar) {
            bw.write(arr + "\t\n");
        }
        bw.close();
        osw.close();
        fos.close();
    }
}


