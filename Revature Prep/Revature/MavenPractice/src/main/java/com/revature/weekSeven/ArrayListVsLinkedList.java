package com.revature.weekSeven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ArrayListVsLinkedList {
    public static void main(String[] args) {
        System.out.println("=== ArrayList vs LinkedList ===");
        
        ArrayList<Integer> arrayList = createArrayList(1,  2, 3, 4, 5);
        LinkedList<Integer> linkedList = createLinkedList(1, 2, 3, 4, 5);

        System.out.println(arrayList);
        System.out.println(linkedList);

        System.out.println(arrayList.size());

        addToArrayList(arrayList, 6, 7, 8, 9);
        System.out.println(arrayList.size());

        manipulateList(arrayList, linkedList);
    }

    /**
     * Creates an ArrayList of Integers
     * @param numbers Numbers to be Added
     * @return The Created ArrayList
     */
    private static ArrayList<Integer> createArrayList(Integer... numbers) {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, numbers);
        
        return list;
    }

    /**
     * Creates a LinkedList of Integers
     * @param numbers Numbers to be added
     * @return The Created LinkedList
     */
    private static LinkedList<Integer> createLinkedList(Integer... numbers) {
        LinkedList<Integer> list = new LinkedList<>();
        Collections.addAll(list, numbers);
        
        return list;
    }

    private static void addToArrayList(ArrayList<Integer> list, Integer... numbers) {
        Collections.addAll(list, numbers);
    }

    private static void manipulateList(ArrayList<Integer> arrayList, LinkedList<Integer> linkedList) {
        System.out.println();
        System.out.println("=== Manipulating ArrayList & LinkedList ===");

        System.out.println(arrayList.get(3));
        System.out.println(linkedList.indexOf(1));

        System.out.println();
        System.out.println("=== Deque Operations ===");

        linkedList.addFirst(0);
        linkedList.addLast(6);
        System.out.println(linkedList);

        System.out.println();
        System.out.println("=== Queue Operations ===");

        linkedList.offer(7);
        linkedList.poll();
        System.out.println(linkedList);

        System.out.println();
        System.out.println("=== Stack Operations ===");

        LinkedList<Integer> stack = createLinkedList();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack);

        stack.push(6);
        System.out.println(stack.peek());
        System.out.println(stack);

        stack.pop();
        System.out.println(stack);
    }
}
