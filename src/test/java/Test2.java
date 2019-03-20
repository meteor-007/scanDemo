import java.util.*;

/**
 * @description:
 * @author: czwei
 * @create: 2019-01-17 11:45
 */
public class Test2 {

    public static HashMap<String, String> dealHashMap(HashMap<String, String> rs) {
        HashMap<String, String> resultMap = new HashMap<>();
        if (rs == null || rs.isEmpty()) {
            return resultMap;
        }

        Iterator<Map.Entry<String, String>> it = rs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String message = entry.getValue();
            for (int index = 0; index < message.length(); index++) {
                String key = entry.getKey() + message.substring(index, index + 1);
                String value = message.substring(0, index) + message.substring(index + 1, message.length());
                resultMap.put(key, value);
            }
        }
        rs.clear();
        return resultMap;

    }

    public static void main(String[] args) {
//        String message = "123456789";
        String message = "VSlib20190304_001.zip";
        System.out.println(message.length());
//        HashMap<String, String> rs = new HashMap<>();
//        //输出数的排列组合
//        for (int index = 0; index < message.length(); index++) {
//            message.substring(index, index + 1);
//            String mes = message.substring(0, index) + message.substring(index + 1, message.length());
//            rs.put(message.substring(index, index + 1), mes);
//        }
//        int i = 0;
//        while (i < message.length() - 1) {
//            rs = dealHashMap(rs);
//            i++;
//        }
//        for (Map.Entry<String, String> entry : rs.entrySet()) {
//            System.out.println(entry.getKey());
//        }
    }
}
