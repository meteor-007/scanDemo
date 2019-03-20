package cnvd.weakpwdscan;


import lombok.Data;
import javax.persistence.Transient;
/**
 * Todo:模型类
 * @param:
 * @return:
 * @Author:$czwei
 * @Date: 2018/12/18 15:42
 */
@Data
@ModuleDefine(
        moduleId = 1004,
        moduleGroupId = 1007,
        moduleIconCls = "",
        moduleName = "databaseConfig.nav.title",
        moduleMapping = ""
)
public class DatabaseConfig {
    /**
     * 数据库监控为关闭状态
     */
    @Transient
    public static final Byte DB_MONITOR_STATUS_OFF = 0;

    public static final Integer ALL_CONNECT = 0;

    public static final Integer VALID_CONNECT = 1;

    public static final Integer INVALID_CONNECT = 2;

    public static final String ORACLE_DB_TYPE = "oracle";

    public static final String MYSQL_DB_TYPE = "mysql";

    public static final String SQLSERVER_DB_TYPE = "sqlserver";

    public static final String DB2_DB_TYPE = "db2";

    public static final String POSTGRESQL_DB_TYPE = "postgresql";

    public static final String MONGODB_DB_TYPE = "mongodb";

    public static final String REDIS_DB_TYPE = "redis";

    public static final String HBASE_DB_TYPE = "hbase";

    public static final String HBASE_MASTER = "master";

    public static final String HBASE_REGION = "region";

    public static final String REDIS_PING_PONG = "PONG";

    public static final String ORACLE_DBA = "sysdba";

    public static final String ORACLE_OPER = "sysoper";

    public static final String HBASE_MASTER_NAME = "\"name\":\"Hadoop:service=HBase,name=Master,sub=Server\"";

    public static final String HBASE_REGION_NAME = "\"name\":\"Hadoop:service=HBase,name=RegionServer,sub=Server\"";

    public static final String HREGIONSERVER_START = "HRegionServer start";

    public static final String HMASTER_START = "HMaster start";

    /**
     * 据库连接状态
     */
    @Transient
    public Integer connectStatus;

    private Integer dbConfigId;

    private String dbUser;

    private String dbPassword;

    private Integer dbPort;

    private String dbIp;

    private String dbExample;

    private String dbAlias;

    private String dbType;

    private String dbVersion;

    private Byte monitorStatus;

    private String extendA;

    private Integer sortId;

    private String description;

    private Long minTime;

    private Long tminTime;

    private Long hourTime;

    private Long shourTime;

    private Byte dataIsRunning;
}