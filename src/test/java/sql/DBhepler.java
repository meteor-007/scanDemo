package sql;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * @description:
 * @author: czwei
 * @create: 2019-02-22 18:15
 */
public class DBhepler {

    int idx;
    Connection conn = null;
    PreparedStatement pstmt = null;

    // 使用commons-io.jar包的FileUtils的类进行读取
    public void readTxtFileByFileUtils(String fileName) {
        File file = new File(fileName);
        dbConnection();
        try {
            LineIterator lineIterator = FileUtils.lineIterator(file, "utf8");
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();

                // 行数据转换成数组
                String[] custArray = line.split("\\|");
                insertCustInfo(custArray);
                Thread.sleep(10);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dbDisConnection();
        }
    }

    // 插入到数据库中
    public void insertCustInfo(String[] strArray) {
        try {
            StringBuffer sqlBf = new StringBuffer();
            sqlBf.setLength(0);

            sqlBf.append("INSERT INTO TEMP_CUST_INFO(cipher_text,dic_library_id,db_type,dic_type)                \n");
            sqlBf.append("          VALUES(?                                                    \n");
            sqlBf.append("               , 1                                                    \n");
            sqlBf.append("               , mysql)                                                   \n");
            sqlBf.append("               , 0)                                                   \n");

            pstmt = conn.prepareStatement(sqlBf.toString());
            idx = 1;
            pstmt.clearParameters();
            pstmt.setString(idx++, strArray[0]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 连接数据库
    public Connection dbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://172.19.1.29:3306/vs";
            String user = "root";
            String password = "Ankki_mySQL123";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection 开启！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    // 关闭数据库
    public void dbDisConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection 关闭！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DBhepler rcf = new DBhepler();
        rcf.readTxtFileByFileUtils("D:\\数据库漏洞扫描\\数据库漏洞扫描\\弱口令字典\\mysql.txt");
    }


}
