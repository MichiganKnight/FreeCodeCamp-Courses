package com.revature.weekSeven;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IterationBasic {
    public static void main(String[] args) {
        System.out.println("=== Basic Iterators ===");

        List<String> list = new ArrayList<>();
        list.add("People");
        list.add("For");
        list.add("People");

        enhanceForLoop(list);
        iterateForEach(list);
        iterateIterator(list);
    }

    private static void enhanceForLoop(List<String> list) {
        System.out.println();
        System.out.println("=== Enhanced For Loop ===");

        for (String element : list) {
            System.out.println(element);
        }
    }

    private static void iterateForEach(List<String> list) {
        System.out.println();
        System.out.println("=== For Each Loop ===");

        list.forEach((element) -> System.out.println(element));
    }

    private static void iterateIterator(List<String> list) {
        System.out.println();
        System.out.println("=== Iterating Using Iterator ===");

        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println(element);
        }
    }
}
