package coder;

import org.junit.Before;
import org.testng.annotations.Test;
import util.DESCoder;
import util.PBECoder;
import util.RSACoder;
import util.Sysmmetric;

import java.math.BigInteger;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

/**
 * Todo:加密相关测试类
 *
 * @author $ czwei
 * @create 2018/12/21
 */
public class CoderTest {
    @Test
    public void test() throws Exception {
        String inputStr = "Ceshi123";
        System.err.println("原文:\n" + inputStr);

        byte[] inputData = inputStr.getBytes();
        String code = Sysmmetric.encryptBASE64(inputData);

        System.err.println("BASE64加密后:\n" + code);
        byte[] output = Sysmmetric.decryptBASE64(code);
        String outputStr = new String(output);
        System.err.println("BASE64解密后:\n" + outputStr);

        // 验证BASE64加密解密一致性
        assertEquals(inputStr, outputStr);

        // 验证MD5对于同一内容加密是否一致
        assertArrayEquals(Sysmmetric.encryptMD5(inputData), Sysmmetric
                .encryptMD5(inputData));

        // 验证SHA对于同一内容加密是否一致
        assertArrayEquals(Sysmmetric.encryptSHA(inputData), Sysmmetric
                .encryptSHA(inputData));

        String key = Sysmmetric.initMacKey();
        System.err.println("Mac密钥:\n" + key);

        // 验证HMAC对于同一内容，同一密钥加密是否一致
        assertArrayEquals(Sysmmetric.encryptHMAC(inputData, key), Sysmmetric.encryptHMAC(
                inputData, key));
        BigInteger md5 = new BigInteger(Sysmmetric.encryptMD5(inputData));
        System.err.println("MD5:\n" + md5.toString(16));
        BigInteger sha = new BigInteger(Sysmmetric.encryptSHA(inputData));
        System.err.println("SHA:\n" + sha.toString(32));
        BigInteger mac = new BigInteger(Sysmmetric.encryptHMAC(inputData, inputStr));
        System.err.println("HMAC:\n" + mac.toString(16));

        String inputStr1 = "123456";
        String key1 = DESCoder.initKey(inputStr1 );
        System.err.println("DES原文:\t" + inputStr1);
        System.err.println("DES密钥:\t" + key1);
        byte[] inputData1 = inputStr1.getBytes();
        inputData = DESCoder.encrypt(inputData1, key1);
        System.err.println("DES加密后:\t" + DESCoder.encryptBASE64(inputData));
        byte[] outputData = DESCoder.decrypt(inputData, key1);
        String outputStr1 = new String(outputData);
        System.err.println("DES解密后:\t" + outputStr1);
        assertEquals(inputStr1, outputStr1);


        String inputStr2 = "Ceshi123";
        System.err.println("原文: " + inputStr2);
        byte[] input = inputStr2.getBytes();
        String pwd = "Ceshi123";
        System.err.println("密码: " + pwd);
        byte[] salt = PBECoder.initSalt();
        byte[] data = PBECoder.encrypt(input, pwd, salt);
        System.err.println("加密后: " + PBECoder.encryptBASE64(data));
        byte[] output2 = PBECoder.decrypt(data, pwd, salt);
        String outputStr2 = new String(output2);
        System.err.println("解密后: " + outputStr2);
        assertEquals(inputStr2, outputStr2);
    }




}
