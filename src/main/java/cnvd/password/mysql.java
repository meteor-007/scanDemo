package cnvd.password;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class mysql {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		byte[] b = pwdjiamiToByte("23AE809DDACAF96AF0FD78ED04B6A265E05AA257"); // 两次sha1加密
		System.out.println(new String(b, "utf-8"));
		byte[] a = jiami("SHA1", "123".getBytes());
		byte[] c = jiami("SHA1", a);
		String s = byteToString(c);
		System.out.println(s);
	}
	private static byte[] jiami(String way, byte[] arrayOfByte)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(way);
		md.reset();
		md.update(arrayOfByte);
		byte[] b = md.digest();
		return b;
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
			sb.append(Integer.toHexString((v & 0x000000FF) | 0xFFFFFF00).substring(6));
		}
		return sb.toString().toUpperCase();
	}

	public static byte[] pwdjiamiToByte(String paramString) {
		if (paramString == null) {
			return null;
		}
		int i = paramString.length();
		if (i% 2 == 1) {
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
}
