package 排列组合;

/**
 * @auther: czwei
 * @date: 2019/5/24 10:02
 */
public class Test7 {

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
    private static int count = 0;

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        // 指定长度为4
        int length = 2;
        char[] comb = new char[length];
        char[] charArray = mergeArray(shuZi, ziMu, teShuZiFu);
        showComb(charArray, comb, 0, length);
        long t2 = System.currentTimeMillis();
        System.out.println("消耗时间:" + (t2 - t1));
    }

    public static void showComb(char[] charArray, char[] comb, int i, int length) {
        int a = 0;
        for (int j = 0; j < charArray.length; j++) {
            comb[i] = charArray[j];
            if (i < length - 1) {
                // 继续递归
                showComb(charArray, comb, i + 1, length);
            } else {
                String str = charArrayToString(comb);
                System.out.println("==当前值=" + str + "===总数==" + ++count);
            }
        }
    }

    public static String charArrayToString(char[] charArray) {
        StringBuffer sb = new StringBuffer();
        for (char ch : charArray) {
            sb.append(ch);
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
