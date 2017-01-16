package me.dabpessoa.util;

import java.util.*;

/**
 * Created by diego.pessoa on 16/01/2017.
 */
public class ListUtils {

    public static List<?> biggerSizeList(int place, List<?>... lists){
        List<List<?>> biggestLists = Arrays.asList(lists);
        Collections.sort(biggestLists, new Comparator<List<?>>() {
            @Override
            public int compare(List<?> o1, List<?> o2) {
                return new Integer(o1.size()).compareTo(new Integer(o2.size()));
            }
        });
        Collections.reverse(biggestLists);
        int index = place-1;
        if (index >= 0 && index < biggestLists.size()) {
            return biggestLists.get(place-1);
        } throw new RuntimeException("Parâmetro 'place' é inválido.");
    }

    public static void main(String[] args) {

        List<Integer> l1 = Arrays.asList(1, 3,5,6, 0, 3);
        List<Integer> l2 = Arrays.asList(1, 3,5,6);
        List<Integer> l3 = Arrays.asList(1, 3,5,4, 0, 3, 8);

        List<Integer> bigger = (List<Integer>) biggerSizeList(1, l1,l2,l3);
        System.out.println("test: "+ bigger);

        System.out.println(bigger.equals(l3));

    }

}
