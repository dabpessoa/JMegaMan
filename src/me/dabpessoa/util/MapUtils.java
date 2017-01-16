package me.dabpessoa.util;

import java.io.Serializable;
import java.util.*;

/**
 * Created by diego.pessoa on 16/01/2017.
 */
public class MapUtils {

    public static <K,T extends Comparable> Map<K, T> sortByValue(Map<K, T> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<K, T>> list = new LinkedList<Map.Entry<K, T>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<K, T>>() {
            public int compare(Map.Entry<K, T> o1,
                               Map.Entry<K, T> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<K, T> sortedMap = new LinkedHashMap<K, T>();
        for (Map.Entry<K, T> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }

}
