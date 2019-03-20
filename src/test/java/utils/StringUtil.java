package utils;

import java.util.*;

/**
 * @description: 字符串工具类测试类
 * @author: czwei
 * @create: 2019-01-15 16:25
 */
public class StringUtil {

    public static void main(String[] args) {

        // 字符串转义
        String[] rs = {"!!'", "!!(", "!!)", "!!*"};
        for (int i = 0; i < rs.length; i++) {
            rs[i].replace("\\", "\\\\").replace("*", "\\*")
                    .replace("+", "\\+").replace("|", "\\|")
                    .replace("{", "\\{").replace("}", "\\}")
                    .replace("(", "\\(").replace(")", "\\)")
                    .replace("^", "\\^").replace("$", "\\$")
                    .replace("[", "\\[").replace("]", "\\]")
                    .replace("?", "\\?").replace(",", "\\,")
                    .replace(".", "\\.").replace("&", "\\&")
                    .replace("'", "\'").replace("\\\"", "\"");
            System.out.println(i + ":" + rs[i]);
        }

        // 1.char数组(字符数组)->字符串
/*        char[] arr={'a','b','c'};
        String string =String.copyValueOf(arr);
        //abc
        System.out.println(string);*/

        // 2、String数组->字符串
/*        String[] arr ={"0123","sb","12f"};
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<arr.length;i++){
        //append String并不拥有该方法，所以借助StringBuffer
        sb.append(arr[i]);
        }
        String sb1 = sb.toString();
        //0123sb12f
        System.out.println(sb1);*/

        // 3.java字符串->数组
/*        String str = "123abc";
        char[] ar = str.toCharArray();//char数组
        for (int i = 0; i < ar.length; i++) {
            System.out.println(ar[i]);    //1 2 3 a b c
        }
        String[] arr = str.split("");
        for (int i = 0; i < arr.length; i++) {//String数组，不过arr[0]为空
            System.out.println(arr[i]);    //空  1 2 3 a b c
        }*/

        //##字符串逆序
//        String s = "123abc";
//        System.out.println(new StringBuilder(s).reverse().toString());

        // 字符串去空格
        //　　1. String.trim() 　　trim()是去掉首尾空格
        //　　2.str.replace(" ", ""); 　　去掉所有空格，包括首尾、中间

        //法一去重复：利用set的不可以存重复对象，去重复【对象要实现hashcode、equals】
       /* List<Object> listVms = new ArrayList<>(16);
        Set set = new HashSet();
        for (Object object : listVms) {
            set.add(object);
        }
        System.out.println("***************::" + set.size());

        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");
        List<String> list2 = new ArrayList<String>();
        list2.add("B");
        list2.add("C");
        //1.并集
        list1.addAll(list2);
        //2.无重复并集
        list2.removeAll(list1);
        list1.addAll(list2);
        //3.交集
        list1.retainAll(list2);
        // 4.差集
        list1.removeAll(list2);


        // 集合遍历
        //第一种
        for (int i = 0; i < list1.size(); i++) {
            System.out.print("第" + i + "个=？" + list1.get(i));
        }
        // 第二种
        for (String example : list1) {
            String name = example;//直接操作Example对象
            System.out.print(("Name:" + name));
        }
        //第三种
        Iterator<String> iter = list1.iterator();
        long t1 = System.currentTimeMillis();
        while (iter.hasNext()) {
            iter.next();
            System.out.println(iter.next());
        }*/

        // 遍历map
        // for
/*        Map<String, String> map = new HashMap<>(16);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }

        // 迭代器
        Set set1 = map.entrySet();
        Iterator i = set1.iterator();
        while (i.hasNext()) {
            Map.Entry<String, String> entry1 = (Map.Entry<String, String>) i.next();
            System.out.println(entry1.getKey() + "==" + entry1.getValue());
        }

        //用keySet()迭代
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key;
            String value;
            key = it.next().toString();
            value = map.get(key);
            System.out.println(key + "--" + value);
        }

        //Entryset 迭代
        Iterator it1 = map.entrySet().iterator();
        System.out.println(map.entrySet().size());
        String key;
        String value;
        while (it1.hasNext()) {
            Map.Entry entry = (Map.Entry) it1.next();
            key = entry.getKey().toString();
            value = entry.getValue().toString();
            System.out.println(key + "====" + value);
        }*/

        //知识点：java 中如何将 "字符串数组" 合并成 "一个字符串" 例如String [] ss = new String [n] 字符串数组中有n个
/*        String[] str = new String[3];
        str[0] = "first";
        str[1] = "second";
        str[2] = "third";
        //方法1
        String allStr1;
        allStr1 = str[0].concat(str[1]).concat(str[2]);
        //方法2
        String allStr2;
        allStr2 = str[0] + str[1] + str[2];
        //方法3
        StringBuffer sb = new StringBuffer();
        String allStr3 = sb.append(str[0]).append(str[1]).append(str[2]).toString();
        System.out.println(allStr1);
        System.out.println(allStr2);
        System.out.println(allStr3);
        //方法4
        StringBuffer sb1 = new StringBuffer();
        String allStr4 = "";
        for (int a = 0; a < str.length; a++) {
            allStr4 = sb1.append(str[a]).toString();
        }
        System.out.println(allStr4);*/

    }

    /***
     * 合并字节数组
     *
     * @param a
     * @return
     */
    public static byte[] mergeArray(byte[]... a) {
        // 合并完之后数组的总长度
        int index = 0;
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i].length;
        }
        byte[] result = new byte[sum];
        for (int i = 0; i < a.length; i++) {
            int lengthOne = a[i].length;
            if (lengthOne == 0) {
                continue;
            }
            // 拷贝数组
            System.arraycopy(a[i], 0, result, index, lengthOne);
            index = index + lengthOne;
        }
        return result;
    }
    /***
     * append a byte.
     *
     * @param a
     * @param b
     * @return
     */
    public static byte[] appandByte(byte[] a, byte b) {
        int length = a.length;
        byte[] resultBytes = new byte[length + 1];
        System.arraycopy(a, 0, resultBytes, 0, length);
        resultBytes[length] = b;
        return resultBytes;
    }
    /***
     * Get top <code>frontNum</code> bytes
     *
     * @param source
     * @param frontNum
     * @return
     */
    public static byte[] getFrontBytes(byte[] source, int frontNum) {
        byte[] frontBytes = new byte[frontNum];
        System.arraycopy(source, 0, frontBytes, 0, frontNum);
        return frontBytes;
    }

    public static byte[] getAfterBytes(byte[] source, int afterNum) {
        int length = source.length;
        byte[] afterBytes = new byte[afterNum];
        System.arraycopy(source, length - afterNum, afterBytes, 0, afterNum);
        return afterBytes;
    }
    /***
     *
     * @param frontNum
     * @param source
     * @return
     */
    public static byte[] filterFrontBytes(int frontNum, byte[] source) {
        return copyByte(frontNum, source.length - frontNum, source);
    }

    public static byte[] copyByte(int start, int length, byte[] source) {
        byte[] des = new byte[length];
        System.arraycopy(source, start, des, 0, length);
        return des;
    }


    /***
     * 合并字符串数组
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] mergeArray2(String[]arr1,String[]arr2){
        int length1=arr1.length;
        int length2=arr2.length;
        int totalLength=length1+length2;
        String[]totalArr=new String[totalLength];
        for(int i=0;i<length1;i++){
            totalArr[i]=arr1[i];
        }
        for(int i=0;i<length2;i++){
            totalArr[i+length1]=arr2[i];
        }
        return totalArr;
    }

    /***
     * merge two int array to a string
     *
     * @param a
     * @param b
     * @return
     */
    public static String merge(int[] a, int[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
            sb.append(",");
        }
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
            sb.append(",");
        }
        int leng_str = sb.toString().length();
        return sb.substring(0, leng_str - 1);
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
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i].length;
        }
        char[] result = new char[sum];
        for (int i = 0; i < a.length; i++) {
            int lengthOne = a[i].length;
            if (lengthOne == 0) {
                continue;
            }
            // 拷贝数组
            System.arraycopy(a[i], 0, result, index, lengthOne);
            index = index + lengthOne;
        }
        return result;
    }


}
