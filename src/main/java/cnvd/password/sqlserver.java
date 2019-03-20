package cnvd.password;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sqlserver {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

		// String pwdjiami =
		// "01004086CEB628AA51DD7E821560D52C6A6B5DC187421C6E8057";//2005 123456
//		String pwdjiami = "010056049B0EEC4467EE48EEA24126BA0AB59DBD26BBC19CA6CB";// 2008
		String string = "01004086CEB61216CB813DA6870A6FCD0CF43F3B1D5D40713A8A31";
		string = string.replaceAll("\\-", "").substring(4, 12);
		System.out.println(string);
		byte[] jiamiafter = sqlserverjiami(string, "Ceshi123");
		byteToString(jiamiafter);
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
	/**
	 * sqlserver加密方法SHA-1
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
