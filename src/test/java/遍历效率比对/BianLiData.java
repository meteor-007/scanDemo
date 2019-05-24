package 遍历效率比对;

import java.io.File;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.junit.Before;

/**
 * Todo: 测试读取txt文件
 *
 * @Author:$czwei
 * @Date: 2018/12/21 11:25
 */
public class BianLiData {

    private static int leng = 2000000;
    private static String[] array;
    private static Set<String> set;
    private static List<String> list;
    private static Queue<String> queue;
    private static Map<String, String> map;

    public static void main(String[] args) {


        array = new String[leng];
        set = new HashSet<String>();
        list = new ArrayList<String>();
        queue = new LinkedList<String>();
        map = new HashMap<String, String>();
        for (int i = 0; i < leng; i++) {
            String key = "didi:" + i;
            String value = "da";
            array[i] = key;
            set.add(key);
            list.add(key);
            queue.add(key);
            map.put(key, value);
        }
//        list.stream().forEach(string ->{
//            System.out.println(string);
//        });

        testArray();
//        testList();
//        testMap();
//        testQueue();
//        testSet();

        long beginMillis = System.currentTimeMillis();
        List<Integer> li = new ArrayList<>(16);
        for (int i = 0; i < 10000000; i++) {
            li.add(i);
        }
        //使用ListIterator
        ListIterator<Integer> lit = li.listIterator(li.size());
        //此时，迭代器已经到达ArrayList的尾部，所以可以开始从后往前的遍历了
        while (lit.hasPrevious()) {
            System.out.println(lit.previous());
        }
        long endMillis = System.currentTimeMillis();
        System.out.println(endMillis - beginMillis);//总耗时，毫秒
    }

    private static void testArray() {
//        Long startTime = new Date().getTime();
//        for (String sk : array) {
//            System.out.println(sk);
//        }
//        Long endTime = new Date().getTime();
//        Long times = endTime - startTime;
//        System.out.println("时间：" + times);
//        Long startTime1 = new Date().getTime();
//        int a = array.length;
//        for (int i = 0; i < a; i++) {
//            System.out.println(array[i]);
//        }
//        Long endTime1 = new Date().getTime();
//        Long times1 = endTime1 - startTime1;
//        System.out.println("时间：" + times1);
        Long startTime2 = new Date().getTime();
        List list=Arrays.asList(array);
        Long endTime2 = new Date().getTime();
        Long times2 = endTime2 - startTime2;
        System.out.println("时间：" + times2);

    }

    public static void testList() {
        Long startTime = new Date().getTime();
        for (String sk : list) {
//            System.out.println(sk);
        }
//        list.stream().forEach(string -> {
////            System.out.println(string);
//        });
        Long endTime = new Date().getTime();
        Long times = endTime - startTime;
        System.out.println("时间：" + times);
    }

    public static void testMap() {
        Long startTime = new Date().getTime();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            entry.getKey();
        }
        Long endTime = new Date().getTime();
        Long times = endTime - startTime;
        System.out.println("时间：" + times);
//        Long startTime1 = new Date().getTime();
//        for (String key : map.keySet()) {
//            String value = (String) map.get(key);
//        }
//        Long endTime1 = new Date().getTime();
//        Long times1 = endTime1 - startTime1;
//        System.out.println("时间1：" + times1);
    }


    public static void testQueue() {
        Long startTime = new Date().getTime();
        for (String s : queue) {
//            System.out.println(s);
        }
        Long endTime = new Date().getTime();
        Long times = endTime - startTime;
        System.out.println("时间1：" + times);
    }


    public static void testSet() {
        Long startTime = new Date().getTime();
        for (String s : set) {
//            System.out.println(s);
        }
        Long endTime = new Date().getTime();
        Long times = endTime - startTime;
        System.out.println("时间：" + times);
    }

}
