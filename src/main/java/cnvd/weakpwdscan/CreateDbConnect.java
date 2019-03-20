package cnvd.weakpwdscan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AESKey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建各种数据库连接
 *
 * @author dqye
 */
public class CreateDbConnect {

    private static final Logger log = LoggerFactory.getLogger(CreateDbConnect.class);

    /**
     * 创建数据库连接
     *
     * @param dbConfig 数据库配置信息
     * @param close    连接是否关闭，true关闭，此时不对map进行修改
     * @return 创建连接是否成功
     */
    public static Boolean createConnect(DatabaseConfig dbConfig, Boolean close) {
        Object connection = createConns(dbConfig);
        if (close) {
            closeConnect(connection);
        } else {
            InitConnect.DB_CONNECT_MAP.put(dbConfig.getDbAlias(), connection);
            if ((connection != null) && (DatabaseConfig.HBASE_DB_TYPE.equals(dbConfig.getDbType()))) {
                // 对于HBase数据库，connection为master或region，将该值做为版本信息存入数据库
                // 既然是put操作，而且连接不为空证明之前连接不上，现在能连接上了，因此更新数据库中的db_version字段
//                dbConfig.setDbVersion(String.valueOf(connection));
//                DatabaseConfigService databaseConfigService = SpringUtils.getBean(DatabaseConfigServiceImpl.class);
//                databaseConfigService.updateDbVersion(dbConfig);
            }
        }
        if (connection != null) {
            return true;
        } else {
//            SystemLog syslog = new SystemLog();
//            syslog.setLogType(Byte.valueOf("1"));
//            syslog.setSystemLogResult(Byte.valueOf("-1"));
//            syslog.setSystemLogTime(System.currentTimeMillis());
//            syslog.setEventLevel(Byte.valueOf("1"));
//            syslog.setLogDescribe("创建名称为:'" + dbConfig.getDbAlias() + "'数据库的连接失败");
//            LogService logServices = SpringUtils.getBean(LogServiceImpl.class);
//            if (logServices != null) {
//                logServices.insertSelective(syslog);
//            }
            return false;
        }
    }

    /**
     * 关闭数据库连接
     *
     * @param object 数据库连接
     */
    public static void closeConnect(Object object) {
        try {
            if (object == null) {
                return;
            }
//            if (object instanceof Jedis) {
//                Jedis jedis = (Jedis) object;
//                jedis.close();
//            }
//            if (object instanceof MongoClient) {
//                MongoClient mongoClient = (MongoClient) object;
//                mongoClient.close();
//                //关闭连接重新赋值为空
//                mongoClient = null;
//            }
            if (object instanceof Connection) {
                Connection con = (Connection) object;
                con.close();
            }
        } catch (SQLException e) {
            log.error("关闭测试连接时异常:", e);
        }
    }

    /**
     * 测试已有连接是否可用
     *
     * @param config 数据库配置信息
     * @return 是否可用
     */
    public static Boolean testConnect(DatabaseConfig config) throws SQLException {
        Object object = InitConnect.DB_CONNECT_MAP.get(config.getDbAlias());
        String dbType = config.getDbType();
        String dbAlias = config.getDbAlias();

        if (DatabaseConfig.HBASE_DB_TYPE.equals(dbType)) {
            Object type = createConns(config);
            if (type == null) {
                log.info("{}数据库的连接不可用",dbAlias);
                return false;
            } else {
                return true;
            }
        }

//        if (DatabaseConfig.REDIS_DB_TYPE.equalsIgnoreCase(dbType)) {
//
//            Jedis jedis = (Jedis) object;
//            if (jedis == null) {
//
//                return false;
//            }
//            String pings = jedis.ping();
//            log.info("{}数据库的连接的状态为:{}", dbAlias, pings);
//            //TODO 判断条件未知
//            if (DatabaseConfig.REDIS_PING_PONG.equalsIgnoreCase(pings)) {
//                return true;
//            } else {
//                //关闭连接
//                jedis.close();
//                return false;
//            }
//        }
//        if (DatabaseConfig.MONGODB_DB_TYPE.equalsIgnoreCase(dbType)) {
//            MongoClient mongoClient = (MongoClient) object;
//            String point = mongoClient.getConnectPoint();
//            log.info("{}数据库的连接的状态为:{}", dbAlias, point);
//            if (SpringUtils.hasLength(point)) {
//                return true;
//            } else {
//                //连接不正常,关闭连接
//                mongoClient.close();
//                mongoClient = null;
//                return false;
//            }
//        }

//        if (object instanceof Connection) {
//            Connection connection = (Connection) object;
//            try {
//                Integer timeout = Integer.valueOf(PropertyUtils.getProperty("testConnectionOutTime"));
//                Boolean flag = connection.isValid(timeout);
//                log.info("{}数据库的连接的状态为:{}", dbAlias, flag);
//                return flag;
//            } catch (SQLException e) {
//                //连接不正常关闭连接
//
//                connection.close();
//                log.debug("{}数据库的连接异常:{}", dbAlias, e);
//            }
//        }
        return false;
    }

    private static Object createConns(DatabaseConfig config) {
        Object connection = null;
        CreateDbConnect factory = new CreateDbConnect();
        switch (config.getDbType().toLowerCase()) {
            case DatabaseConfig.REDIS_DB_TYPE:
//                connection = factory.redisConnect(config);
                break;
            case DatabaseConfig.MONGODB_DB_TYPE:
//                connection = factory.mongoDBConnect(config);
                break;
            case DatabaseConfig.POSTGRESQL_DB_TYPE:
                connection = factory.postgreSQLConnect(config);
                break;
            case DatabaseConfig.DB2_DB_TYPE:
                connection = factory.db2Connect(config);
                break;
            case DatabaseConfig.MYSQL_DB_TYPE:
                connection = factory.mysqlConnect(config);
                break;
            case DatabaseConfig.ORACLE_DB_TYPE:
                connection = factory.oracleConnect(config);
                break;
            case DatabaseConfig.SQLSERVER_DB_TYPE:
                connection = factory.sqlServerConnect(config);
                break;
            case DatabaseConfig.HBASE_DB_TYPE:
//                connection = factory.hbaseConnect(config);
                break;
            default:
                break;
        }
        return connection;
    }

    //用以存储数据库测试失败的值
    public static ThreadLocal<Integer> testConnectThreadLocal = new ThreadLocal<Integer>();

    /**
     * 创建mysql数据库连接
     *
     * @param dbConfig
     * @return
     */
    public Connection mysqlConnect(DatabaseConfig dbConfig) {
        // URL指向要访问的数据库名mydata com.mysql.cj.jdbc.Driver
        String url = "jdbc:mysql://" + dbConfig.getDbIp() + ":" + dbConfig.getDbPort() + "/";
        try {
            // 加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            // 1.getConnection()方法，连接MySQL数据库！！
            String password = HashEncodeUtil.aesDecrypt(dbConfig.getDbPassword(), AESKey.DBCONFIG.getKey());
            return DriverManager.getConnection(url, dbConfig.getDbUser(), password);
        } catch (SQLException e) {
            //把数据库测试失败值添加到testConnectThreadLocal变量
            findDbTestFailureValue(e.getErrorCode());
            log.error("创建mysql数据库{}连接时异常,{}", dbConfig.getDbAlias(), e);
        } catch (ClassNotFoundException fe) {
            log.error("创建mysql数据库{}连接时异常", dbConfig.getDbAlias(), fe);
        }
        return null;
    }

//    /**
//     * 创建redis数据库连接
//     *
//     * @param dbConfig
//     * @return
//     */
//    public Jedis redisConnect(DatabaseConfig dbConfig) {
//        //0、创建池子的配置对象
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        //最大闲置个数
//        poolConfig.setMaxIdle(300);
//        poolConfig.setMaxWaitMillis(100);
//        //最小闲置个数
//        poolConfig.setMinIdle(10);
//        poolConfig.setMaxTotal(500);
//
//        //1、创建一个redis的连接池
//        Jedis jedis = null;
//        String password;
//        try (JedisPool pool = new JedisPool(poolConfig, dbConfig.getDbIp(), dbConfig.getDbPort())) {
//            //2、从池子中获取redis的连接资源
//            jedis = pool.getResource();
//            password = dbConfig.getDbPassword();
//            if (SpringUtils.hasLength(password)) {
//                password = HashEncodeUtil.aesDecrypt(password, AESKey.DBCONFIG.getKey());
//                jedis.auth(password);
//            }
//            return jedis;
//        } catch (Exception e) {
//            //认证异常的时候将jedis关闭
//            if (jedis != null) {
//                jedis.close();
//            }
//            log.error("创建Redis数据库{}连接时异常", dbConfig.getDbAlias(), e);
//        }
//        return null;
//    }

    /**
     * 创建oracle数据库连接
     *
     * @param dbConfig
     * @return
     */
    public Connection oracleConnect(DatabaseConfig dbConfig) {
        Connection con;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String userName = dbConfig.getDbUser();
            if (DatabaseConfig.ORACLE_DBA.equalsIgnoreCase(dbConfig.getExtendA())) {
                userName = userName + " as sysdba";
            }
            if (DatabaseConfig.ORACLE_OPER.equalsIgnoreCase(dbConfig.getExtendA())) {
                userName = userName + " as sysoper";
            }
            String password = HashEncodeUtil.aesDecrypt(dbConfig.getDbPassword(), AESKey.DBCONFIG.getKey());
            con = DriverManager.getConnection("jdbc:oracle:thin:@" + dbConfig.getDbIp() + ":" + dbConfig.getDbPort() + "/"
                    + dbConfig.getDbExample(), userName, password);
            return con;
        } catch (SQLException e) {
            //把数据库测试失败值添加到testConnectThreadLocal变量
            findDbTestFailureValue(e.getErrorCode());
            log.error("创建Oracle数据库{}连接时异常", dbConfig.getDbAlias(), e);
        } catch (ClassNotFoundException fe) {
            log.error("创建Oracle数据库{}连接时异常", dbConfig.getDbAlias(), fe);
        }
        return null;
    }

    /**
     * 创建SqlServer数据库连接
     *
     * @param dbConfig
     * @return
     */
    public Connection sqlServerConnect(DatabaseConfig dbConfig) {
        try {
            Connection conn = null;
            //1.加载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //2.获得数据库的连接
            String url = "jdbc:sqlserver://" + dbConfig.getDbIp() + ":" + dbConfig.getDbPort() + ";database=" + dbConfig.getDbExample();
            DriverManager.setLoginTimeout(1);
            String password = HashEncodeUtil.aesDecrypt(dbConfig.getDbPassword(), AESKey.DBCONFIG.getKey());
            conn = (Connection) DriverManager.getConnection(url, dbConfig.getDbUser(), password);
            return conn;
        } catch (SQLException e) {
            //把数据库测试失败值添加到testConnectThreadLocal变量
            findDbTestFailureValue(e.getErrorCode());
            log.error("创建SQLServer数据库{}连接时异常", dbConfig.getDbAlias(), e);
        } catch (ClassNotFoundException fe) {
            log.error("创建SQLServer数据库{}连接时异常", dbConfig.getDbAlias(), fe);
        }
        return null;
    }

    /***
     * 创建DB2数据库连接
     *
     * @param dbConfig
     * @return
     */
    public Connection db2Connect(DatabaseConfig dbConfig) {
        Connection conn = null;
        try {
            Class.forName("com.ibm.db2.jdbc.net.DB2Driver");
            String password = HashEncodeUtil.aesDecrypt(dbConfig.getDbPassword(), AESKey.DBCONFIG.getKey());
            conn = DriverManager.getConnection("jdbc:db2://" + dbConfig.getDbIp() + ":" + dbConfig.getDbPort() +
                    "/" + dbConfig.getDbExample(), dbConfig.getDbUser(), password);
            return conn;
        } catch (Exception e) {
            log.error("创建DB2数据库{}连接时异常", dbConfig.getDbAlias(), e);
        }
        return null;
    }

    /**
     * 创建Postgre数据库连接
     *
     * @param dbConfig
     * @return
     */
    public Connection postgreSQLConnect(DatabaseConfig dbConfig) {
        try {
            String url = "jdbc:postgresql://" + dbConfig.getDbIp() + ":" + dbConfig.getDbPort() + "/" + dbConfig.getDbExample();
            Class.forName("org.postgresql.Driver");
            String password = HashEncodeUtil.aesDecrypt(dbConfig.getDbPassword(), AESKey.DBCONFIG.getKey());
            return DriverManager.getConnection(url, dbConfig.getDbUser(), password);
        } catch (Exception e) {
            log.error("创建PostgreSQL数据库{}连接时异常", dbConfig.getDbAlias(), e);
        }
        return null;
    }

//    /**
//     * 创建mongoDB数据库连接
//     *
//     * @param dbConfig
//     * @return
//     */
//    public Mongo mongoDBConnect(DatabaseConfig dbConfig) {
//
//        if (dbConfig == null) {
//            return null;
//        }
//
//        //设置超时连接
//        MongoClientOptions.Builder build = null;
//        try {
//            build = new MongoClientOptions.Builder();
//            build.serverSelectionTimeout(2000);
//            //设置连接池与数据库的最大连接数 默认为100
//            build.connectionsPerHost(20);
//            //如果当前连接都在使用中，则每个连接上可以有1个线程在等待  默认值为5
//            build.threadsAllowedToBlockForConnectionMultiplier(1);
//            build.connectTimeout(2000);
//            //空闲时间之后,会转化可用的连接
//            build.maxWaitTime(1000);
//            build.socketTimeout(1000);
//
//            //设置连接池最大空闲时间, 入库模块每5秒调用一次,超过这个时间之后,线程池将会回收连接
//            build.maxConnectionIdleTime(10000);
//        } catch (Exception e) {
//            log.error("mongodb连接参数配置出错", dbConfig.getDbAlias(), e);
//        }
//
//        MongoClientOptions options = null;
//        if (build != null) {
//            options = build.build();
//        }
//
//        //无密码认证
//        if(!SpringUtils.hasLength(dbConfig.getDbUser())){
//            ServerAddress address = new ServerAddress(dbConfig.getDbIp(),dbConfig.getDbPort());
//
//            MongoClient mongoClient = new MongoClient(address,options);
//            try {
//                // 连接到数据库
//                MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
//                Document document = mongoDatabase.runCommand(new BasicDBObject("serverStatus", Boolean.TRUE));
//                return mongoClient;
//            } catch (Exception e) {
//                String message = e.getMessage();
//                //测试ip或端口错误或数据库关闭
//                Boolean isIpOrPortOrDBColse = message.contains("Connection refused");
//                if (isIpOrPortOrDBColse) {
//                    testConnectThreadLocal.set(Constants.THE_TEST_CONNECT_IS_IP_PORT_DBCLOSE_FAILED);
//                } else {
//                    testConnectThreadLocal.set(null);
//                }
//                log.error("创建MongoDB数据库{}连接时异常", dbConfig.getDbAlias(), e);
//                mongoClient.close();
//                mongoClient = null;
//                return null;
//            }
//        }else {
//            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
//            //ServerAddress()两个参数分别为 服务器地址 和 端口
//            ServerAddress serverAddress = new ServerAddress(dbConfig.getDbIp(), dbConfig.getDbPort());
//            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
//            addrs.add(serverAddress);
//            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
//            String password = HashEncodeUtil.aesDecrypt(dbConfig.getDbPassword(), AESKey.DBCONFIG.getKey());
//            MongoCredential sha1Credential = MongoCredential.createScramSha1Credential(dbConfig.getDbUser(), "admin",
//                    password.toCharArray());
//            List<MongoCredential> sha1Credentials = new ArrayList<MongoCredential>();
//            sha1Credentials.add(sha1Credential);
//
//            MongoCredential credential = MongoCredential.createMongoCRCredential(dbConfig.getDbUser(),"admin",password.toCharArray());
//            List<MongoCredential> credentials = new ArrayList<>();
//            credentials.add(credential);
//
//
//            //通过连接认证获取MongoDB连接  不同版本连接方式不一样
//            MongoClient shaClient = new MongoClient(addrs, sha1Credentials, options);
//            MongoClient crClient = new MongoClient(addrs,credentials,options);
//
//            try {
//                DB shadb = shaClient.getDB("admin");
//                //认证失败执行命令时会抛出异常
//                shadb.command("serverStatus");
//                //如果sha认证成功，则将创建的cr认证关闭
//                crClient.close();
//                crClient=null;
//                return shaClient;
//            } catch (Exception e) {
//                log.warn("{}数据库以SHA方式认证失败",dbConfig.getDbAlias());
//                //sha认证失败关闭连接
//                shaClient.close();
//                shaClient = null;
//                try {
//                    DB crdb = crClient.getDB("admin");
//                    crdb.command("serverStatus");
//                    return  crClient;
//                } catch (Exception e1) {
//                    String message = e1.getMessage();
//                    //测试ip或端口错误或数据库关闭
//                    Boolean isIpOrPortOrDBColse = message.contains("Connection refused");
//                    //测试用户或密码错误
//                    Boolean isUserOrPassword = message.contains("auth failed");
//                    if (isIpOrPortOrDBColse) {
//                        testConnectThreadLocal.set(Constants.THE_TEST_CONNECT_IS_IP_PORT_DBCLOSE_FAILED);
//                    } else if (isUserOrPassword) {
//                        testConnectThreadLocal.set(Constants.THE_TEST_CONNECT_IS_USER_PASSWORD_FAILED);
//                    } else {
//                        testConnectThreadLocal.set(null);
//                    }
//                    crClient.close();
//                    //参考博客的说法，连接池关闭但是连接还在，赋值为null关闭连接
//                    crClient = null;
//                    log.error("创建MongoDB数据库{}连接时异常", dbConfig.getDbAlias(), e);
//                }
//            }
//        }
//
//        return null;
//    }


//    /**
//     * 创建Hbase数据库连接
//     *
//     * @param dbConfig 数据库配置信息
//     * @return 成功返回master或region，失败返回null
//     */
//    public String hbaseConnect(DatabaseConfig dbConfig) {
//        String alias = dbConfig.getDbAlias();
//        String url = "http://" + dbConfig.getDbIp() + ":" + dbConfig.getDbPort() + "/jmx";
//        HttpGet get = new HttpGet(url);
//        // 使用jdk1.7新特性关闭HTTP请求  解决连接过多问题
//        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
//            CloseableHttpResponse response = httpClient.execute(get);
//            Integer statusCode = response.getStatusLine().getStatusCode();
//            if (HttpServletResponse.SC_OK != statusCode) {
//                log.error("创建HBase数据库{}连接时异常", alias);
//                return null;
//            }
//            String infos = JSONObject.fromObject(EntityUtils.toString(response.getEntity())).getString("beans");
//            if (!SpringUtils.hasLength(infos)) {
//                log.error("创建HBase数据库{}连接时读取服务器类型异常", alias);
//                return null;
//            }
//            if (infos.indexOf(DatabaseConfig.HBASE_MASTER_NAME) != -1) {
//                return "master";
//            }
//            if (infos.indexOf(DatabaseConfig.HMASTER_START) != -1) {
//                return "master";
//            }
//            if (infos.indexOf(DatabaseConfig.HBASE_REGION_NAME) != -1) {
//                return "region";
//            }
//            if (infos.indexOf(DatabaseConfig.HREGIONSERVER_START) != -1) {
//                return "region";
//            }
//            log.error("创建HBase数据库{}连接时读取服务器类型异常", alias);
//            return null;
//        } catch (IOException e) {
//            String message = e.getMessage();
//            //测试ip或端口错误或数据库关闭
//            Boolean isIp = message.contains("Connection timed out");
//            //测试用户或密码错误
//            Boolean isPosrtOrDbColse = message.contains("Connection refused");
//            if (isIp) {
//                testConnectThreadLocal.set(Constants.THE_TEST_CONNECT_IS_IP_FAILED);
//            } else if (isPosrtOrDbColse) {
//                testConnectThreadLocal.set(Constants.THE_TEST_CONNECT_IS_PORT_DBCLOSE_FAILED);
//            } else {
//                testConnectThreadLocal.set(null);
//            }
//            log.error("创建HBase数据库{}连接时异常", alias);
//            return null;
//        }
//    }

    /*
      * @Author oushiguang
      * @Description //TODO 把数据库测试失败值添加到testConnectThreadLocal变量
      * @Date 15:54 2018/8/2
      * @Param [errorCode 异常错误码]
      * @return void
      **/
    public void findDbTestFailureValue(Integer errorCode) {

        Map<Integer, Integer> errorValueMap = new HashMap<>(16);
        errorValueMap.put(17002, Constants.THE_TEST_CONNECT_IS_IP_PORT_FAILED);
        errorValueMap.put(1031, Constants.THE_TEST_CONNECT_IS_USER_ROLE_FAILED);
        errorValueMap.put(1017, Constants.THE_TEST_CONNECT_IS_USER_PASSWORD_FAILED);
        errorValueMap.put(12514, Constants.THE_TEST_CONNECT_IS_SID_DBCLOSE_FAILED);
        errorValueMap.put(28009, Constants.THE_TEST_CONNECT_IS_ROLE_FAILED);
        errorValueMap.put(0, Constants.THE_TEST_CONNECT_IS_IP_PORT_DBCLOSE_FAILED);
        errorValueMap.put(1045, Constants.THE_TEST_CONNECT_IS_USER_PASSWORD_FAILED);
        errorValueMap.put(18456, Constants.THE_TEST_CONNECT_IS_USER_PASSWORD_FAILED);
        errorValueMap.put(4060, Constants.THE_TEST_CONNECT_IS_DBTYPE_FAILED);

        //获取errorCode对应的数据库测试失败值
        Integer dbTestFailureValue = errorValueMap.get(errorCode);
        //把数据库测试失败值添加到testConnectThreadLocal变量
        testConnectThreadLocal.set(dbTestFailureValue);
        //获取不到数据库测试失败值则把testConnectThreadLocal变量置为空
        if (dbTestFailureValue == null) {
            testConnectThreadLocal.set(null);
        }

    }
}
