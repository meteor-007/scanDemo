package upgrade;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @description: 文件下载
 * @author: czwei
 * @create: 2019-01-04 14:59
 */
public class DownloadFile {

    public static void downloadFiles(String fileName, String tmpFileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(fileName) && StringUtils.isEmpty(tmpFileName)) {
            return;
        }
        File file = new File(tmpFileName);
        if (!file.exists()) {
            return;
        }

        String postfix = tmpFileName.substring(tmpFileName.lastIndexOf("."));
        response.reset();
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8") + postfix + "\"");
        } else {
            // 下载的文件名显示编码处理
            fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + postfix + "\"");
        }
        response.setContentType("application/x-msdownload;charset=UTF-8");
        FileInputStream fis = new FileInputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

        byte[] buffer = new byte[2048];
        int readlength = 0;
        while ((readlength = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, readlength);
        }
        try {
            fis.close();
        } catch (IOException e) {
        }
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
//        downloadFiles();
    }




}
