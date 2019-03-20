package cnvd.weakpwdscan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author carl
 * @date 2017/3/20
 */
public class Constants {

    public static final Map<String, List<String>> POPEDOM_MAP = new HashMap<>(16);
    /**
     * token有效期（分钟）
     */
    public static final int TOKEN_EXPIRES_MINUTE = 35;
    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";
    public static final String THE_USER_ISNOT_LOGIN = "00000003";

    /**
     * 证书七天就要过期时间
     */
    public static final Long THE_LICENSE_FILE_IS_OVERING_IN_THREE_DAY = 604800000L;

    /**
     * 验证码错误
     */
    public static final Integer THE_VALIDATECODE_IS_ERROR = 102;
    /**
     * 用户或者密码不正确
     */
    public static final Integer THE_USER_OR_PASSORD_IS_NOT_RIGHT = 101;

    /**
     * 用户名已存在
     */
    public static final Integer THE_USER_IS_EXISTED = 103;

    /**
     * 旧密码错误
     */
    public static final Integer THE_OLDPASSWORD_IS_ERROR = 104;
    /**
     * 新旧密码相同
     */
    public static final Integer THE_NEW_PASSWORD_IS_EQUAL = 105;
    /**
     * 用户被锁定,不允许登入
     */
    public static final Integer THE_USER_IS_LOCKED = 106;
    /**
     * 验证码失效
     */
    public static final Integer THE_VALIDATECODE_IS_LOSE = 107;
    /**
     * 验证码失效
     */
    public static final Integer THE_DELETE_IS_YONGSELF = 108;
    /**
     * 用户登录失败一次
     */
    public static final Integer THE_USER_LOGIN_FAIL_ONE = 109;
    /**
     * 用户登录失败二次
     */
    public static final Integer THE_USER_LOGIN_FAIL_TWO = 110;
    /**
     * 用户登录失败三次
     */
    public static final Integer THE_USER_LOGIN_FAIL_THREE = 111;
    /**
     * 用户登录失败四次
     */
    public static final Integer THE_USER_LOGIN_FAIL_FOUR = 112;
    /**
     * 用户已锁，请稍后再登录
     */
    public static final Integer THE_USER_LOGIN_FAIL_FIVE = 113;
    /**
     * 主机已存在
     */
    public static final Integer THE_IP_IS_EXISTED = 201;
    /**
     * 数据库名已存在
     */
    public static final Integer THE_ALIAS_IS_EXISTED = 202;
    /**
     * 测试连接测试失败
     */
    public static final Integer THE_TEST_CONNECT_IS_FAILED = 203;
    /**
     * 修改时，数据库配置信息被删除，数据库配置信息不存在
     */
    public static final Integer THE_DBCONFIG_IS_NOT_EXISTS = 204;
    /**
     * 添加数据库的时候,数据库数目超限
     */
    public static final Integer THE_DBCONFIG_NUMBER_OVERRUN = 205;

    /**
     * 删除数据库时全部失败，未删除
     */
    public static final Integer THE_DBCONFIG_DELETE_FAIL = 206;

    /**
     * 删除数据库时部分删除失败
     */
    public static final Integer THE_DBCONFIG_DELETE_EXIST_FAIL = 207;

    /**
     * 上传证书证书异常
     */
    public static final Integer THE_LICENSE_IS_ERROR = 301;
    /**
     * 上传证书证书过期
     */
    public static final Integer THE_LICENSE_IS_OVERDUE = 302;

    /**
     * 证书不存在,即没有授权
     */
    public static final Integer THE_LICENSE_FILE_IS_NOT_EXIST = 303;
    /**
     * 证书授权软件有误,即没有授权
     */
    public static final Integer THE_LICENSE_FILE_IS_NOT_THIS_SOFTWARE = 304;
    /**
     * 权限组已存在
     */
    public static final Integer THE_GROUP_IS_EXISTED = 401;
    /**
     * 删除权限组时还存在用户
     */
    public static final Integer THE_USER_IS_EXISTED_IN_GROUP = 402;
    /**
     * 邮件测试失败
     */
    public static final Integer THE_EMAIL_TEST_IS_FAILED = 501;

    /**
     * 正在进行脱敏任务
     */
    public static final Integer THE_RESPOND_IS_DESENSITIZATION_ING = 601;

    /**
     * 脱敏规则已存在
     */
    public static final Integer THE_DESEN_RULE_NAME_IS_EXISTED = 602;

    /**
     * 删除脱敏规则时全部失败，未删除
     */
    public static final Integer THE_DESEN_RULE_DELETE_FAIL = 603;

    /**
     * 删除脱敏规则时部分删除失败
     */
    public static final Integer THE_DESEN_RULE_DELETE_EXIST_FAIL = 604;

    /**
     * 正在进行自动发现任务
     */
    public static final Integer THE_RESPOND_IS_FIND_ING = 605;

    /***
     * 脱敏任务已经存在
     */
    public static final Integer THE_DESENSITIZTION_TASK_ALREADY_EXIST = 701;
    /***
     * 脱敏任务中，源数据库中被脱敏表，已经在目标数据库中存在
     */
    public static final Integer THE_DESENSITIZTION_TASK_TABLE_EXIST = 702;

    /***
     * 创建表失败
     */
    public static final Integer THE_CREATE_TABLE_FAILED = 703;

    /***
     * 创建文件夹失败
     */
    public static final Integer THE_CREATE_FILE_FAILED = 704;

    /***
     * 上传文件失败
     */
    public static final Integer THE_SAVE_FILE_FAILED = 705;
    /**
     * 目标数据库已存在
     */
    public static final Integer THE_TARGET_DATABASE_EXIST = 706;
    /***
     * 目标类型错误
     */
    public static final Integer THE_TARGET_ERROR = 706;

    /**
     * 数据库未更新
     */
    public static final Integer THE_DB_NOT_UPDATE = 707;

    /**
     * 数据库连接错误
     */
    public static final Integer DB_ERROR = 708;

    /***
     *  规则模板已经存在
     */
    public static final Integer THE_RULE_GROUP_ALREADY_EXIST= 801;

    /***
     *  禁用用户已经存在
     */
    public static final Integer THE_DISABLE_USER_EXIST = 907;

    /***
     *  敏感类型已存在
     */
    public static final Integer THE_SENS_TYPE_EXIST = 901;

    /***
     *  敏感类型被占用
     */
    public static final Integer THE_SENS_TYPE_OCCUPIED = 902;

    /***
     *  数据字典已存在
     */
    public static final Integer THE_DATA_DICTIONARY_EXIST = 903;

    /***
     *  数据字典表已存在
     */
    public static final Integer THE_DATA_DICTIONARY_TABLE_EXIST = 904;

    /***
     *  数据字典表被占用
     */
    public static final Integer THE_DATA_DICTIONARY_OCCUPIED = 905;

    /***
     *  校验失败
     */
    public static final Integer THE_SENS_TYPE_CHECK_FAIL = 906;

    /**
     * 返回成功
     */
    public static final Integer THE_RESPOND_IS_SUCCEED = 0;

    /**
     * 返回失败，未知异常
     */
    public static final Integer THE_RESPOND_IS_FAILED = -1;

    /**
     * 请求参数不正确
     */
    public static final Integer THE_REQUEST_PARAM_FAILED = 501;

    /**
     * 任务已存在
     */
    public static final Integer THE_JOB_IS_EXIST = 502;

    /**
     * 任务名重复
     */
    public static final Integer THE_JOB_NAME_SAME = 503;

    /**
     * 测试连接oracleIP或端口错误
     */
    public static final Integer THE_TEST_CONNECT_IS_IP_PORT_FAILED = 2031;

    /**
     * 测试连接用户名或密码错误
     */
    public static final Integer THE_TEST_CONNECT_IS_USER_PASSWORD_FAILED = 2032;

    /**
     * 测试连接SQLServer、MySQL、MongoDB IP或端口号错误或数据库未启动
     */
    public static final Integer THE_TEST_CONNECT_IS_IP_PORT_DBCLOSE_FAILED = 2034;

    /**
     * 测试连接Oracle SID/服务名错误或数据库未启动
     */
    public static final Integer THE_TEST_CONNECT_IS_SID_DBCLOSE_FAILED = 2035;

    /**
     * 测试连接SQLServer数据库名称错误
     */
    public static final Integer THE_TEST_CONNECT_IS_DBTYPE_FAILED = 2036;

    /**
     * 测试连接Oracle角色错误（注：Oracle超级用户登录时，选择Default会报错）
     */
    public static final Integer THE_TEST_CONNECT_IS_ROLE_FAILED = 2037;

    /**
     * 测试连接Oracle用户名或角色错误（注：Oracle普通用户登录时，选择SYSDBA/SYSOPER会报错）
     */
    public static final Integer THE_TEST_CONNECT_IS_USER_ROLE_FAILED = 2038;

    /**
     * 测试连接Hbase端口错误或数据库未启动
     */
    public static final Integer THE_TEST_CONNECT_IS_PORT_DBCLOSE_FAILED = 2033;

    /**
     * 测试连接Hbase Ip错误
     */
    public static final Integer THE_TEST_CONNECT_IS_IP_FAILED = 2039;

    /**
     * 测试连接MYSQL数据库不存在
     */
    public static final Integer THE_DB_NOT_EXIST = 2040;

    /**
     * 结果判断字符串
     */
    public final static String FAIL = "false";
    /**
     * 结果判断字符串
     */
    public final static String SUCCESS = "success";
    /**
     * 用户存在判断字符串
     */
    public final static String EXIST = "exist";

    public final static String SOURCE = "source";

    public final static  String TARGET = "target";

    public final static Integer THE_LICENSE_INFO_NUM = 4;

    public final static String BEAN_CFCMMS_ROOT = "com.ankki.cfcmms.model";
    public final static String BEAN_DSA_ROOT = "com.ankki.dsa.model";

    /**
     * 模糊查询特殊符号转义
     */
    public final static String PERCENT_SIGNS = "%";

    public final static String UNDERSCORES = "_";

    private Constants() {
        throw new IllegalAccessError("Constants class");
    }

    /***
     * 任务状态——进行中
     */
    public static final int RUNING = 2;
    /***
     * 任务状态——等待中
     */
    public static final int WAITING = 3;
    /***
     * 任务状态——完成
     */
    public static final int COMPLETE = 5;

    /***
     * 任务状态——停止
     */
    public static final int STOP = 4;

    /**
     * 任务状态--失败
     */
    public static final int JOB_ERROR = 6;

    /***
     * 任务状态——未启动
     */
    public static final int NOT_STARTING = 1;

    /***
     * 任务状态——响应中
     */
    public static final int RUNING_AND_WAITING = 0;

    /***
     * 立即执行任务
     */
    public static final int NOW_EXECUTION_JOB = 0;

    /***
     * 定时执行任务
     */
    public static final int TIMING_EXECUTION_JOB = 1;

    /***
     * 周期执行任务
     */
    public static final int CYCLE_EXECUTION_JOB = 2;

    /**
     * 全表发现
     */
    public static final int SENS_MODE_CONTENT = 2;
    /**
     * 样本发现
     */
    public static final int SENS_MODE_FIELD = 1;

    /***
     * 任务类型：数据库脱敏
     */
    public static final String TASK_TYPE_DZ = "dz";

    /***
     * 任务类型：自动发现敏感数据
     */
    public static final String TASK_TYPE_FIND = "find";

    /**
     * 任务类型：文件脱敏
     */
    public static final String TASK_TYPE_FILE = "file_dz";

    /***
     * 脱敏任务剩余线程数表中数据ID
     */
    public static final int DZ_THREAD_SURPLUS = 2;

    /***
     * 自动发现任务剩余线程数表中数据ID
     */
    public static final int FIND_THREAD_SURPLUS = 5;

    public static final int THREAD_MAX_VALUE = 1;

    /***
     * 单次最大脱敏量
     */
    public static final int DZ_THREAD_MAX_VALUE = 2;

    /**
     * oracle
     */
    public static final String ORACLE_DB_TYPE = "oracle";
    public static final String ORCALE_RULE_SYSDBA = "sysdba";
    public static final String ORCALE_RULE_SYSOPER = "sysoper";

    public static final String SQLSERVER_DB_TYPE = "mssqlnative";

    public static final String MYSQL_DB_TYPE = "mysql";

    public static final String DM_DB_TYPE = "generic";

    public static final String MONGODB_DB_TYPE = "mongodb";

    public static final String HIVE_DB_TYPE = "hive2";

    /**
     * db2
     */
    public static final  String DB2_DB_TYPE = "db2";
    public static final String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";

    public static final int ASCIICODE_A = 65;

    // -------------------------- 日志相关常量 -------------------
    /**
     * 日志类型：异常日志
     */
    public static final byte LOG_TYPE_ERROR = 1;

    /**
     * 日志类型：其他日志
     */
    public static final byte LOG_TYPE_OTHER = 2;

    /**
     * 日志类型：设备日志
     */
    public static final byte LOG_TYPE_EQUIPMENT = 3;

    /**
     * 日志等级：一般事件
     */
    public static final byte LOG_EVENT_LEVEL_NORMAL = 0;

    /**
     * 日志等级：一般告警
     */
    public static final byte LOG_EVENT_LEVEL_WARN = 1;

    /**
     * 日志等级：致命告警
     */
    public static final byte LOG_EVENT_LEVEL_ERROR = 2;

    /**
     * 日志结果：成功
     */
    public static final byte LOG_RESULT_SUCCESS = 0;

    /**
     * 日志结果：失败
     */
    public static final byte LOG_RESULT_ERROR = -1;

    /**
     * scv缓存值
     */
    public static final String CSV_BUFFER_SIZE = "50000";

    /**
     * excel类型
     */
    public static final int EXCEL_TYPE = 1;

    /**
     * txt类型
     */
    public static final int TXT_TYPE = 3;

    /**
     * csv类型
     */
    public static final int CSV_TYPE = 2;

    /**
     * 目标数据库类型
     */
    public static final int DB_TYPE = 1;

    /**
     * 目标文件类型
     */
    public static final int FILE_TYPE = 0;

    /**
     * 错误：驱动包找不到
     */
    public static final int DRIVER_NOT_FOUND = 2041;

}
