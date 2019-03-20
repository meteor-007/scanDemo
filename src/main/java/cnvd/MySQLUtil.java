package cnvd;

import java.sql.*;
import java.util.Set;

/**
 * MySQL工具类
 *
 * @author zml
 * @date 2018-7-25
 */
public class MySQLUtil {


    /**
     * 数据库连接
     */
    Connection conn = null;

    /**
     * 获取数据库连接
     */
    private void createConnect() {
        String url = "jdbc:mysql://" + Constant.IP + ":" + Constant.PORT + "/" + Constant.DB_NAME;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, Constant.USER, Constant.PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取漏洞库里已经存在的URL
     *
     * @param oldUrlSet 将获取到的url保存在Set里
     */
    public void queryUrlSet(Set<String> oldUrlSet) {

        // 第一次执行SQL语句前获取数据库连接
        if (conn == null) {
            createConnect();
        }
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(Constant.QUERY_URL_SQL)) {
            while (resultSet.next()) {
                oldUrlSet.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将解析到的数据保存到数据库中
     *
     * @param cnvdDb 待入库的数据
     */
    public void insertInfo(CnvdDb cnvdDb) {

        // 第一次执行SQL语句前获取数据库连接
        if (conn == null) {
            createConnect();
        }
        try (PreparedStatement ps = conn.prepareStatement(Constant.INSERT_SQL);) {
            ps.setString(1, cnvdDb.getTitle());
            ps.setString(2, cnvdDb.getCnvdId());
            ps.setString(3, cnvdDb.getReleaseTime());
            ps.setString(4, cnvdDb.getHazardLevel());
            ps.setString(5, cnvdDb.getAttackRoute());
            ps.setString(6, cnvdDb.getAttackComplexity());
            ps.setString(7, cnvdDb.getCertification());
            ps.setString(8, cnvdDb.getConfidentiality());
            ps.setString(9, cnvdDb.getIntegrity());
            ps.setString(10, cnvdDb.getAvailability());
            Float score = cnvdDb.getScore();
            if (score != null) {
                ps.setFloat(11, score);
            } else {
                // 并不是所有的漏洞都有分数，如果未解析到分数，则保存null
                ps.setString(11, null);
            }
            ps.setString(12, cnvdDb.getAffectedProduct());
            ps.setString(13, cnvdDb.getBugtraqId());
            ps.setString(14, cnvdDb.getBugtraqLink());
            ps.setString(15, cnvdDb.getOtherId());
            ps.setString(16, cnvdDb.getCveId());
            ps.setString(17, cnvdDb.getCveLink());
            ps.setString(18, cnvdDb.getDescription());
            ps.setString(19, cnvdDb.getReferenceLink());
            ps.setString(20, cnvdDb.getSolution());
            ps.setString(21, cnvdDb.getPatch());
            ps.setString(22, cnvdDb.getPatchLink());
            ps.setString(23, cnvdDb.getVerifyMessage());
            ps.setString(24, cnvdDb.getReportingTime());
            ps.setString(25, cnvdDb.getInclusionTime());
            ps.setString(26, cnvdDb.getUpdateTime());
            ps.setString(27, cnvdDb.getAnnex());
            ps.setString(28, cnvdDb.getUrl());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
