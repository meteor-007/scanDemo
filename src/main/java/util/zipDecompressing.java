package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

//使用org.apache.tools.zip这个就不会中文乱码
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * @author Dana·Li
 * <p>
 * 程序实现了ZIP解压[decompression]
 * <p>
 * 大致功能包括用了多态，递归等JAVA核心技术，可以对单个文件和任意级联文件夹进行压缩和解压。 需在代码中自定义源输入路径和目标输出路径。
 * <p>
 * 在本段代码中，实现的是解压部分；
 */
public class zipDecompressing {
    static String zipPath = "C:\\Users\\Administrator\\Desktop\\Test.zip";//需要解压的压缩文件
    static String outPath = "C:\\Users\\Administrator\\Desktop\\";//解压完成后保存路径,记得"\\"结尾哈

    public static void main(String args[]) throws Exception {
        ZipFile zipFile = new ZipFile(zipPath, "GBK");//压缩文件的实列,并设置编码
        //获取压缩文中的所以项
        for (Enumeration<ZipEntry> enumeration = zipFile.getEntries(); enumeration.hasMoreElements(); ) {
            ZipEntry zipEntry = enumeration.nextElement();//获取元素
            //排除空文件夹
            if (!zipEntry.getName().endsWith(File.separator)) {
                System.out.println("正在解压文件:" + zipEntry.getName());//打印输出信息
                //创建解压目录
                File f = new File(outPath + zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf(File.separator)));
                //判断是否存在解压目录
                if (!f.exists()) {
                    f.mkdirs();//创建解压目录
                }
                OutputStream os = new FileOutputStream(outPath + zipEntry.getName());//创建解压后的文件
                BufferedOutputStream bos = new BufferedOutputStream(os);//带缓的写出流
                InputStream is = zipFile.getInputStream(zipEntry);//读取元素
                BufferedInputStream bis = new BufferedInputStream(is);//读取流的缓存流
                CheckedInputStream cos = new CheckedInputStream(bis, new CRC32());//检查读取流，采用CRC32算法，保证文件的一致性
                byte[] b = new byte[1024];//字节数组，每次读取1024个字节
                //循环读取压缩文件的值
                while (cos.read(b) != -1) {
                    bos.write(b);//写入到新文件
                }
                cos.close();
                bis.close();
                is.close();
                bos.close();
                os.close();
            } else {
                //如果为空文件夹，则创建该文件夹
                new File(outPath + zipEntry.getName()).mkdirs();
            }
        }
        System.out.println("解压完成");
        zipFile.close();
    }

}
