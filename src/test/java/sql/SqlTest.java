package sql;

import java.sql.*;

/**
 * Todo:sql 语句测试
 *
 * @author $ czwei
 * @create 2018/12/25
 */
public class SqlTest {

    public static void main(String[] args) {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet rs = null;// 创建一个结果集对象
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url="jdbc:oracle:" + "thin:@172.23.1.161:1521:orcl8i";
            String user = "Sys";// 用户名,系统默认的账户名
            String password = "Ceshi123";// 你安装时选设置的密码
            con = DriverManager.getConnection(url,user,password);
            /**
             * Todo: db2 数据库
             * @Author:$czwei
             * @Date: 2018/12/25 15:57
             */
//            Class.forName("com.ibm.db2.jdbc.app.DB2Driver ").newInstance();
//            String url="jdbc:db2://localhost:5000/sample";
            // oracle
            String sql ="SELECT name,password,spare4 FROM sys.user$";
//                    String sql = "SELECT name,password,spare4 FROM sys.user$ WHERE name IN (SELECT name FROM dba_users WHERE authentication_type='PASSWORD')";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while(rs.next()){
                String userName = rs.getString("NAME");
                String userPwd = rs.getString("PASSWORD");
                String userSpare = rs.getString("SPARE$");
                System.out.println(userName+"--->"+userPwd+"--------" + userSpare);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
