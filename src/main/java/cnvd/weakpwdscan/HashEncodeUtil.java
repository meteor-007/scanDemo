package cnvd.weakpwdscan;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author czjuan
 * @date 2018/4/9
 */
public class HashEncodeUtil {
    private HashEncodeUtil() {
        throw new IllegalStateException("HashEncodeUtil class");
    }

    /**
     * 秘钥长度
     */
    public static final Integer THE_KEY_LENGTH = 16;
    private static final Logger log = LoggerFactory.getLogger(HashEncodeUtil.class);

    /**
     * AES算法加密
     *
     * @param encryptStr 需要加密的字符串
     * @param sKey       加密密钥
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String encryptStr, String sKey) {
        // 判断密钥是否正确
        if (sKey == null) {
            log.info("AES加密的Key为空null");
            return null;
        }
        // 判断密钥是否为16位
        if (sKey.length() != THE_KEY_LENGTH) {
            log.info("AES加密的Key长度不是16位");
            return null;
        }
        try {
            byte[] raw = sKey.getBytes("utf-8");
            //定义加密算法，这里用AES算法加密skey，得到加密的密钥
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 实例化Cipher对象，得到AES/ECB/PKCS5Padding转换的对象，"算法/模式/补码方式,模式和补码方式可以不写，因为默认就为ECB和PKCS5Padding"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //用密钥初始化 Cipher对象为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted = cipher.doFinal(encryptStr.getBytes("utf-8"));
            // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
            String encode = Base64.encode(encrypted);
            return encode;
        } catch (Exception e) {
            log.error("AES加密过程发生异常:{}", e);
            return null;
        }

    }

    /**
     * AES算法 解密
     *
     * @param decryptStr 需要解密的字符串
     * @param sKey       解密密钥
     * @return
     */
    public static String aesDecrypt(String decryptStr, String sKey) {
        try {
            // 判断密钥是否正确
            if (sKey == null) {
                log.info("AES解密的Key为空null");
                return null;
            }
            // 判断密钥是否为16位
            if (sKey.length() != THE_KEY_LENGTH) {
                log.info("AES解密的Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // 先用base64解密
            byte[] encrypted = Base64.decode(decryptStr);

            byte[] original = cipher.doFinal(encrypted);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception e) {
            log.info("AES解密过程发生异常:{}", e);
            return null;
        }
    }
}
