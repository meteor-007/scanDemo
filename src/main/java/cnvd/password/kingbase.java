package cnvd.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class kingbase {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String name = "SYSTEM";
		String string = "Ceshi123";
		String pwdjiami = "md5a89b94e43b383ce3f621d6227829548f";// 人大金仓数据库保存的密码
		byte[] jiamiByte = pwdjiamiToByte(pwdjiami.substring(3));
		System.out.println(new String(jiamiByte));
		byte[] var2 = (string + name).getBytes();
		byte[] jiamipwd = jiami("MD5", var2);
		System.out.println(new String(jiamipwd));
		byteToString(jiamipwd);
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
