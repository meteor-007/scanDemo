package cnvd.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Oracle2 {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String string = "Ceshi123";
		// S:无用信息,42位之后为随机值
		String pwd = "S:F87BE4C1463EECA763BCA7497EDDAA988B461764258521A6AA4D5F14D1E5";
		byte[] a = pwdjiamiToByte(pwd.substring(2, 42));
		byteToString(a);
		String str = pwd.substring(42, pwd.length());// 获得随机值

		byte[] paramString1 = string.getBytes();
		byte[] paramString2 = pwdjiamiToByte(str);
		byte[] arrayOfByte = new byte[paramString1.length + paramString2.length];
		System.arraycopy(paramString1, 0, arrayOfByte, 0, paramString1.length);
		System.arraycopy(paramString2, 0, arrayOfByte, paramString1.length,
				paramString2.length);
		byte[] b = jiami("SHA-1", arrayOfByte);
		byteToString(b);
	}

	private static void byteToString(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(Integer.toHexString((a[i] & 0x000000FF) | 0xFFFFFF00).substring(6));
		}
		System.out.println(sb.toString().toUpperCase());
	}
	
	private static byte[] jiami(String way, byte[] arrayOfByte)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(way);
		md.reset();
		md.update(arrayOfByte);
		byte[] b = md.digest();
		return b;
	}
	
	public static byte[] pwdjiamiToByte(String paramString) {
		if (paramString == null) {
			return null;
		}
		int i = paramString.length();
		if (i  % 2== 1) {
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
