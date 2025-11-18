package com.revature.weekTwo.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListExample {
    public static void main(String[] args) {
        System.out.println("=== Lists Example ===");

        List<String> list = new ArrayList<>();
        list.add("Dog");
        list.add("Cat");
        list.add("Bird");

        System.out.println(list);
        list.add("Elephant");
        System.out.println(list);

        System.out.println("=== List Elements ===");

        System.out.println(list);
        System.out.println(list.get(2));
        System.out.println(list.indexOf("Cat"));

        list.remove(1);
        list.set(0, "NewAnimal");
        System.out.println(list);

        List<String> sublist = list.subList(1, 3);
        System.out.println(sublist);

        System.out.println("=== List Iteration ===");

        for (String element : list) {
            System.out.println(element);
        }

        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }

        ListIterator<String> listIterator1 = list.listIterator();
        while (listIterator1.hasPrevious() ) {
            System.out.println(listIterator1.previous());
        }


    }
}
