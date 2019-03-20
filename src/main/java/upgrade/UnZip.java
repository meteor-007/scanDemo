package upgrade;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

/**
 * @description:
 * @author: czwei
 * @create: 2019-01-24 15:20
 */
public class UnZip {

    /**
    *@Description: 解压文件夹
    *@Param:
    *@return:
    *@Author: czwei
    *@date: 2019/1/24
    */
    public static void main(String[] args) throws IOException {

        String source = "D:\\数据库漏洞扫描\\VSlib20190124_001.zip";
        String dest = "D:\\数据库漏洞扫描\\数据库漏洞扫描\\";
        String password = "password";
        try {
            File zipFile = new File(source);
            // 首先创建ZipFile指向磁盘上的.zip文件  
            ZipFile zFile = new ZipFile(zipFile);
            zFile.setFileNameCharset("GBK");
            // 解压目录 
            File destDir = new File(dest);
            if (zFile.isEncrypted()) {
                // 设置密码 
                zFile.setPassword(password.toCharArray());
            }
            // 将文件抽出到解压目录(解压) 
            zFile.extractAll(dest);
            List<FileHeader> headerList = zFile.getFileHeaders();
            List<File> extractedFileList = new ArrayList<File>();
            for (FileHeader fileHeader : headerList) {
                if (!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
                }
            }
            File[] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);
            for (File f : extractedFileList) {
                System.out.println(f.getAbsolutePath() + "....");
            }

        } catch (ZipException e) {

        }
    }
}
