package cnvd.weakpwdscan;

//import com.ankki.cfcmms.dao.DatabaseConfigMapper;
//import com.ankki.cfcmms.filters.InitConnect;
//import com.ankki.cfcmms.model.DatabaseConfig;
//import com.ankki.cfcmms.util.dbconnect.CreateDbConnect;
//import net.sf.json.JSONObject;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统工具类
 *
 * @author dqy
 * @date 2016/6/1
 */
public class SysUtils {

    private static Logger log = LoggerFactory.getLogger(SysUtils.class);
    private static String INSTALL_PATH = "";

    @Resource
//    private static DatabaseConfigMapper databaseConfigDao;
    /**
     * 用于分割的字符串
     */
    public static final String SPLIT = ",";

    private SysUtils() {
        throw new IllegalAccessError("SysUtils class");
    }

    /**
     * 获取tomcat的安装路径.
     * @return tomcat 安装路径
     */
    public static String getInstallPath() {
        if (StringUtils.hasLength(INSTALL_PATH)) {
            return INSTALL_PATH;
        } else {
            String rootPath = SysUtils.class.getClassLoader().getResource("").getPath();
            int index = rootPath.indexOf("WEB-INF");
            if (index == -1) {
                INSTALL_PATH = rootPath;
            } else {
                INSTALL_PATH = rootPath.substring(0, index) + File.separator;
            }
            log.info("获取到系统的安装路径为：" + INSTALL_PATH);
        }
        return INSTALL_PATH;
    }

    public static Object getClassObj(Class refClass) {
        Class<T> operObj = null;
        Type type = refClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            operObj = (Class<T>) types[0];
        }
        return operObj;
    }
    /**
     * 获取Mongodb相关结果，格式化数据
     * @param key key
     * @param map map
     * @return 结果
     * @author zmling
     */
    public static String queryMongodbValue(String key, Map<String, Object> map) {
        key = key.replaceAll("\r", "").replaceAll("\n", "");
        String value = "";
        String[] keys = key.split("\\.");
        for (int i = 0; i < keys.length; i++) {
            String name = keys[i];
            if (i < keys.length - 1) {
                if (map.containsKey(name)) {
                    map = (Map) map.get(name);
                } else {
                    return "0";
                }
            } else {
                if (map.containsKey(name)) {
                    value = map.get(name).toString();
                } else {
                    value = "0";
                }
                break;
            }
        }
        String bytesIn = "bytesIn";
        String bytesOut = "bytesOut";
        if (key.indexOf(bytesIn) != -1 || key.indexOf(bytesOut) != -1) {
            //格式化为MB单位，并保留小数点后两位
            BigDecimal bg = BigDecimal.valueOf(Double.parseDouble(value) / 1024);
            value = String.valueOf((bg.setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue());
        }
        return value;
    }
    /**
     * 获取redis相关结果,格式化数据
     * @param infos        查询信息
     * @param key2ValueMap 结果集
     */
    public static void queryRedisKey2ValeMap(String infos, Map<String, Object> key2ValueMap) {
        String[] infoList = infos.split("\r\n");
        for (String key2Value : infoList) {
            if ((key2Value.length() > 0) && (!key2Value.startsWith("#"))) {
                int index = key2Value.indexOf(':');
                String key = key2Value.substring(0, index);
                String value = key2Value.substring(index + 1);
                key2ValueMap.put(key, value);
            }
        }
    }
    /**
     * 获取HBase name与具体信息对应关系
     * @param infos         查询信息
     * @param name2ValueMap 结果集
     */
    public static void queryHbaseName2ValeMap(String infos, Map<String, String> name2ValueMap) {
        StringBuilder stringBuffer = new StringBuilder();
        int flag = 0;
        for (int i = 1; i < infos.length() - 1; i++) {
            char ch = infos.charAt(i);
            stringBuffer.append(ch);
            if (ch == '{') {
                flag++;
            }
            if (ch == '}') {
                flag--;
            }
            if ((flag == 0) && (stringBuffer.length() != 0)) {
                i++;
                String value = stringBuffer.toString();
                String name = "\"name\":\"";
                int index1 = value.indexOf(name);
                String temp = value.substring(index1);
                int index2 = temp.substring(name.length()).indexOf('\"');
                String key = temp.substring(0, name.length() + index2 + 1);
                name2ValueMap.put(key, value);
                stringBuffer = new StringBuilder();
            }

        }
    }

    /**
     * HBase 通过KEY获取value
     * @param info 查询信息
     * @param key  属性名
     */
    public static String queryHbaseValueByKey(String info, String key) {
        int index = info.indexOf(key);
        if (index == -1) {
            return null;
        }
        info = info.substring(index + key.length());
        index = info.indexOf('\"');
        int index1 = info.indexOf(',');
        if ((index != -1) && (index1 != -1) && (index1 < index)) {
            index = index1;
        }
        if ((index == -1) && (index1 == -1)) {
            index = info.indexOf('}');
        }
        info = info.substring(0, index);
        return info;
    }
    /**
     * HBase 通过KEY获取value列表，适用于嵌套多层的情况
     * @param info 查询信息
     * @param key  属性名
     */
    public static Map<String, String> queryHbaseValueListByKey(String info, String key) {
        int index = info.indexOf(key);
        if (index == -1) {
            return null;
        }
        info = info.substring(index + key.length() + 1);
        index = info.indexOf('}');
        info = info.substring(0, index).replaceAll("\"", "");
        Map<String, String> map = new HashMap<>(16);
        for (String string : info.split(SPLIT)) {
            index = string.indexOf(":");
            map.put(string.substring(0, index), string.substring(index + 1));
        }
        return map;
    }
    /**
     * 获取数据库连接工具类
     *
     * @param config 数据库配置信息
     * @param close  是否关闭这个连接，true关闭（测试连接时不将该连接put到MAP中，并关闭该连接），false不关闭
     * @return 连接信息
     */
    public static Object queryDbConnectInfo(DatabaseConfig config, Boolean close) {
        String dbAlias = config.getDbAlias();
        // 先利用别名从MAP中获取
        Object conn = InitConnect.DB_CONNECT_MAP.get(dbAlias);
        if (conn == null) {
            // 从MAP中获取到的连接为null或该别名的连接未在MAP中，此时应尝试重新创建连接
            log.info("数据库{}的连接为null，准备重新创建连接。", dbAlias);
            //关闭连接  参考博客说的，连接池关闭连接仍然存在  重新赋值为null
            CreateDbConnect.closeConnect(InitConnect.DB_CONNECT_MAP.get(dbAlias));
            boolean result = CreateDbConnect.createConnect(config, close);
            if (!result) {
                log.warn("数据库{}获取连接失败", dbAlias);
                return null;
            } else if (close) {
                // 此时获取数据库连接成功，但是这是个连接已关闭，是个临时连接，此时返回success字符串
                return "success";
            }
//            if (result) {
//                GetMonitorStateUtil getMonitorStateUtil = new GetMonitorStateUtil();
//                //重新创建数据库连接成功时，更新数据库版本信息
//                getMonitorStateUtil.updateDbVersionByAlias(config, databaseConfigDao, conn);
//            }
        } else {
            // 从MAP中获取到相关数据库的连接，要先判断该连接是否依然可用
            log.info("重新校验MAP中的数据库{}的连接是否可用。", dbAlias);
            // close为true证明是测试连接，此时不应该用MAP中的数据，而是通过前台传参判断该临时配置信息是否可用
            //result 为false表示连接不可用
            Boolean result = false;
            if (!close) {
                try {
                    result = CreateDbConnect.testConnect(config);
                } catch (SQLException e) {
                    log.error("{}数据库测试连接失败", dbAlias);
                }
            }
            if (!result) {
                // 此时MAP中的连接已经不可用，要重新创建连接
                log.info("数据库{}的连接已不可用，需重新创建连接。", dbAlias);
                boolean flag = CreateDbConnect.createConnect(config, close);
                if (!flag) {
                    log.warn("数据库{}重新获取连接失败", dbAlias);
                    return null;
                } else if (close) {
                    // 此时获取数据库连接成功，但是这是个连接已关闭，是个临时连接，此时返回success字符串
                    return "success";
                }
            }
        }
        // 返回MAP中最新的连接
        return InitConnect.DB_CONNECT_MAP.get(dbAlias);
    }


    /**
     * 查询HBase相关信息
     *
     * @param databaseConfig 数据库配置信息
     * @return 相关监控状态
     */
//    public static String queryHbaseInfo(DatabaseConfig databaseConfig) {
//        String alias = databaseConfig.getDbAlias();
//        String url = "http://" + databaseConfig.getDbIp() + ":" + databaseConfig.getDbPort() + "/jmx";
//        HttpGet get = new HttpGet(url);
//        // 使用jdk1.7新特性关闭HTTP请求  解决连接过多问题
//        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
//            CloseableHttpResponse response = httpClient.execute(get);
//            return JSONObject.fromObject(EntityUtils.toString(response.getEntity())).getString("beans");
//        } catch (IOException e) {
//            log.error("查询HBase数据库{}相关信息时发生未知异常", alias, e);
//            return null;
//        }
//    }

    /**
     * 查询Redis相关信息
     *
     * @param resultMap 数据库配置信息
     * @return redis属性值
     */
    public static void pullRedisResult(Map<String, String> resultMap, String[] strs) {
        if ((strs != null) && (strs.length > 0)) {
            for (int i = 0; i < strs.length; i++) {
                String s = strs[i];
                String[] str = s.split(":");
                if ((str != null) && (str.length > 1)) {
                    String key = str[0];
                    String value = str[1];
                    resultMap.put(key, value);
                }
            }
        }
    }

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = getCompile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line       源字符串
     * @param smallCamel 大小写
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();

        Pattern pattern = getCompile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        if (smallCamel) {
            return sb.toString().toLowerCase();
        }
        return sb.toString();
    }

    public static Pattern getCompile(String regex) {
        return Pattern.compile(regex);
    }

    /**
     * 转义Mysql关键字
     *
     * @param keyword 源字符串
     * @return 转后的字符串
     */
    public static String escapeMysqlSpecialChar(String keyword) {
        if (SpringUtils.hasLength(keyword)) {
            String[] fbsArr = {"\\", "$", "|", "%", "_", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }

        return keyword;
    }

    /**
     * 用于格式化时间
     *
     * @param occurTime
     * @return
     */
    public static String chageTime2Data(long occurTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(occurTime);
        return simpleDateFormat.format(date);
    }


}
