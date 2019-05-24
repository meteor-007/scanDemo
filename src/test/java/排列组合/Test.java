package 排列组合;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Auther: czwei
 * @Date: 2019/4/25 12:02
 */
public class Test {

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

    //生成排列的长度
    private int listLength;
    //源数据
    private List<Integer> sourceList;
    //生成的所有排列存放的容器
    private List<List<Integer>> targetList;

    public Test(int listLength, List<Integer> sourceList) {
        this.listLength = listLength;
        this.sourceList = sourceList;
        this.targetList = new LinkedList<List<Integer>>();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        //1.
//        char[] chars = "1234567890abcdefghijklmnopqrstuvwxyz".toCharArray();
        long start = System.currentTimeMillis();
        //StringBuffer存储和操作字符串 是字符串变量，它的对象是可以扩充和修改的3个字符排列一共36^3种
        StringBuffer buffer = new StringBuffer(91 * 91 * 91 * 91);
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                for (int k = 0; k < chars.length; k++) {
                    for (int a = 0; a < chars.length; a++) {
                        if (i != j && i != k && j != k && i!=a && j!=a && k!=a) {
                            buffer.append(chars[i]).append(chars[j]).append(chars[k]).append(chars[a]).append("\n");
                        }
                    }
                }
            }
        }
        long time = (System.currentTimeMillis() - start) / 1000;
        System.out.println(buffer);
        System.out.println(time);


        //2.
/*        List<Integer> sourceList = new ArrayList<Integer>();
        for (int i=1;i<chs.length;i++){
            sourceList.add(i);
        }
        Test tl = new Test(4,sourceList);
        List<List<Integer>> targetList = tl.productList();
        System.out.println(targetList.size());
        for(int i=0;i<targetList.size();i++)
        {
            System.out.println(Arrays.toString(targetList.get(i).toArray()));
        }*/
    }

    public List<List<Integer>> productList() {
        if (listLength > 0) {
            for (int i = 0; i < sourceList.size(); i++) {
                List<Integer> childList = new LinkedList<Integer>();
                addEle(childList, i);
            }
        }
        return this.targetList;
    }

    private void addEle(List<Integer> currentList, int index) {
        currentList.add(sourceList.get(index));
        if (currentList.size() < listLength) {
            for (int i = 0; i < sourceList.size(); i++) {
                List<Integer> childList = new LinkedList<Integer>();
                childList.addAll(currentList);
                addEle(childList, i);
            }
        } else if (currentList.size() == listLength) {
            targetList.add(currentList);
        }
    }

}
