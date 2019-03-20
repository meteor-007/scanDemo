package cnvd.password;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class dmdbms {

    private static final byte[] cc = {48, 0, 0, 0, 0, 0, 0, 0};
    private static SecretKey d = new SecretKeySpec(cc, "DES");
    private static final IvParameterSpec f = new IvParameterSpec(new byte[]{
            32, 33, 35, 36, 37, 38, 39, 40});

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        // TODO Auto-generated method stub
        String name = "SYSDBA";
        String string = "123456";
        // 达梦数据库保存的密码
        String pwdjiami = "__0Sk+wY\\R7Du]Ni^&Vp~V|/w{3V~R3~5Bo-Pqs1uNT2&Q91"; // 达梦7
//        String pwdjiami ="K_\B&TBU=Tv\=~)n~IE3I@^~`{nK7,|3+N63t[CY?[A>m3p.";// 达梦6

        byte[] jiamiByte = initDmJiami(pwdjiami);
        System.out.println(new String(jiamiByte));
        byte[] pwdByte = b(string, name);// 加密原始密码
        System.out.println(new String(pwdByte));
    }

    private static byte[] initDmJiami(String pwdjiami) {
        byte[] arrayOfByte = new byte[pwdjiami.length()];
        for (int i = 0; i < pwdjiami.length(); i++) {
            arrayOfByte[i] = ((byte) pwdjiami.charAt(i));
        }
        return arrayOfByte;
    }

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
}
