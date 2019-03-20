package cnvd.反编译;
import java.io.*;

/**
 * 将Class文件加密
 *
 * @author zml
 * @date 2018-7-3
 */
public class ClassEncrypt {

    public static void main(String[] args){
        encrypt(" F:\\git-项目\\Vulnerability-scanning\\src\\main\\java\\cnvd\\反编译\\HelloWorld.java");
    }

    /**
     * 文件加密
     * @param fileName 要加密文件的绝对路径
     */
    public static void encrypt(String fileName){

        try(FileInputStream fis = new FileInputStream(fileName);
            FileOutputStream fos = new FileOutputStream(fileName+".class")) {
            byte[] bytes = new byte[1024];
            int hasRead = fis.read(bytes);
            while(hasRead>0){
                for (byte b:bytes) {
                    fos.write(b^0xff);
                }
                hasRead = fis.read(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}