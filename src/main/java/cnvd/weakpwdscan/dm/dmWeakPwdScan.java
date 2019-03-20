package cnvd.weakpwdscan.dm;

import cnvd.weakpwdscan.DatabaseConfig;
import cnvd.weakpwdscan.DatabaseEnum;
import cnvd.weakpwdscan.SysUtils;
import cnvd.weakpwdscan.WeakPwdScanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Todo:达梦数据库弱口令扫描类
 *
 * @author $ czwei
 * @create 2018/12/20
 */
public class dmWeakPwdScan {

    private static final Logger log = LoggerFactory.getLogger(dmWeakPwdScan.class);
    /**
     * Todo:MySQL弱口令扫描入口
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
        String sqlText = "select USERNAME, PASSWORD from DBA_USERS";//dm7
        String dbVersion = databaseConfig.getDbVersion();
        if(DatabaseEnum.DM_6.getKey().equals(dbVersion)){
            sqlText = "select ID, NAME AS USERNAME, PASSWORD from SYSLOGINS";
        }
        Map<String, String> userName2PwdMap = new HashMap<>(16);
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sqlText)) {
                while (resultSet.next()) {
                    String userName = resultSet.getString("USERNAME");
                    String userPwd = resultSet.getString("PASSWORD");
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
