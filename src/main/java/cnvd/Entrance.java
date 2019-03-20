package cnvd;

import java.util.ArrayList;
import java.util.List;

/**
 * 入口类
 *
 * @author zml
 * @date 2018-7-24
 */
public class Entrance {


    public static void main(String[] args) {

        // 采集URL，并将采集到的URL保存到指定文件中
        getUrl();

        // 解析采集到的url对应的网页，并把解析到的数据保存到数据库中
        saveInfo();
    }

    /**
     * 采集URL，并将结果保存在指定文件中
     */
    public static void getUrl() {
        GetUrl getUrl = new GetUrl();
        getUrl.gainOldUrl();
        String url = Constant.URL.replace("{?}", Constant.MAX.toString());
        for (int i = 0; i < Constant.TOTAL; i++) {
            Integer start = i * Constant.MAX;
            getUrl.gainUrl(url.replace("{#}", start.toString()));
            System.out.println("已完成第" + (i + 1) + "页的URL采集工作。");
        }
        getUrl.saveUrl();
        System.out.println("已将所有采集到的URL保存到文件中。");
    }

    /**
     * 解析采集到的url对应的网页，并把解析到的数据保存到数据库中
     */
    private static void saveInfo() {
        SaveInfo saveInfo = new SaveInfo();
        List<String> urlList = new ArrayList<>();
        saveInfo.gainUrl(urlList);
        for (String url : urlList) {
            CnvdDb cnvdDb = saveInfo.gainInfo(url);
            while (Constant.FLAG) {
                Constant.FLAG = false;
                cnvdDb = saveInfo.gainInfo(url);
            }
            if (cnvdDb != null) {
                saveInfo.saveInfo(cnvdDb);
            }
        }
    }
}