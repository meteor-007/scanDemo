package file;

import java.io.*;

/**
 * 文件分割，合并
 *
 * @Auther: czwei
 * @Date: 2019/3/2 10:48
 */
public class FileSliptUtil {

    static void splitFileDemo(File src, int m) {
        if (src.isFile()) {
            //获取文件的总长度
            long l = src.length();
            //获取文件名
            String fileName = src.getName().substring(0, src.getName().indexOf("."));
            //获取文件后缀
            String endName = src.getName().substring(src.getName().lastIndexOf("."));
            System.out.println(endName);
            try (InputStream in = new FileInputStream(src)) {
                for (int i = 1; i <= m; i++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(src.getParent());
                    sb.append("\\");
                    sb.append(fileName);
                    sb.append("-data");
                    sb.append(i);
                    sb.append(endName);
                    System.out.println(sb.toString());
                    File file2 = new File(sb.toString());
                    //创建写文件的输出流
                    OutputStream out = new FileOutputStream(file2);
                    int len = -1;
                    byte[] bytes = new byte[10 * 1024 * 1024];
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                        if (file2.length() > (l / m)) {
                            break;
                        }
                    }
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("--- 文件分割完成 ---");
        }
    }

    /**
     * @param src 合并文件路径
     * @Description 文件合并的方法 改进后的方法
     */
    public static void joinFileDemo(String[] src) {
        //    获取合并文件
        File newFile = new File(src[0].toString());
        //    获取文件名    后缀
        String fileName = newFile.getName().substring(0, newFile.getName().indexOf("_"));
        String endName = newFile.getName().substring(newFile.getName().lastIndexOf("."));
        //    得到新的文件名
        StringBuilder sb = new StringBuilder();
        sb.append(newFile.getParent());
        sb.append("\\");
        sb.append(fileName);
        sb.append(endName);
        newFile = new File(sb.toString());
        for (String s : src) {
            File file = new File(s);
            try {
                //读取小文件的输入流
                InputStream in = new FileInputStream(file);
                OutputStream out = new FileOutputStream(newFile, true);
                int len = -1;
                byte[] bytes = new byte[10 * 1024 * 1024];
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                out.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件合并完成！");
    }

}
