package cnvd.weakpwdscan.sqlserver;

import cnvd.weakpwdscan.DatabaseConfig;
import cnvd.weakpwdscan.DatabaseEnum;
import cnvd.weakpwdscan.SysUtils;
import cnvd.weakpwdscan.WeakPwdScanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * SQL Server弱口令扫描类
 *
 * @author zml
 * @date 2018-5-25
 */
public class SqlServerWeakPwdScan {
    private static final Logger log = LoggerFactory.getLogger(SqlServerWeakPwdScan.class);

    /**
     * SQLServer弱口令扫描
     *
     * @param databaseConfig
     */
    public void sqlServer(DatabaseConfig databaseConfig) {
        String dbAlias = databaseConfig.getDbAlias();
        Object object = SysUtils.queryDbConnectInfo(databaseConfig, false);
        if (object == null) {
            log.error("获取{}数据库的连接为null", dbAlias);
            return;
        }
        Connection conn = (Connection) object;

        // 获取该数据库下的用户名与密码的对应关系
        Map<String, String> userName2PwdMap = querySqlServerNameMap(databaseConfig, conn);

        String dbVersion = databaseConfig.getDbVersion();
        if (DatabaseEnum.SQL_SERVER_2005.getKey().equals(dbVersion) ||
                DatabaseEnum.SQL_SERVER_2008.getKey().equals(dbVersion)) {
            sqlServer2005To2008(databaseConfig, userName2PwdMap);
        }
        if (DatabaseEnum.SQL_SERVER_2012.getKey().equals(dbVersion) ||
                DatabaseEnum.SQL_SERVER_2014.getKey().equals(dbVersion) ||
                DatabaseEnum.SQL_SERVER_2016.getKey().equals(dbVersion) ||
                DatabaseEnum.SQL_SERVER_2017.getKey().equals(dbVersion)) {
            sqlServer2012To2017(databaseConfig, userName2PwdMap, conn);
        }
    }

    /**
     * 获取SQLServer 用户名与密码对应关系
     *
     * @param databaseConfig 数据库配置信息
     * @param conn           数据库连接
     * @return 用户名与密码对应关系
     */
    private Map<String, String> querySqlServerNameMap(DatabaseConfig databaseConfig, Connection conn) {
        String dbAlias = databaseConfig.getDbAlias();
        String sqlText = "SELECT name,password_hash FROM sys.sql_logins WHERE name IS NOT NULL";
        Map<String, String> userName2PwdMap = new HashMap<>(16);
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sqlText)) {
                while (resultSet.next()) {
                    String userName = resultSet.getString("name");
                    String userPwd = resultSet.getString("password_hash");
                    userName2PwdMap.put(userName, userPwd);
                }
            }
        } catch (SQLException e) {
            log.error("查询{}数据库的用户名密码时发生未知错误", e);
        }
        return userName2PwdMap;
    }

    /**
     * 处理2005及2008版本的弱口令扫描
     *
     * @param databaseConfig  数据库配置信息
     * @param userName2PwdMap 用户名与密码对应关系
     */
    private void sqlServer2005To2008(DatabaseConfig databaseConfig, Map<String, String> userName2PwdMap) {
        // 保存密码较弱的用户名
        List<String> warnNameList = new ArrayList<>();
        for (Map.Entry<String, String> entry : userName2PwdMap.entrySet()) {
            String userName = entry.getKey();
            String pwd = entry.getValue();
            if (pwd == null) {
                continue;
            }
            for (String string : WeakPwdScanUtil.dictionarySet) {
                byte[] strings = WeakPwdScanUtil.sqlServerEncodePwd(pwd.replaceAll("\\-", "").substring(4, 12), string);
                // 数据字典中加密后的密文
                String string2Pwd = WeakPwdScanUtil.byte2HexString(strings);
                // 加密后的密文与数据库中存储的密文一致，证明该密码在我们的数据字典中，则该密码较弱
                if (string2Pwd.equals(pwd.substring(12))) {
                    warnNameList.add(userName);
                    break;
                }
            }
        }
        System.out.println(warnNameList);
    }

    /**
     * 处理2012到2017版本的弱口令扫描
     *
     * @param databaseConfig  数据库配置信息
     * @param userName2PwdMap 用户名与密码对应关系
     * @param conn            数据库连接
     */
    private void sqlServer2012To2017(DatabaseConfig databaseConfig, Map<String, String> userName2PwdMap, Connection conn) {

        String dbAlias = databaseConfig.getDbAlias();
        // 保存该数据库中所有的用户名
        Set<String> nameSet = userName2PwdMap.keySet();
        // 保存密码较弱的用户名
        List<String> warnNameList = new ArrayList<>();
        // 2012—2017版本的数据库不能进行密码匹配，只能通过执行SQL语句的方式进行处理
        // 遍历我们的数据字典，执行如下SQL语句，如果有返回值，则返回值就是对应密码的用户名
        // 即该用户的密码在我们的数据字典中，该用户的密码为弱密码
        // 注：相同密码的用户可能有多个
        // 注：2005和2008也可以采用这种方式，但是这种方式的效率比较低，因此2005和2008采用密码匹配的方式
        String sqlText = "SELECT name FROM sys.sql_logins WHERE PWDCOMPARE(?, password_hash) = 1";
        try (PreparedStatement statement = conn.prepareStatement(sqlText)) {
            for (String string : WeakPwdScanUtil.dictionarySet) {
                if (nameSet.isEmpty()) {
                    return;
                }
                statement.setString(1, string);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // 有返回结果，证明该用户的密码强度较弱
                        String name = resultSet.getString("name");
                        warnNameList.add(name);
                        // 每次找到匹配的用户名就将该用户名从nameSet中移除掉
                        // 这样当nameSet为空时，证明所有的用户密码均已匹配出，就没必要再进行匹配操作
                        nameSet.remove(name);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("匹配{}数据库的密码时发生未知错误", dbAlias, e);
        }
        System.out.println(warnNameList);
    }

}
