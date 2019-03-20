package upgrade;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * @description:
 * @author: czwei
 * @create: 2019-01-04 11:57
 */
public class FileTool {



    public static void main(String[] args) throws IOException {
        // 实现文本内容替换(文件内容加密了，没有权限打开)
        autoReplace("D:\\数据库漏洞扫描\\test.txt","D:\\数据库漏洞扫描\\test2.txt");


        //
        String jarPath = "D:\\数据库漏洞扫描\\dz-1.0-SNAPSHOT-b3540.war";
        String sourcePath = "D:\\数据库漏洞扫描\\sysconfig.properties";
        String destPath = "config/sysconfig.properties";
        try {
            replaceFile(jarPath, sourcePath, destPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private static void autoReplace(String filePath, String outPath) throws IOException {
        File file = new File(filePath);
        Long fileLength = file.length();
        byte[] fileContext = new byte[fileLength.intValue()];
        FileInputStream in = new FileInputStream(filePath);
        in.read(fileContext);
        in.close();
        String str = new String(fileContext);

        str = str.replace("", "");

        PrintWriter out = new PrintWriter(outPath);
        out.write(str.toCharArray());
        out.flush();
        out.close();
    }


    /**
     *
     * @param jarPath
     *            jar包所在绝对路径
     * @param sourcePath
     *            confPath配置文件绝对路径
     * @param destPath
     *            配置文件jar包 位置config/sysconfig.properties
     * @throws IOException
     */
    public static void replaceFile(String jarPath, String sourcePath, String destPath) throws IOException {
        String jarName = jarPath.substring(jarPath.lastIndexOf(File.separator), jarPath.lastIndexOf("."));
        File file = new File(jarPath);
        File destFile = new File(jarPath.substring(0, jarPath.lastIndexOf(File.separator)) + jarName + "_cp.jar");
        file.renameTo(destFile);// 将jar文件名重命名为jarName_cp.jar

        JarFile jarFile = null;
        InputStream in = null;
        JarOutputStream out = null;
        try {
            jarFile = new JarFile(destFile);
            out = new JarOutputStream(new FileOutputStream(file));
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                InputStream in_ = null;
                try {
                    String jarEntryName = jarEntry.getName();
                    System.out.println(jarEntryName);
                    if (destPath.equals(jarEntryName)) {
                        continue;
                    }
                    in_ = jarFile.getInputStream(jarEntry);
                    out.putNextEntry(jarEntry);
                    copyFile(in_, out);
                } finally {
                    if (in_ != null) {
                        try {
                            in_.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
            JarEntry jarEntry = new JarEntry(destPath);
            out.putNextEntry(jarEntry);
            in = new FileInputStream(new File(sourcePath));
            copyFile(in, out);

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                }
            }
        }

        destFile.delete();
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        int length = 2097152;
        byte[] buffer = new byte[length];
        int len = 0;
        while ((len = in.read(buffer)) > -1) {
            out.write(buffer, 0, len);
        }
    }



    public static void downloadByNIO2(String serverURL, String s, String jarName) {
    }
}
