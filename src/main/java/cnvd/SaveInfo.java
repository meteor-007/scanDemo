package cnvd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 解析采集到的url对应的网页，并把解析到的数据保存到数据库中
 *
 * @author zml
 * @date 2018-7-24
 */
public class SaveInfo {

    /**
     * 读取本地文件中保存的url
     *
     * @param urlList 将读取到的url保存到urlList中
     */
    public void gainUrl(List<String> urlList) {
        try (FileReader reader = new FileReader(new File(Constant.URL_FILE_PATH));
             BufferedReader br = new BufferedReader(reader)) {
            String string = null;
            while ((string = br.readLine()) != null) {
                if (string.length() > 0) {
                    urlList.add(string);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析指定url对应的页面，并将结果返回
     *
     * @param url 待解析的url
     * @return 解析到的数据
     */
    public CnvdDb gainInfo(String url) {
        try {

            // 获取该url请求的response
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // 将response中的数据放到StringBuffer中
            StringBuffer stringBuffer = new StringBuffer();
            try (BufferedReader br = new BufferedReader(inputStreamReader);) {
                String string = null;
                while ((string = br.readLine()) != null) {
                    stringBuffer.append(string);
                }
            }

            // response数据相当于HTML文件，因此将其转换成Document，解析数据，将解析到的数据放到cnvdDb对应的属性中
            CnvdDb cnvdDb = new CnvdDb();
            cnvdDb.setUrl(url);

            Document doc = Jsoup.parse(stringBuffer.toString());
            Elements tagElement = doc.getElementsByTag("h1");
            cnvdDb.setTitle(tagElement.text());

            tagElement = doc.getElementsByClass("gg_detail");
            int size = tagElement.select("tr").size();
            for (int i = 0; i < size; i++) {
                Elements tr = tagElement.select("tr").get(i).select("td");
                if (tr.size() > 1) {
                    String name = tr.get(0).text();
                    String value = tr.get(1).text();
                    switch (name) {
                        case "CNVD-ID":
                            cnvdDb.setCnvdId(value);
                            break;
                        case "公开日期":
                            cnvdDb.setReleaseTime(value);
                            break;
                        case "危害级别":
                            cnvdDb.setHazardLevel(value);
                            break;
                        case "影响产品":
                            cnvdDb.setAffectedProduct(value);
                            break;
                        case "CVE ID":
                            cnvdDb.setCveId(value);
                            cnvdDb.setCveLink(tr.get(1).select("a").attr("href"));
                            break;
                        case "漏洞描述":
                            cnvdDb.setDescription(value);
                            break;
                        case "参考链接":
                            cnvdDb.setReferenceLink(value);
                            break;
                        case "漏洞解决方案":
                            cnvdDb.setSolution(value);
                            break;
                        case "厂商补丁":
                            cnvdDb.setPatch(value);
                            cnvdDb.setPatchLink("http://www.cnvd.org.cn" + tr.get(1).select("a").attr("href"));
                            break;
                        case "验证信息":
                            cnvdDb.setVerifyMessage(value);
                            break;
                        case "报送时间":
                            cnvdDb.setReportingTime(value);
                            break;
                        case "收录时间":
                            cnvdDb.setInclusionTime(value);
                            break;
                        case "更新时间":
                            cnvdDb.setUpdateTime(value);
                            break;
                        case "漏洞附件":
                            cnvdDb.setAnnex(value);
                            break;
                        case "BUGTRAQ ID":
                            cnvdDb.setBugtraqId(value);
                            cnvdDb.setBugtraqLink(tr.get(1).select("a").attr("href"));
                            break;
                        case "其他 ID":
                            cnvdDb.setOtherId(value);
                            break;
                    }
                }
            }

            Element element = doc.getElementById("showDiv");
            Elements tds = element.select("td");
            size = tds.size();
            for (int i = 0; i < size; i++) {
                String name = tds.get(i).select("span").text();
                String value = tds.get(i).text().replace(name, "");
                switch (name) {
                    case "攻击途径：":
                        cnvdDb.setAttackRoute(value);
                        break;
                    case "攻击复杂度：":
                        cnvdDb.setAttackComplexity(value);
                        break;
                    case "认证：":
                        cnvdDb.setCertification(value);
                        break;
                    case "机密性：":
                        cnvdDb.setConfidentiality(value);
                        break;
                    case "完整性：":
                        cnvdDb.setIntegrity(value);
                        break;
                    case "可用性：":
                        cnvdDb.setAvailability(value);
                        break;
                }
            }

            String value = element.select("div").get(1).text().replaceAll("[^0-9\\.]", "");
            if (value != null && value.length() > 0) {
                cnvdDb.setScore(Float.valueOf(value));
            }
            return cnvdDb;
        } catch (Exception e) {
            e.printStackTrace();
            Constant.FLAG = true;
            return null;
        }
    }

    /**
     * 将解析到的数据保存到数据库中
     *
     * @param cnvdDb 待入库的数据
     */
    public void saveInfo(CnvdDb cnvdDb) {
        MySQLUtil mySQLUtil = new MySQLUtil();
        mySQLUtil.insertInfo(cnvdDb);
    }
}
