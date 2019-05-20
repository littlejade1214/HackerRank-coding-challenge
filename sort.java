import java.io.*;
import java.math.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Solution {
    static void sortByKey(Map map) {
        Object[] objects = map.keySet().toArray();
        Arrays.sort(objects);
        for (int i = 0; i < objects.length; i++) {
            System.out.println("Key: " + objects[i] + "Value: " + map.get(objects[i]));
        }
    }

    static void sortByValue(Map map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> mapping : list) {
            System.out.println("Key: " + mapping.getKey() + "Value: " + mapping.getValue());
        }
    }

    public static void main(String[] args) {
        Map<Integer, String> tableA = new HashMap<>();
        map1.put(4, "four");
        map1.put(1, "one");
        map1.put(2, "two");

        Map<String, Integer> tableB = new HashMap<>();
        map2.put("four", 4);
        map2.put("one", 1);
        map2.put("two", 2);

        sortByKey(tableA);
        sortByValue(tableB);
    }
}
