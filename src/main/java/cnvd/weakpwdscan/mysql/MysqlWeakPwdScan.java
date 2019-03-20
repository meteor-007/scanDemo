package cnvd.weakpwdscan.mysql;

import cnvd.weakpwdscan.DatabaseConfig;
import cnvd.weakpwdscan.DatabaseEnum;
import cnvd.weakpwdscan.SysUtils;
import cnvd.weakpwdscan.WeakPwdScanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Todo:MySQL弱口令扫描类
 *
 * @Author:$czwei
 * @Date: 2018/12/18 15:43
 */
public class MysqlWeakPwdScan {

    private static final Logger log = LoggerFactory.getLogger(MysqlWeakPwdScan.class);

    /**
     * Todo:MySQL弱口令扫描入口
     *
     * @param databaseConfig
     */
    public void mysql(DatabaseConfig databaseConfig) {

        String dbAlias = databaseConfig.getDbAlias();
        Object object = SysUtils.queryDbConnectInfo(databaseConfig, false);
        if (object == null) {
            log.error("获取{}数据库的连接为null", dbAlias);
            return;
        }
        Connection conn = (Connection) object;
//        Class.forName("org.gjt.mm.mysql.Driver").newInstance();
//        String url = "jdbc:mysql://localhost/myDB?user=soft&password=soft1234&useUnicode=true&characterEncoding=8859_1";
        //myDB为数据库名
//        Connection conn = DriverManager.getConnection(url);
        String sqlText = "SELECT user,password FROM mysql.user";//5.0,5.5,5.6
        String dbVersion = databaseConfig.getDbVersion();
        if (DatabaseEnum.MYSQL_5_7.getKey().equals(dbVersion) || DatabaseEnum.MYSQL_8_0.getKey().equals(dbVersion)) {
            sqlText = "SELECT user,authentication_string AS password " +
                    "FROM mysql.user WHERE user != '' AND user != 'mysql.sys'";
        }
        Map<String, String> userName2PwdMap = new HashMap<>(16);
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sqlText)) {
                while (resultSet.next()) {
                    String userName = resultSet.getString("user");
                    String userPwd = resultSet.getString("password");
                    userName2PwdMap.put(userName, userPwd);
                }
            }
        } catch (SQLException e) {
            log.error("查询{}数据库的用户名密码时发生未知错误", dbAlias, e);
            return;
        }
        // 保存密码较弱的用户名
        List<String> warnNameList = new ArrayList<>();
        for (Map.Entry<String, String> entry : userName2PwdMap.entrySet()) {
            String userName = entry.getKey();
            String pwd = entry.getValue();
            if (pwd == null) {
                continue;
            }
            if (WeakPwdScanUtil.mysqlDictionarySet.contains(pwd)) {
                warnNameList.add(userName);
            }
        }
    }
}
