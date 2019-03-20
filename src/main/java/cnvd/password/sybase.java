package cnvd.password;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sybase {

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		String string="";
		String pwdjiami = "c0077852792bd3e354ac68ff7472459d0f76260074c4b7e0ec1c4f21dd4c224adbb9a71fa4621eabd452";// 十六进制
		// 两位代表一个字符，前2到10位为变值第11位开始时有效密码加密即从68ff74....
		byte[] jiamiByte = initSysbaseJiami(pwdjiami);
		System.out.println(new String(jiamiByte));
		byteToString(jiamiByte);
//		System.out.println(jiamiByte);
		byte[] key = initKey(pwdjiami);
		byte[] pwdbyte = string.getBytes("UTF-16BE");
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

	
	private static byte[] jiami(String way, byte[] arrayOfByte)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(way);
		md.reset();
		md.update(arrayOfByte);
		byte[] b = md.digest();
		return b;
	}
	
	
	private static void byteToString(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(Integer.toHexString((a[i] & 0x000000FF) | 0xFFFFFF00).substring(6));
		}
		System.out.println(sb.toString().toUpperCase());
	}
}
