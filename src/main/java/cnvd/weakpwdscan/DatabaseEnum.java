package cnvd.weakpwdscan;

/**
 * 数据库相关枚举类
 *
 * @author zml
 * @date 2018-5-25
 */
public enum DatabaseEnum {

    MYSQL("mysql"),

    SQL_SERVER("sqlServer"),

    ORACLE("oracle"),

    POSTGRES("postgres"),

    SYBASE("sybase"),

    MYSQL_5_0("5.0"),

    MYSQL_5_5("5.5"),

    MYSQL_5_6("5.6"),

    MYSQL_5_7("5.7"),

    MYSQL_8_0("8.0"),

    SQL_SERVER_2000("2000"),

    SQL_SERVER_2005("2005"),

    SQL_SERVER_2008("2008"),

    SQL_SERVER_2012("2012"),

    SQL_SERVER_2014("2014"),

    SQL_SERVER_2016("2016"),

    SQL_SERVER_2017("2017"),

    SYBASE_12_5("12.5"),

    SYBASE_15_5("15.5"),

    SYBASE_15_7("15.7"),

    DM_7("7"),

    DM_6("6");
    private String key;

    private DatabaseEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
