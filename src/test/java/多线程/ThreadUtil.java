package 多线程;

import password.TestPassword;
import 穷举.WpTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程工具类
 *
 * @auther: czwei
 * @date: 2019/5/22 15:49
 */

public class ThreadUtil {

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

    /**
     * 多线程处理list
     * 这里我改造成了静态方法
     *
     * @param data           数据list
     * @param countDownLatch 协调多个线程之间的同步
     * @param threadNum      开启的线程数:也可以使用countDownLatch.getCount();//来获取开启的线程数但是要注意这个会是一个风险。因为这是一个可变的数。而控制他改变的罪魁祸首就是countDownLatch.countDown();
     */
    public static synchronized void handleList(List<String> data, CountDownLatch countDownLatch, int threadNum) {
        //获取数据的总数
        int length = data.size();
        //计算每个线程平均处理的数据
        int tl = length % threadNum == 0 ? length / threadNum : (length / threadNum + 1);
        for (int i = 0; i < threadNum; i++) {
            //获得每个线程的最后下标(避免有余数导致数据错误所以前面的线程下标+1)
            int end = (i + 1) * tl;
            //最后一个线程拿到的是剩余的数据
            HandleThread thread = new HandleThread(
                    "线程[" + (i + 1) + "] ", data, i * tl, end > length ? length : end, countDownLatch);
            //开启线程运行run方法进行数据处理
            thread.start();
        }
    }

    /**
     * 内置类继承线程类
     * 这里为了方便大家学习就直接这样写了.
     */
    static class HandleThread extends Thread {
        //线程名称
        private String threadName;
        //该线程负责的数据
        private List<String> data;
        //开始集合的下标
        private int start;
        //结束集合的下标
        private int end;
        //协调多个线程之间的同步
        private CountDownLatch countDownLatch;

        /**
         * 无参构造函数
         */
        public HandleThread() {
            super();
        }

        /**
         * 带参构造方法
         *
         * @param threadName     当前线程名称
         * @param data           数据
         * @param start          开始的下标
         * @param end            结束的下标
         * @param countDownLatch 协调多个线程之间的同步
         */
        public HandleThread(String threadName, List<String> data, int start, int end, CountDownLatch countDownLatch) {
            this.threadName = threadName;
            this.data = data;
            this.start = start;
            this.end = end;
            this.countDownLatch = countDownLatch;
        }

        /**
         * 重写Thread的run方法  调用start方法之后自动调用run方法
         */
        public void run() {
            // 这里处理数据
            //获取当前线程需要处理的数据
            List<String> subList = data.subList(start, end);
            for (int a = 0; a < subList.size(); a++) {
                if (subList.get(a).equalsIgnoreCase("1002")) {
                    System.out.println(threadName + "处理了:" + subList.get(a) + "  ！");
                }
            }
            System.out.println(threadName + "处理了 " + subList.size() + "条数据！");
            // 执行子任务完毕之后，countDown减少一个点
            countDownLatch.countDown();
        }
    }

    /**
     * 使用main方法进行测试
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        // 合并数组
        char[] chars = mergeArray(shuZi, ziMu);
        // 穷举
        generate(4, chars);
        //设置需要开启的线程数
//        int threadNum = 10;
        //CountDownLatch实现使用一个计数器，而参数cout就是初始化计数器的值，该值一经初始化就不能再被修改。
//        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
//        ThreadUtil.handleList(data, countDownLatch, threadNum);
        // 调用await方法阻塞当前线程，等待子线程完成后在继续执行
//        countDownLatch.await();
        long t2 = System.currentTimeMillis();
        System.out.println("=============主线程执行完毕!================" + "消耗时间:" + (t2 - t1) / 10000 + "/S");
    }

    /**
     * 穷举打印输出，可以将打印输出的文件形成字典
     *
     * @param maxLength：生成的字符串的最大长度
     */
    public static List<String> generate(int maxLength, char[] chars) throws Exception {

        //设置需要开启的线程数
        int threadNum = 2;
        //CountDownLatch实现使用一个计数器，而参数cout就是初始化计数器的值，该值一经初始化就不能再被修改。
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        //计数器，多线程时可以对其加锁，当然得先转换成Integer类型。
        List<String> cursList = new ArrayList<>(16);
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
            cursList.add(buider.toString());
        }
        cursList = cursList.subList(0, cursList.size() - 1);
        return cursList;
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

