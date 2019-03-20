import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.commons.collections.map.CaseInsensitiveMap;

/**
 * Todo: 测试读取txt文件
 *
 * @Author:$czwei
 * @Date: 2018/12/21 11:25
 */
public class TxtCeshi {

    private static Map<Integer, String> queryNum2Name(String filePath) {
        Map<Integer, String> map = new HashMap<>();
        try (FileReader reader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String string = bufferedReader.readLine();
            while (string != null) {
                int index = string.indexOf(":");
                map.put(Integer.valueOf(string.substring(0, index)), string.substring(index + 1));
                string = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
//		Map<Integer, String> map = queryNum2Name("D:\\数据库漏洞扫描\\数据库漏洞扫描\\弱口令字典\\dictionary.txt");
//		Map<Integer, String> map1 = queryNum2Name("E:\\milan.txt");



//        Scanner in = new Scanner(System.in);
        List<String> list= new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("good");
        list.add("shit");

        for(Iterator<String> iterator=list.iterator();iterator.hasNext();){
            if(iterator.next().equals("hello")){
                iterator.remove();
            }
        }
        System.out.println(list);
    }

}
