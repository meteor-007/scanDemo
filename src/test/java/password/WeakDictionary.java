package password;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Todo:弱口令扫描：
 * 基于用户规则初始化字典
 * 基于用户的密码长度（数字，字符，制定字符）
 *
 * @author $ czwei
 * @create 2018/12/21
 */
public class WeakDictionary {

//    private static FileWriter fileWriter = null;

    private static FileOutputStream fis = null;

    private static OutputStreamWriter osw = null;

    /**
     * 弱口令基于用户名规则初始化字典
     *
     * @param name
     * @param a
     * @return
     */
    public static String[] userNameRule(String name, int a) { //
        int var5 = 0;
        switch (a) {
            // 与用户名相同
            case 3:
                return new String[]{name, name.toLowerCase(), name.toUpperCase()};
            // 用户+简短数字
            case 4:
                ArrayList var9 = new ArrayList();
                int var7;
                for (var7 = 0; var7 <= 1000; ++var7) {
                    var9.add(name.toUpperCase() + var7);
                }
                for (var7 = 0; var7 <= 1000; ++var7) {
                    var9.add(name + var7);
                }
                for (var7 = 0; var7 <= 1000; ++var7) {
                    var9.add(name.toLowerCase() + var7);
                }
                return (String[]) var9.toArray(new String[0]);
            // 用户名+常见年份
            case 5:
                ArrayList var10 = new ArrayList();
                for (var5 = 1950; var5 <= 2050; ++var5) {
                    var10.add(name.toUpperCase() + var5);
                }
                for (var5 = 1950; var5 <= 2050; ++var5) {
                    var10.add(name + var5);
                }
                for (var5 = 1950; var5 <= 2050; ++var5) {
                    var10.add(name.toLowerCase() + var5);
                }
                return (String[]) var10.toArray(new String[0]);
            // 用户名反转
            case 6:
                StringBuffer var2 = new StringBuffer();
                for (var5 = name.length() - 1; var5 >= 0; --var5) {
                    var2.append(name.charAt(var5));
                }
                StringBuffer var6 = new StringBuffer();
                for (int var3 = name.length() - 1; var3 >= 0; --var3) {
                    var6.append(name.toUpperCase().charAt(var3));
                }
                StringBuffer var8 = new StringBuffer();
                for (int var4 = name.length() - 1; var4 >= 0; --var4) {
                    var8.append(name.toLowerCase().charAt(var4));
                }
                return new String[]{var2.toString(), var6.toString(),
                        var8.toString()};
            default:
                return new String[0];
        }
    }

    /**
     * Todo: 基于密码长度
     *
     * @Author:$czwei
     * @Date: 2018/12/19 14:30
     */
    private static StringBuffer l = new StringBuffer();
    private static int r = 3;// 密码不大于r位数
    private static final char[] shuzi = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] zimu = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] teshuzifu = new char[]{'`', '~', '!', '@',
            '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', '{',
            '[', '}', ']', ';', ':', '\'', '\"', '\\', '|', ',', '<', '.', '>',
            '/', '?', ' '};

    private static final char[] test = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-',
            '=', '_', '+', '{', '[', '}', ']', ';', ':'};
    private static final String[] test1 = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z",
            "`", "~", "!", "@", "#", "$", "%", "^", "&", "*",
            "(", ")", "-", "=", "_", "+", "{", "[", "}", "]",
            ";", ":", "\'", "\"", "\\", "|", ",", "<", ".", ">",
            "/"};//, "?"
    // 根据用户选择数字，字母，特殊字符，以及指定字符合并成的字符数组
    private static char[] q = new char[]{'a', 'n', 'k', 'i'};
    // 现在测试为ankki

    /**
     * 弱口令基于密码长度进行初始化字典
     * 1.
     */
    private static void f() throws IOException {

        boolean a = l.length() != r - 1;
        List<String> list = new ArrayList<>(16);
        for (int i = 0; i < q.length; ++i) {
            l.append(test[i]);
//            fis= new FileOutputStream("D:\\数据库漏洞扫描\\数据库漏洞扫描\\密码字典\\password-4.txt",true);
//            osw = new OutputStreamWriter(fis,"UTF-8");
//            osw.write(l.toString() + "\t\n");
            System.out.println(l.toString());
            list.add(l.toString());
            // 这里进行匹配密码，如果有匹配到就记录已经匹配到的用户并删除
            if (a) {
                f();
            }
            l.deleteCharAt(l.length()-1);
        }
        System.out.println(list);
//        osw.flush();
    }

    public static void main(String[] args) throws IOException {
        f();
//        osw.close();
    }

}
