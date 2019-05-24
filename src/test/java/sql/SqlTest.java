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

            // oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url="jdbc:oracle:" + "thin:@172.19.1.142:1521:orcl9i";
            String user = "sys as sysdba";// 用户名,系统默认的账户名
            String password = "Ceshi123";// 你安装时选设置的密码
            con = DriverManager.getConnection(url,user,password);
            String sql ="SELECT u.NAME AS NAME,u.PASSWORD AS PASSWORD FROM sys.user$ u JOIN dba_users d ON u.NAME = d.username";

            // sysbase
//            Class.forName("com.sybase.jdbc2.jdbc.SybDriver").newInstance();
//            String url = "jdbc:sybase:Tds:172.23.1.102:5000/master";// 数据库名
//            String user = "sa";// 用户名,系统默认的账户名
//            String password = "Ceshi123";// 你安装时选设置的密码
//            con = DriverManager.getConnection(url,user,password);
//            String sql ="select name,len(password) as length,password,status from master..syslogins";

            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while(rs.next()){
                String userName = rs.getString("NAME");
                String userPwd = rs.getString("PASSWORD");
                System.out.println(userName+"--->"+userPwd+"--------");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
