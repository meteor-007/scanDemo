//package cnvd.weakpwdscan.sysbase;
//
//import cnvd.weakpwdscan.DatabaseConfig;
//import cnvd.weakpwdscan.SysUtils;
//import cnvd.weakpwdscan.WeakPwdScanUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Sybase弱口令扫描类
// * @author zml
// * @date 2018-5-25
// */
//public class SybaseWeakPwdScan {
//
//    private static final Logger log = LoggerFactory.getLogger(SybaseWeakPwdScan.class);
//    /**
//     * Sybase弱口令扫描
//     * @param databaseConfig
//     */
//    public void sybase(DatabaseConfig databaseConfig) {
//
////        String dbAlias = databaseConfig.getDbAlias();
////        Object object = SysUtils.queryDbConnectInfo(databaseConfig, false);
////        if (object == null) {
////            log.error("获取{}数据库的连接为null", dbAlias);
////            return;
////        }
////        Connection conn = (Connection) object;
//        String sqlText = "SELECT name,password FROM master..syslogins";
//        Map<String, String> userName2PwdMap = new HashMap<>(16);
//        try (Statement statement = conn.createStatement()) {
//            try (ResultSet resultSet = statement.executeQuery(sqlText)) {
//                while (resultSet.next()) {
//                    String userName = resultSet.getString("name");
//                    String userPwd = resultSet.getString("password");
//                    userName2PwdMap.put(userName, userPwd);
//                }
//            }
//        } catch (SQLException e) {
//            log.error("查询{}数据库的用户名密码时发生未知错误", dbAlias, e);
//            return;
//        }
//        // 保存密码较弱的用户名
//        List<String> warnNameList = new ArrayList<>();
//        for (Map.Entry<String, String> entry : userName2PwdMap.entrySet()) {
//            String userName = entry.getKey();
//            String pwd = entry.getValue();
//            if (pwd == null) {
//                continue;
//            }
//            for (String string : WeakPwdScanUtil.dictionarySet) {
//                byte[] b = (string + userName).getBytes();
//                byte[] c = WeakPwdScanUtil.encodePwd("MD5", b);
//                // 数据字典中加密后的密文
//                String string2Pwd = WeakPwdScanUtil.byte2HexString(c);
//                // 加密后的密文与数据库中存储的密文一致，证明该密码在我们的数据字典中，则该密码较弱
//                if (string2Pwd.equals(pwd.substring(3).toUpperCase())) {
//                    warnNameList.add(userName);
//                    break;
//                }
//            }
//        }
//    }
//}
