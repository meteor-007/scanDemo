package cnvd.password;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FilePassword {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		try {
			FileReader reader = new FileReader("D:\\数据库漏洞扫描\\漏扫资料\\dictionary.txt");
			BufferedReader br = new BufferedReader(reader);
			StringBuffer result = new StringBuffer();
			String s = br.readLine();
			while (s!=null) {
				byte[] a = jiami("SHA1", s.getBytes());
				byte[] c = jiami("SHA1", a);
				s = byteToString(c);
				result.append("*").append(s).append("\r\n");
				s= br.readLine();
			}
			br.close();
			reader.close();
			FileWriter writer = new FileWriter("D:\\数据库漏洞扫描\\漏扫资料\\mysql.txt");
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(result.toString());
			bw.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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

}
