package passwordtocombina;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: czwei
 * @create: 2019-01-17 17:13
 */
public class PasswordCombination3 {

    private char[] base;

    private static FileOutputStream fis = null;

    private static OutputStreamWriter osw = null;

    private PasswordCombination3(char[] base) {
        this.base = base;
    }

    private List<char[]> getCombinations(int length) {
        List<char[]> list = new ArrayList<char[]>();
        list.add(new char[length]);
        return compose(0, list);
    }

    private List<char[]> compose(int index, List<char[]> list) {
        if (index >= list.get(0).length) {
            return list;
        }
        char[] item = null;
        for (int i = 0, size = list.size(); i < size; i++) {
            item = list.get(i);
            for (int j = 0; j < base.length; j++) {
                if (j > 0) {
                    item = copy(item);
                    list.add(item);
                }
                item[index] = base[j];
            }
        }
        return compose(++index, list);
    }

    private char[] copy(char[] item) {
        return Arrays.copyOf(item, item.length);
    }


    //调用方式
    public static void main(String[] args) throws IOException {
//        char[] base = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
//        StringBuffer l = new StringBuffer();
        char[] base = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '`', '~', '!', '@', '#', '$', '%', '^', '&', '*',
                '(', ')', '-', '=', '_', '+', '{', '[', '}', ']',
                ';', ':', '\'', '\"', '\\', '|', ',', '<', '.', '>',
                '/', '?'};//
        PasswordCombination3 combination = new PasswordCombination3(base);
        List<char[]> result = combination.getCombinations(4);
        String rs;
        List<String> list = new ArrayList<>(16);;
        for (char[] cs : result) {
            rs = new String(cs);
//            fis = new FileOutputStream("E:\\pw\\password-1.txt", true);
//            osw = new OutputStreamWriter(fis, "UTF-8");
//            osw.write(rs + "\t\n");
//            osw.flush();
            list.add(rs);
//            System.out.println(list);
        }
        System.out.println(list);
//        osw.close();
//        List<String> l=f(base,3);
//        System.out.println(l);

//        String ss="abc";
//        char[] cc;
//        cc=ss.toCharArray();
//        System.out.println(cc);
//
//        char data[] = {'s', 'g', 'k'};
//        String str = new String(data);
//        System.out.println(str);
    }


    private static List<String> f(char[] base, int r) {

        List<String> l = new ArrayList<String>(16);
        PasswordCombination3 combination = new PasswordCombination3(base);
        List<char[]> result = combination.getCombinations(r);
        String rs;
        for (char[] cs : result) {
            rs = new String(cs);
            l.add(rs);
        }
        return l;
    }
}
