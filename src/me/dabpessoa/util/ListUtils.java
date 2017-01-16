package me.dabpessoa.util;

import java.util.*;

/**
 * Created by diego.pessoa on 16/01/2017.
 */
public class ListUtils {

    public static List<?> bigger(int place, List<?>... lists){
        List<List<?>> biggestLists = Arrays.asList(lists);
        Collections.sort(biggestLists, new Comparator<List<?>>() {
            @Override
            public int compare(List<?> o1, List<?> o2) {
                return new Integer(o1.size()).compareTo(new Integer(o2.size()));
            }
        });
        return biggestLists.get(place);
    }

}
