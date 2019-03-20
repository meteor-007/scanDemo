package password;

import org.junit.Ignore;

import java.util.*;

/**
 * @Description:
 * @Auther: czwei
 * @Date: 2019/3/6 18:35
 */
public class WpTest {

    //密码可能会包含的字符集合
    private static char[] fullCharSource = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '~', '!', '@', '#', '$', '%', '^', '&',
            '*', '(', ')', '_', '+', '{', '}', '|', ':', '"',
            '<', '>', '?', ';', '\'', ',', '.', '/', '-', '=',
            '`'};
    // 数字-1
    private static char[] shuZi = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    // 字母-1
    private static char[] ziMu = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'};
    // 特殊字符-1
    private static char[] teShuZiFu = {
            '~', '!', '@', '#', '$', '%', '^', '&', '*',
            '(', ')', '_', '+', '{', '}', '|', ':', '"',
            '<', '>', '?', ';', '\'', ',', '.', '/', '-',
            '=', '`'};

    //将可能的密码集合长度
//    private static int fullCharLength = fullCharSource.length;

    /**
     * 穷举打印输出，可以将打印输出的文件形成字典
     *
     * @param maxLength：生成的字符串的最大长度
     */
    public static List<String> generate(int maxLength, char[] chars) {
        //计数器，多线程时可以对其加锁，当然得先转换成Integer类型。
        List<String> cursList = new ArrayList<>(16);
        int counter = 0;
        StringBuilder buider = new StringBuilder();
        while (buider.toString().length() <= maxLength) {
            buider = new StringBuilder(maxLength * 2);
            int _counter = counter;
            //10进制转换成26进制
            while (_counter >= chars.length) {
                //获得低位
                buider.insert(0, chars[_counter % chars.length]);
                _counter = _counter / chars.length;
                //精髓所在，处理进制体系中只有10没有01的问题，在穷举里面是可以存在01的
                _counter--;
            }
            //最高位
            buider.insert(0, chars[_counter]);
            counter++;
//            cursList.add(buider.toString());
        }
//        cursList.remove(cursList.size() - 1);
        return cursList;
    }

    public static void main(String[] args) {
        long beginMillis = System.currentTimeMillis();
        String s = "wdwddwqdqqee";
//        String[] array = s.split("");
//        //实例化一个set集合
//        Set set = new HashSet();
//        //遍历数组并存入集合,如果元素已存在则不会重复存入
//        Collections.addAll(set, array);
//        //返回Set集合的数组形式
//        String s1 = Arrays.toString(set.toArray());
        char[] array1 = removeRepeatChar(s).toCharArray();

        char[] chars = mergeArray(shuZi,array1);
        System.out.println(chars);
        List<String> list = generate(1,chars);//以最大长度为50测试
        System.out.println(list);
        long endMillis = System.currentTimeMillis();
        System.out.println((endMillis - beginMillis));//总耗时，毫秒
    }

    public static String removeRepeatChar(String str) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i ++) {
            char charWord = str.charAt(i);
            int first = str.indexOf(charWord);
            int last = str.lastIndexOf(charWord);
            if (first  == last || first == i) {
                sb.append(charWord);
            }
        }
        return sb.toString();
    }



    /***
     * 合并字符数组
     *
     * @param a
     * @return
     */
    public static char[] mergeArray(char[]... a) {
        // 合并完之后数组的总长度
        int index = 0;
        int sum = 0;
        for (char[] chars : a) {
            sum = sum + chars.length;
        }
        char[] result = new char[sum];
        for (char[] chars : a) {
            int lengthOne = chars.length;
            if (lengthOne == 0) {
                continue;
            }
            // 拷贝数组
            System.arraycopy(chars, 0, result, index, lengthOne);
            index = index + lengthOne;
        }
        return result;
    }
}
