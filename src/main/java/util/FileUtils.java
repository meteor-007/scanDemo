package util;

import java.io.*;
import java.util.ArrayList;

/**
 * @description: 文件流处理类工具
 * @author: czwei
 * @create: 2019-01-16 11:57
 */
public class FileUtils {

    /**
     * @Description: 方法三：一行一行读取文件
     * @Param:
     * @return:
     * @Author: czwei
     * @date: 2019/1/16
     */
    private static void readFile() {
        File file = new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\弱口令字典\\dictionary.txt");
//        File file=new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\弱口令字典\\mysql.txt");
        BufferedReader reader = null;
        String temp = null;
        int line = 1;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((temp = reader.readLine()) != null) {
                System.out.println("line" + line + ":" + temp);
                line++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 方法一：读取TXT文本内容
     *
     * @param name
     */
    public static String[] toArrayByFileReader1(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(name);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        String[] s = new String[length];
        for (int i = 0; i < length; i++) {
            String array = arrayList.get(i);
            s[i] = array;
        }
        // 返回数组
        return s;
    }


    /**
     * @Description: 方法二：解析普通文本文件  流式文件 如txt
     * @Param:
     * @return:
     * @Author: czwei
     * @date: 2019/1/14
     */
    public static String[] readTxt(String path) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        StringBuilder content = new StringBuilder("");
        try {
            String code = resolveCode(path);
            File file = new File(path);
            InputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, code);
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            while (null != (str = br.readLine())) {
                arrayList.add(str);
            }
            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("读取文件:" + path + "失败!");
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        String[] s = new String[length];
        for (int i = 0; i < length; i++) {
            String array = arrayList.get(i);
            s[i] = array;
        }
        // 返回数组
        return s;
    }

    public static String resolveCode(String path) throws Exception {

        InputStream inputStream = new FileInputStream(path);
        byte[] head = new byte[3];
        inputStream.read(head);
        String code = "gb2312";  //或GBK
        if (head[0] == -1 && head[1] == -2)
            code = "UTF-16";
        else if (head[0] == -2 && head[1] == -1)
            code = "Unicode";
        else if (head[0] == -17 && head[1] == -69 && head[2] == -65)
            code = "UTF-8";
        inputStream.close();
        System.out.println(code);
        return code;
    }

    /**
    *@Description: 一行一行数据写入文件
    *@Param: 
    *@return: 
    *@Author: czwei
    *@date: 2019/1/16
    */
    public static void writeFile02(String[] arrs) throws IOException {
//        String[] arrs={
//                "zhangsan,23,福建",
//                "lisi,30,上海",
//                "wangwu,43,北京",
//                "laolin,21,重庆",
//                "ximenqing,67,贵州"
//        };
        //写入中文字符时解决中文乱码问题
        FileOutputStream fos=new FileOutputStream(new File("D:\\数据库漏洞扫描\\数据库漏洞扫描\\5位密码字典\\password.txt"));
        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter  bw=new BufferedWriter(osw);
        //简写如下：
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
        //        new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8"));
        for(String arr:arrs){
            bw.write(arr+"\t\n");
        }
        //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
        bw.close();
        osw.close();
        fos.close();
    }
}
