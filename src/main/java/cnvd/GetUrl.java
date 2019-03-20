package cnvd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 采集URL，并将采集到的URL保存到指定文件中
 *
 * @author zml
 * @date 2018-7-24
 */
public class GetUrl {

    /**
     * 保存漏洞库里已经存在的URL
     */
    Set<String> oldUrlSet = new HashSet<>();

    /**
     * 保存所有采集到的URL
     */
    List<String> urlList = new ArrayList<>();

    /**
     * 获取漏洞库里已经存在的URL
     * 这些URL对应网站的信息已经解析入库，因此不再保存这些URL到本地文件，即不再对这些URL进行解析
     */
    public void gainOldUrl() {
        MySQLUtil mySQLUtil = new MySQLUtil();
        mySQLUtil.queryUrlSet(oldUrlSet);
    }

    /**
     * 采集当前页面下的所有URL，并保存到urlList中
     *
     * @param url 当前进行URL采集的网页的URL地址
     */
    public void gainUrl(String url) {
        try {

            // 获取该url请求的response
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            try (BufferedReader br = new BufferedReader(inputStreamReader);) {

                // 将response中的数据放到StringBuffer中
                StringBuffer stringBuffer = new StringBuffer();
                String string = null;
                while ((string = br.readLine()) != null) {
                    stringBuffer.append(string);
                }

                // response数据相当于HTML文件，因此将其转换成Document，解析数据，采集url
                Document doc = Jsoup.parse(stringBuffer.toString());
                Elements tagElement = doc.getElementsByTag("tbody");
                int size = tagElement.select("tr").size();
                for (int i = 0; i < size; i++) {
                    String currentUrl = "http://www.cnvd.org.cn" + tagElement.select("tr").get(i).select("a").attr("href");
                    if (!oldUrlSet.contains(currentUrl)) {
                        // 这些URL对应网站的信息还未解析入库，因此保存这些URL到本地文件，即只对这些URL进行解析
                        urlList.add(currentUrl);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将所有采集到的URL保存到文件中
     */
    public void saveUrl() {
        try (FileWriter writer = new FileWriter(new File(Constant.URL_FILE_PATH));
             BufferedWriter bw = new BufferedWriter(writer);) {
            for (String url : urlList) {
                bw.write(url + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
