package 多线程;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @auther: czwei
 * @date: 2019/5/23 19:53
 */
public class ThreadMode {

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

    public Thread getThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 合并数组
                char[] chars = mergeArray(shuZi, ziMu);
                // 穷举
                try {
                    String[] strings = generate(2, chars);
                    for (String clearText : strings) {
                        if (clearText.equalsIgnoreCase("a2s3")) {
                            System.out.println(clearText);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return thread;
    }

    /**
     * 穷举打印输出，可以将打印输出的文件形成字典
     *
     * @param maxLength：生成的字符串的最大长度
     */
    public static String[] generate(int maxLength, char[] chars) throws Exception {

        //设置需要开启的线程数
        int threadNum = 2;
        //CountDownLatch实现使用一个计数器，而参数cout就是初始化计数器的值，该值一经初始化就不能再被修改。
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        //计数器，多线程时可以对其加锁，当然得先转换成Integer类型。
        List<String> cursList = new ArrayList<>(16);
        double length = Math.pow((double) maxLength, (double) (chars.length & 0xff));
        String[] strings = new String[(int) length];
        int counter = 0;
        int a = 0;
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
            strings[a] = buider.toString();
        }
        return strings;
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
