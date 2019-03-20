package cnvd.weakpwdscan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * @author carl
 * @date 2016/4/9
 */
public class SpringUtils implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SpringUtils.class);
    private static ApplicationContext applicationContext;

    private static DecimalFormat df = new DecimalFormat("#.00");

    /**
     * 获取国际化资源.
     *
     * @param key    资源文件中的key
     * @param params 资源文件中的参数
     * @return 返回相应的字符串信息
     */
    public static String getMessage(String key, Object[] params) {
        checkApplicationContext();
        String value = "";
        try {
            value = applicationContext.getMessage(key, params, Locale.CHINA);
        } catch (Exception e) {
            log.error("not find the value of:", e);
        }
        return value;
    }

    /**
     * 获取国际化资源.
     *
     * @param key    资源文件中的key
     * @param params 一个参数
     * @return 返回相应的字符串信息
     */
    public static String getMessage(String key, String params) {
        Object[] param = {params};
        return getMessage(key, param);
    }

    /**
     * 获取国际化资源.
     *
     * @param key
     * @return
     */
    public static String getMessage(String key) {
        return getMessage(key, "");
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return (T) applicationContext.getBean(clazz);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                    "applicaitonContext未注入,请在SpringMVC-servlet.xml中定义SpringContextHolder");
        }
    }

    private static synchronized void setApplication(ApplicationContext applicationCon) {
        applicationContext = applicationCon;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    @Override
    public void setApplicationContext(ApplicationContext ac) {
        if (ac != null) {
            setApplication(ac);
        }
    }

    public static boolean hasLength(String token) {
        if (token == null) {
            return false;
        }
        if (token.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 将数据库中列名转为对应set函数名
     *
     * @param columnName 列名
     * @return 对应Set函数名
     */
    public static String columnName2SetName(String columnName) {

        String[] names = columnName.split("_");
        StringBuilder stringBuilder = new StringBuilder("set");
        for (String name : names) {
            stringBuilder.append(String.valueOf(name.charAt(0)).toUpperCase());
            if (name.length() > 1) {
                stringBuilder.append(name.substring(1));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将String转为Double并保留两位小数
     *
     * @param string string
     * @return double
     */
    public static Double string2Double(String string) {
        return Double.valueOf(df.format(Double.valueOf(string)));
    }

    /**
     * 将Double保留两位小数
     *
     * @param d Double
     * @return double
     */
    public static Double doubleFormat(Double d) {
        return Double.valueOf(df.format(d));
    }
}
