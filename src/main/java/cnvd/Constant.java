package cnvd;

/**
 * 常量配置
 *
 * @author zml
 * @date 2018-7-24
 */
public class Constant {

    /**
     * 进行URL采集的网页的总页数
     */
    public static Integer TOTAL = 93;

    /**
     * 进行URL采集的网页的每页展示条数，默认20条
     */
    public static Integer MAX = 20;

    /**
     * 进行URL采集的网页的URL地址
     * 其中typeId是漏洞类型，30代表数据库漏洞
     * offset是该业第一条数据的序号，从0开始，第一页是0，第二页是20，第三页是40……
     */
    public static String URL = "http://www.cnvd.org.cn/flaw/typeResult?typeId=30&max={?}&offset={#}";

    /**
     * 将所有采集到的URL保存到文件
     */
    public static String URL_FILE_PATH = "E:\\VBLibrary\\file\\CNVD_URLS.txt";

    /**
     * 数据库IP地址
     */
    public static String IP = "172.19.1.11";

    /**
     * 数据库端口号
     */
    public static String PORT = "3306";

    /**
     * 数据库用户名
     */
    public static String USER = "root";

    /**
     * 数据库密码
     */
    public static String PASSWORD = "Ankki_mySQL123";

    /**
     * 待操作的数据库名
     */
    public static String DB_NAME = "aas-vs";

    /**
     * 获取漏洞库里已经存在的URL
     */
    public static String QUERY_URL_SQL = "SELECT url FROM cnvd_db";

    /**
     * 待执行的插入语句模板
     */
    public static String INSERT_SQL = "INSERT INTO cnvd_db VALUES('0',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 是否成功解析到网页数据标志，为false时表示已成功解析到网页，将解析到的数据入库，true表示解析时发生异常，需要再次进行解析
     * 为了解决有些网页由于网络原因会偶尔访问异常，只要访问异常，则还要再次执行
     */
    public static Boolean FLAG = false;

}
