package cnvd.weakpwdscan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author czjuan
 * @date 2018/06/28
 */
public class GetMonitorStateUtil {


    private static Logger log = LoggerFactory.getLogger(GetMonitorStateUtil.class);

    public static final Byte MONITOR_IS_CLOSE = 0;

//    /**
//     * 根据数据库类型拼接get方法，获取对应数据库类型的全局监控状态
//     *
//     * @param databaseType     数据库类型
//     * @param monitorConfigDao MonitorConfigMapper 实例
//     * @return
//     * @throws NoSuchMethodException     在拼接get方法时可能抛出找不到该方法的异常
//     * @throws IllegalAccessException    执行invoke方法时抛出
//     * @throws InvocationTargetException 执行invoke方法时抛出
//     */
//    public Byte monitorConfigGetMethod(String databaseType, MonitorConfigMapper monitorConfigDao) throws NoSuchMethodException,
//            IllegalAccessException, InvocationTargetException {
//        MonitorConfig monitorConfig = monitorConfigDao.selectByPrimaryKey(1);
//        Class<? extends MonitorConfig> monitorConfigClass = MonitorConfig.class;
//        // 获得字符串名字
//        String fieldName = databaseType + "MonitorState";
//        // 字符串首字母大写
//        char[] cs = fieldName.toCharArray();
//        cs[0] -= 32;
//        // 调用get方法
//        Method method = monitorConfigClass.getMethod("get" + String.valueOf(cs));
//        // 得到值
//        return (Byte) method.invoke(monitorConfig);
//    }
//
//    /**
//     * 根据数据库配置ID获取该数据库的监控状态
//     *
//     * @param dbConfigId        数据库配置ID
//     * @param databaseConfigDao DatabaseConfigMapper实例
//     * @param monitorConfigDao  monitorConfigDao实例
//     * @return
//     */
//    public Byte getMonitorState(Integer dbConfigId, DatabaseConfigMapper databaseConfigDao, MonitorConfigMapper monitorConfigDao) {
//        MonitorConfig monitorConfig = monitorConfigDao.selectByPrimaryKey(1);
//        DatabaseConfig databaseConfig = databaseConfigDao.selectByPrimaryKey(dbConfigId);
//
//        Byte monitorState = MONITOR_IS_CLOSE;
//        //判断全局监控总开关是否关闭，若全局监控关闭则数据库实例监控关闭
//        if (monitorConfig.getAllMonitorConfig().equals(MONITOR_IS_CLOSE)) {
//            monitorState = monitorConfig.getAllMonitorConfig();
//        } else {
//            //若全局监控开启，则进一步判断数据库实例所在的数据库类型开关是否开启
//            Byte databaseState = 0;
//            try {
//                //利用反射拼接数据库类型get方法获取，全局数据库类型监控状态
//                //数据库配置信息可能已被删除
//                if (databaseConfig != null) {
//                    databaseState = monitorConfigGetMethod(databaseConfig.getDbType(), monitorConfigDao);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.debug("在拼接get方法获取对应数据库类型的监控状态时发生异常:", e);
//            }
//            if (databaseState.equals(MONITOR_IS_CLOSE)) {
//                //若数据库类型监控状态关闭则数据库实例监控关闭
//                monitorState = databaseState;
//            } else {
//                //若数据库类型监控状态开启则数据库实例监控开关由其单个来管决定
//                if (databaseConfig != null) {
//                    monitorState = databaseConfig.getMonitorStatus();
//                }
//            }
//        }
//        return monitorState;
//    }
//
//    /**
//     * 根据数据库配置ID查询这个数据库的最后一条数据，用于判断状态是否一致，一致则不入库
//     *
//     * @param databaseLifecycle    需要添加的生命周期事件
//     * @param dbConfigId           数据库配置ID
//     * @param databaseLifecycleDao DatabaseLifecycleMapper实例
//     */
//    public void insertDatabaseLifecycleUtil(DatabaseLifecycle databaseLifecycle, Integer dbConfigId, DatabaseLifecycleMapper databaseLifecycleDao) {
//        //根据数据库配置ID查询这个数据库的最后一条数据，用于判断状态是否一致，一致则不入库
//        DatabaseLifecycle databaseLifecycle1 = databaseLifecycleDao.selectByDbConfigId(dbConfigId);
//        if (databaseLifecycle1 != null) {
//            if (!databaseLifecycle1.getMonitorStatusCode().equals(databaseLifecycle.getMonitorStatusCode())) {
//                databaseLifecycleDao.insert(databaseLifecycle);
//            } else {
//                if (!databaseLifecycle1.getConnectStatusCode().equals(databaseLifecycle.getConnectStatusCode())) {
//                    databaseLifecycleDao.insert(databaseLifecycle);
//                } else {
//                    if (!databaseLifecycle1.getActionCode().equals(databaseLifecycle.getActionCode())) {
//                        databaseLifecycleDao.insert(databaseLifecycle);
//                    }
//                }
//            }
//        } else {
//            databaseLifecycleDao.insert(databaseLifecycle);
//        }
//    }
//
//    /**
//     * 对于判断连接成功和连接失败的动作，只需要判断监控状态和连接状态不同再入库即可
//     *
//     * @param databaseLifecycle    需要添加的生命周期事件
//     * @param dbConfigId           数据库配置ID
//     * @param databaseLifecycleDao DatabaseLifecycleMapper实例
//     */
//    public void insertConectLifecycleUtil(DatabaseLifecycle databaseLifecycle, Integer dbConfigId,
//                                          DatabaseLifecycleMapper databaseLifecycleDao) {
//        //根据数据库配置ID查询这个数据库的最后一条数据，用于判断状态是否一致，一致则不入库
//        DatabaseLifecycle databaseLifecycle1 = databaseLifecycleDao.selectByDbConfigId(dbConfigId);
//        if (databaseLifecycle1 != null) {
//            if (!databaseLifecycle1.getMonitorStatusCode().equals(databaseLifecycle.getMonitorStatusCode())) {
//                databaseLifecycleDao.insert(databaseLifecycle);
//            } else {
//                if (!databaseLifecycle1.getConnectStatusCode().equals(databaseLifecycle.getConnectStatusCode())) {
//                    databaseLifecycleDao.insert(databaseLifecycle);
//                }
//            }
//        }
//    }
//
//    /**
//     * 通过主键更新数据库版本信息
//     *
//     * @param record
//     */
//    public void updateDbVersionByAlias(DatabaseConfig record, DatabaseConfigMapper databaseConfigDao, Object conn) {
//        if (conn != null) {
//            String dbType = record.getDbType();
//
//            if (dbType.equals(DatabaseConfig.HBASE_DB_TYPE)) {
//                // 对于HBase数据库，conn为master或region，将该值做为版本信息存入数据库
//                record.setDbVersion(String.valueOf(conn));
//                databaseConfigDao.updateDbVersionByAlias(record);
//            }
//
//            if (dbType.equals(DatabaseConfig.SQLSERVER_DB_TYPE)) {
//                // SQLServer查询版本信息
//                Connection connection = (Connection) conn;
//                try (Statement statement = connection.createStatement()) {
//                    String versionTest = "SELECT @@Version";
//                    try (ResultSet versionSet = statement.executeQuery(versionTest)) {
//                        while (versionSet.next()) {
//                            String version = versionSet.getString(1);
//                            version = version.substring(21, 25);
//                            record.setDbVersion(version);
//                        }
//                    }
//                    databaseConfigDao.updateDbVersionByAlias(record);
//                } catch (SQLException e) {
//                    log.error("{}：查询版本信息时发生未知异常", record.getDbAlias(), e);
//                }
//            }
//
//            if (dbType.equals(DatabaseConfig.MYSQL_DB_TYPE)) {
//                // MySQL查询版本信息
//                Connection connection = (Connection) conn;
//                try (Statement statement = connection.createStatement()) {
//                    String versionTest = "SELECT version()";
//                    try (ResultSet versionSet = statement.executeQuery(versionTest)) {
//                        while (versionSet.next()) {
//                            String version = versionSet.getString(1);
//                            version = version.substring(0, 3);
//                            record.setDbVersion(version);
//                        }
//                    }
//                    databaseConfigDao.updateDbVersionByAlias(record);
//                } catch (SQLException e) {
//                    log.error("{}：查询版本信息时发生未知异常", record.getDbAlias(), e);
//                }
//            }
//
//            if (dbType.equals(DatabaseConfig.ORACLE_DB_TYPE)) {
//                // Oracle查询版本信息
//                Connection connection = (Connection) conn;
//                try (Statement statement = connection.createStatement()) {
//                    String versionTest = "SELECT version FROM PRODUCT_COMPONENT_VERSION WHERE product LIKE'Oracle%'";
//                    try (ResultSet versionSet = statement.executeQuery(versionTest)) {
//                        while (versionSet.next()) {
//                            String version = versionSet.getString(1);
//                            version = version.substring(0, 2);
//                            record.setDbVersion(version);
//                        }
//                    }
//                    databaseConfigDao.updateDbVersionByAlias(record);
//                } catch (SQLException e) {
//                    log.error("{}：查询版本信息时发生未知异常", record.getDbAlias(), e);
//                }
//            }
//            if (dbType.equals(DatabaseConfig.MONGODB_DB_TYPE)) {
//                MongoClient mongoClient = (MongoClient) conn;
//                if (mongoClient != null) {
//                    record.setDbVersion("-");
//                    databaseConfigDao.updateDbVersionByAlias(record);
//                }
//
//            }
//        }
//    }

}
