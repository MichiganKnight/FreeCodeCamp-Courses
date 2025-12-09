package com.revature.weekSeven;

import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        System.out.println("=== Priority Queue ===");

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(10);
        queue.add(20);
        queue.add(15);

        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());

        PriorityQueue<String> queue2 = new PriorityQueue<>();
        queue2.add("Gators");
        queue2.add("For");
        queue2.add("Gators");

        System.out.println("Initial Priority Queue: " + queue2);

        queue2.remove("Gators");
        System.out.println("After Remove: " + queue2);

        System.out.println("Poll Method: " + queue2.poll());
        System.out.println("Final Priority Queue: " + queue2);

        PriorityQueue<String> queue3 = new PriorityQueue<>();
        queue3.add("Gators");
        queue3.add("For");
        queue3.add("Gators");

        System.out.println("Initial Priority Queue: " + queue3);
        String element = queue3.peek();
        System.out.println("Accessed Element: " + element);

        Iterator<String> iterator = queue3.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
        }
    }
}
