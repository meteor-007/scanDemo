package cnvd.反编译;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 将Class文件解密
 *
 * @author zml
 * @date 2018-7-3
 */
public class ClassDecrypt {

    /**
     * 待加密的class文件位置
     */
    static String srcPath = "F:\\git-项目\\Vulnerability-scanning\\src\\main\\webapp\\WEB-INF\\classes";

    /**
     * 加密后的class文件保存位置
     */
    static String destPath = "F:\\git-项目\\Vulnerability-scanning\\src\\main\\webapp\\WEB-INF\\classes1";

    /**
     * 保存所有文件List
     */
    static List<File> fileList = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File(srcPath);
        queryFilePath(file);
        System.out.println(fileList);
    }

    /**
     * 获取所有待加密文件的路径
     *
     * @param file 文件夹
     */
    public static void queryFilePath(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                queryFilePath(f);
            }
            if (f.isFile()) {
                fileList.add(f);
            }
        }
    }


//    public static byte[] queryFilePath(File file) {
//        File[] files = new File(srcPath).listFiles();
//        for (File f : files) {
//
//        }
//
//
//        try (FileInputStream fis = new FileInputStream(fileName);
//             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//            byte[] bytes = new byte[1024];
//            int hasRead = fis.read(bytes);
//            while (hasRead > 0) {
//                for (byte b : bytes) {
//                    baos.write(b ^ 0xff);
//                }
//                hasRead = fis.read(bytes);
//            }
//            return baos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}