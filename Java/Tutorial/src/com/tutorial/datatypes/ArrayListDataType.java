package com.tutorial.datatypes;

import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListDataType {
    public static void main(String[] args) {
        System.out.println("=== ArrayList ===");

        basicArrayList();
        advancedArrayList();
    }

    private static void basicArrayList() {
        System.out.println("=== Basic ArrayList ===");

        // Integer is Wrapper for Primitive Int
        ArrayList<Integer> numbers = new ArrayList<>();

        numbers.add(1); // 0
        numbers.add(2); // 1
        numbers.add(3); // 2
        numbers.add(4); // 3
        numbers.add(5); // 4

        System.out.println(numbers.toString());
        System.out.println(numbers);

        System.out.println(numbers.get(2));

        numbers.remove(2);

        System.out.println(numbers);

        numbers.remove(Integer.valueOf(1));

        System.out.println(numbers);

        numbers.clear();

        System.out.println(numbers);

        for (int i = 0; i < 5; i++) {
            numbers.add(i);
        }

        System.out.println(numbers);

        numbers.set(2, Integer.valueOf(30));

        System.out.println(numbers);

        ArrayList<Integer> jumbledNumbers = new ArrayList<>();

        jumbledNumbers.add(5);
        jumbledNumbers.add(3);
        jumbledNumbers.add(1);
        jumbledNumbers.add(4);
        jumbledNumbers.add(2);

        System.out.println(jumbledNumbers);

        jumbledNumbers.sort(Comparator.naturalOrder());

        System.out.println(jumbledNumbers);

        jumbledNumbers.sort(Comparator.reverseOrder());

        System.out.println(jumbledNumbers);

        System.out.println(jumbledNumbers.size());
        System.out.println(jumbledNumbers.contains(Integer.valueOf(1)));
        System.out.println(jumbledNumbers.contains(10));

        jumbledNumbers.clear();

        System.out.println(jumbledNumbers.isEmpty());
    }

    private static void advancedArrayList() {
        System.out.println("\n=== Advanced ArrayList ===");

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        System.out.println("Before: " + numbers);

        numbers.forEach(number -> {
            numbers.set(numbers.indexOf(number), number * 2);

            //System.out.println(number * 2);
        });

        System.out.println("After: " + numbers);
    }
}
