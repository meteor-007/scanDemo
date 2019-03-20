package coder;

import org.junit.Before;
import util.RSACoder;
import org.testng.annotations.Test;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Todo:
 *
 * @author $ czwei
 * @create 2018/12/26
 */
public class CodersTest {


    private String publicKey;
    private String privateKey;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();

        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }

    @Test
    public void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String inputStr = "Ceshi123";
        byte[] data = inputStr.getBytes();
        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
        byte[] decodedData = RSACoder
                .decryptByPublicKey(encodedData, publicKey);
        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);
        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("状态:\r" + status);
//        assertTrue(status);

    }
}
