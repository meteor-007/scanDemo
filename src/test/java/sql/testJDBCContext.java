package sql;

import password.TestPassword;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;

/**
 * @description: 向数据库插入文本内容和读取文本内容
 * @author: czwei
 * @create: 2019-01-15 11:27
 */
public class testJDBCContext {

    private static String str;

    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://172.19.1.29:3306/aas_vs";
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String password = "Ankki_mySQL123";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
        if (conn != null) {
            System.out.println("获取连接成功");
            insert(conn);
        } else {
            System.out.println("获取连接失败");
        }
        System.out.println("Successfully connected");

//        // 字符串转数组  java.lang.String
//        String str = "012345";
////        String[] str1 = str.split(",",1);
////        System.out.println(Arrays.toString(str1));
//        String[] arr = str.split(""); // 用,分割
//        for (int i=0;i< arr.length;i++) {
//            System.out.println(arr[i]);
//        }
////        System.out.println(Arrays.toString(arr)); // [0, 1, 2, 3, 4, 5]
    }

    public static void insert(Connection conn) throws FileNotFoundException, SQLException, UnsupportedEncodingException {

        //开始时间
        Long begin = System.currentTimeMillis();
        System.out.println("开始时间:" + begin);
        // sql前缀
        StringBuilder prefix = new StringBuilder("INSERT INTO public_dictionary(db_type,cipher_text,clear_dictionary_id,wp_way) VALUES ");

//        StringBuilder prefix = new StringBuilder("INSERT INTO clear_dictionary(dic_library_id,clear_text) VALUES ");
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        InputStream inputStream = new FileInputStream("D:\\gbase.txt");
        inputReader = new InputStreamReader(inputStream, "utf8");
        bufferReader = new BufferedReader(inputReader);
        // 读取一行
        String line = null;
        //StringBuffer strBuffer = new StringBuffer();
        // 保存sql后缀
        //StringBuffer suffix = new StringBuffer();
        // 设置事务为非自动提交
        try {
            //conn.setAutoCommit(false);
            // 准备执行语句
            Statement st = conn.createStatement();
            /*PreparedStatement ps = conn.prepareStatement(prefix);
            ps.setInt(1, 1);
            ps.setInt(2, 4);
            ps.setInt(5, 0);*/
            int index = 0;
            while ((line = bufferReader.readLine()) != null) {
                index++;
                prefix.append("(9,");
//                line = line.replace("\\", "\\\\").replace("*", "\\*")
//                        .replace("+", "\\+").replace("|", "\\|")
//                        .replace("{", "\\{").replace("}", "\\}")
//                        .replace("(", "\\(").replace(")", "\\)")
//                        .replace("^", "\\^").replace("$", "\\$")
//                        .replace("[", "\\[").replace("]", "\\]")
//                        .replace("?", "\\?").replace(",", "\\,")
//                        .replace(".", "\\.").replace("&", "\\&")
//                        .replace("'", "\\\\\'").replace("\"", "\\\\\"");
                //ps.setString(3, line);
//                prefix.append("\"" + line + "\"),");
                //                    String cipherText = TestPassword.mysql(line);
//                    String cipherText = TestPassword.pgsql(line);
                line = line.replace("\\", "\\\\").replace("*", "\\*")
                            .replace("+", "\\+").replace("|", "\\|")
                            .replace("{", "\\{").replace("}", "\\}")
                            .replace("(", "\\(").replace(")", "\\)")
                            .replace("^", "\\^").replace("$", "\\$")
                            .replace("[", "\\[").replace("]", "\\]")
                            .replace("?", "\\?").replace(",", "\\,")
                            .replace(".", "\\.").replace("&", "\\&")
                            .replace("'", "\\'").replace("\"", "\\\"");
                    prefix.append("'" + line + "',"+ index + ",1),");
//                prefix.append("\'" + line + "\'),");
                if (index % 10000 ==0) {
                    String sql = prefix.toString();
                    sql = sql.substring(0, sql.length() - 1);
                    st.executeUpdate(sql);
                    prefix = new StringBuilder("INSERT INTO public_dictionary(db_type,cipher_text,clear_dictionary_id,wp_way) VALUES ");
//                    prefix = new StringBuilder("INSERT INTO clear_dictionary(dic_library_id,clear_text) VALUES ");
                }
                //suffix = new StringBuffer();
                //strBuffer.append(str);
               /* // 按照相应规则截取字符串
                String a[] = line.split(" ");
                String s = "";
                for (int i = 1; i < a.length; i++) {
                    s += a[i]+ " ";
                }*/
                // 去掉字符串开头和结尾的空格
//                String ss = s.trim();
//                System.out.println(a[0]);
//                System.out.println(ss);
//                suffix = new StringBuffer();
                // 构建SQL后缀

                // }
                // 构建完整SQL
                //String sql = prefix + suffix.substring(0, suffix.length() - 1);
               /* // 添加执行SQL
                ps.addBatch(sql);
                // 执行操作
                ps.executeBatch();
                // 提交事务
                conn.commit();*/
            }
            String sql = prefix.toString();
            sql = sql.substring(0, sql.length() - 1);
            st.executeUpdate(sql);
//            ps.close();
            st.close();
            conn.close();
            bufferReader.close();
            //结束时间
            Long end = System.currentTimeMillis();
            System.out.println("消耗时间:" + (end - begin) / 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

