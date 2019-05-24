package mapvalue排序;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: czwei
 * @Date: 2019/5/13 11:23
 */
public class MapSort {

    public static void main(String[] argv) {

        List<Map<String, Object>> list = new ArrayList<>(16);
        Map<String, Object> unsortMap = new HashMap<>(16);
        unsortMap.put("z", 10);
        unsortMap.put("b", 20);
        list.add(unsortMap);
        Map<String, Object> unsortMap2 = new HashMap<>(16);
        unsortMap2.put("z", 30);
        unsortMap2.put("b", 40);
        list.add(unsortMap2);
        Map<String, Object> unsortMap4 = new HashMap<>(16);
        unsortMap4.put("z", 60);
        unsortMap4.put("b", 70);
        list.add(unsortMap4);
        Map<String, Object> unsortMap5 = new HashMap<>(16);
        unsortMap5.put("z", 80);
        unsortMap5.put("b", 90);
        list.add(unsortMap5);
        Map<String, Object> unsortMap3 = new HashMap<>(16);
        unsortMap3.put("z", 50);
        unsortMap3.put("b", 60);
        list.add(unsortMap3);
        Map<String, Object> unsortMap6 = new HashMap<>(16);
        unsortMap6.put("z", 100);
        unsortMap6.put("b", 110);
        list.add(unsortMap6);
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer name2 = Integer.valueOf(o1.get("b").toString());
                Integer name1 = Integer.valueOf(o2.get("b").toString());
                return name1.compareTo(name2);
            }
        });
        for (Map<String, Object> map : list.subList(0, 5)) {
            System.out.println(map.get("z") + ":" + map.get("b"));
        }


/*            System.out.println(unsortMap);
            System.out.println("Sorted...");
            //sort by values, and reserve it, 10,9,8,7,6...
            Map<String, Integer> result = unsortMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            System.out.println(result);

            //Alternative way
            Map<String, Integer> result2 = new LinkedHashMap<>();
            unsortMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
            System.out.println(result2);*/

    }

//    public static void main(String[] args) {
//        Map<String, Integer> mapRepeat = new HashMap<>();
//
//        mapRepeat.put("aa", 1);
//        mapRepeat.put("bb", 45);
//        mapRepeat.put("cc", 32);
//        mapRepeat.put("dd", 226);
//        mapRepeat.put("ee", 16);
//        mapRepeat.put("ff", 320);
//        mapRepeat.put("gg", 99);
//
//
////        Map<String, Integer> mapRepeat1 = new HashMap<>();
////        mapRepeat1.put("aa", 1);
////        mapRepeat1.put("bb", 5);
//        //.sorted标签，对map根据value排序
//        //.map方法把map的key转成set
//        //.subList取前n位，下图（0，5位）
//        //.retainAll(拿key-set，去与map碰撞)，判断交集并覆盖之前的list，取出相交个数
//
//        //list的value就是相交的 key，如需value取出即可
//        List<String> mobileList = mapRepeat.entrySet().stream()
//                .sorted((Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> o2.getValue() - o1.getValue())
//                .map(entry -> entry.getKey()).collect(Collectors.toList())
//                .subList(0, 5);
////        mobileList.retainAll(mapRepeat1.keySet());
//
//        System.out.println(mobileList.toString());
//    }

}
