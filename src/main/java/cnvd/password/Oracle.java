package cnvd.password;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Oracle {

	private static final byte[] a = { 1, 35, 69, 103, -119, -85, -51, -17 };
	private static SecretKey b = new SecretKeySpec(a, "DES");
	private static final IvParameterSpec c = new IvParameterSpec(new byte[8]);
	
	public static void main(String[] args) throws Exception {
		String name = "sys";
		String string = "Ceshi123";
		byte[] aa = (name + string).toUpperCase().getBytes("utf-16be");
		byte[] paramString1 = Arrays.copyOf(aa, (aa.length + 7) / 8 << 3);
		byte[] paramString2 = a(paramString1, b);

		SecretKey sks = new SecretKeySpec(paramString2,
				paramString2.length - 8, 8, "DES");
		byte[] bb = a(paramString1, sks);
		byte[] cc = new byte[8];
		System.arraycopy(bb, bb.length - 8, cc, 0, 8);
		byteToString(cc);

	}

	private static void byteToString(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(Integer.toHexString((a[i] & 0x000000FF) | 0xFFFFFF00).substring(6));
		}
		System.out.println(sb.toString().toUpperCase());
	}
	
	protected final static byte[] a(byte[] arg1, SecretKey paramKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher localCipher;
		localCipher = Cipher.getInstance("DES/CBC/NoPadding");
		localCipher.init(1, paramKey, c);
		byte[] b = localCipher.doFinal(arg1);
		return b;
	}
	
}
