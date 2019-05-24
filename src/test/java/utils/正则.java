package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Auther: czwei
 * @Date: 2019/5/9 09:56
 */
public class 正则 {

    public static void main(String[] args) {
        // 要验证的字符串
//        String str = "VSLib20190509";
        String str = "VSLib20190509_001";
        String str1 = str.substring(5,13);
        // 邮箱验证规则
//        String regEx = "(^[VSLib]{5}$[0-9]{4}[0-9]{2}[0-9]{2}[_][0-9]{3})";
        String regEx =  "(^[0-9]{4}[0-9]{2}[0-9]{2}[_][0-9]{3})";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        System.out.println(rs);
        System.out.println(str1);
    }

    public boolean matcherRegEx(String str,String regEx){
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        return rs;
    }
}
