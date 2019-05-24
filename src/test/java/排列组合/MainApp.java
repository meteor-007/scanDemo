package 排列组合;

import java.util.Stack;

/**
 * @Description:
 * @Auther: czwei
 * @Date: 2019/4/25 11:22
 */
public class MainApp {

    static char[] chars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '~', '!', '@', '#', '$', '%', '^', '&',
            '*', '(', ')', '_', '+', '{', '}', '|', ':', '"',
            '<', '>', '?', ';', '\'', ',', '.', '/', '-', '=',
            '`'};

    public static void main(String[] args) {
        int[] num=new int[]{1,2,3,4,5};
        long start = System.currentTimeMillis();
        String str="";
        //求3个数的组合个数
        count(0,str,chars,4);
        count(0,str,chars,3);
        count(0,str,chars,2);
        count(0,str,chars,1);
//        求1-n个数的组合个数
//        count1(0,str,chars);
        long time = (System.currentTimeMillis() - start) / 1000;
        System.out.println(time);
    }

    private static void count1(int i, String str, char[] num) {
        if (i == num.length) {
            System.out.println(str);
            return;
        }
        count1(i + 1, str, num);
        count1(i + 1, str + num[i] + "", num);
    }

    private static void count(int i, String str, char[] num, int n) {
        if (n == 0) {
            System.out.println(str);
            return;
        }
        if (i == num.length) {
            return;
        }
        count(i + 1, str + num[i] + "", num, n );
        count(i + 1, str, num, n);
    }

}
