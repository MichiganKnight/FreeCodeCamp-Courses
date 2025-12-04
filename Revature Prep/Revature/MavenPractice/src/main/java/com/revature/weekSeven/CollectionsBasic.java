package com.revature.weekSeven;

import java.util.*;

public class CollectionsBasic {
    public static void main(String[] args) {
        System.out.println("=== Basic Java Collections ===");

        ArrayList<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("David");

        System.out.println("Names:");
        for (String name : names) {
            System.out.println(name);
        }

        HashMap<String, Integer> ages = new HashMap<>();
        ages.put("Alice", 18);
        ages.put("Bob", 20);
        ages.put("Charlie", 30);
        ages.put("David", 40);

        System.out.println();
        System.out.println("Ages:");
        for (Map.Entry<String, Integer> entry : ages.entrySet()) {
            String name = entry.getKey();
            int age = entry.getValue();
            System.out.println(name + ": " + age);
        }

        SetExample.init();
        HashSetExample.exampleOne();
        HashSetExample.exampleTwo();
        TreeSetExample.exampleOne();
        TreeSetExample.exampleTwo();
        QueueExample.init();
        DequeExample.init();
        MapExample.init();
        HashMapExample.init();
        HashMapExample.addElementsToHashtableExampleOne();
        HashMapExample.addElementsToHashtableExampleTwo();
        HashMapExample.updatesOnHashtable();
        HashMapExample.removeMappingsFromHashtable();
    }
}

class SetExample {
    public static void init() {
        System.out.println();
        System.out.println("=== Set Example ===");

        Set<Integer> integers = new HashSet<>();
        integers.addAll(Arrays.asList(1, 2, 3, 4, 8, 9, 0));

        Set<Integer> integers2 = new HashSet<>();
        integers2.addAll(Arrays.asList(1, 3, 7, 5, 4, 0, 7, 5));

        Set<Integer> union = new HashSet<>(integers);
        union.addAll(integers2);
        System.out.println("Union of the Two Sets:");
        System.out.println(union);

        Set<Integer> intersection = new HashSet<>(integers);
        intersection.retainAll(integers2);
        System.out.println("Intersection of the Two Sets:");
        System.out.println(intersection);

        Set<Integer> difference = new HashSet<>(integers);
        difference.removeAll(integers2);
        System.out.println("Difference of the Two Sets:");
        System.out.println(difference);
    }
}

class HashSetExample {
    public static void exampleOne() {
        System.out.println();
        System.out.println("=== HashSet Example 1 ===");

        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("This");
        hashSet.add("is");
        hashSet.add("a");
        hashSet.add("Test");

        hashSet.remove("This");

        System.out.println("HashSet Contains:");
        for (String str : hashSet) {
            System.out.println(str);
        }
    }

    public static void exampleTwo() {
        System.out.println();
        System.out.println("=== HashSet Example 2 ===");

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Computer");
        arrayList.add("Science");

        HashSet<String> hashSet = new HashSet<>(arrayList);
        hashSet.add("Portal");
        hashSet.add("Rev");

        Iterator<String> iterator = hashSet.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}

class TreeSetExample {
    public static void exampleOne() {
        System.out.println();
        System.out.println("=== Tree Set Example ===");

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("This");
        treeSet.add("is");
        treeSet.add("a");
        treeSet.add("Test");

        treeSet.add("This");

        System.out.println("TreeSet Contains:");
        for (String str : treeSet) {
            System.out.println(str);
        }
    }

    public static void exampleTwo() {
        System.out.println();
        System.out.println("=== TreeSet Example 2 ===");

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("Computer");
        treeSet.add("Science");

        TreeSet<String> treeSet2 = new TreeSet<>();
        treeSet2.add("Portal");
        treeSet2.add("Rev");

        Iterator<String> iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}

class QueueExample {
    public static void init() {
        System.out.println();
        System.out.println("=== Queue Example ===");

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            queue.add(i);
        }

        System.out.println("Queue Contains: " + queue);

        // Removes Head Element
        int removedElement = queue.remove();
        System.out.println("Removed: " + removedElement);

        System.out.println("Queue Contains: " + queue);

        int head = queue.peek();
        System.out.println("Head: " + head);

        int size = queue.size();
        System.out.println("Size of Queue: " + size);
    }
}

class DequeExample {
    public static void init() {
        System.out.println();
        System.out.println("=== Deque Example ===");

        Deque<String> deque = new LinkedList<>();
        deque.add("Element 1 (Tail)");
        deque.addFirst("Element 2 (Head)");
        deque.addLast("Element 3 (Tail)");
        deque.push("Element 4 (Head)");
        deque.offer("Element 5 (Tail)");
        deque.offerFirst("Element 6 (Head)");

        System.out.println("Deque Contains: " + deque + "\n");

        deque.removeFirst();
        deque.removeLast();

        System.out.println("Deque Contains: " + deque);
    }
}

class MapExample {
    public static void init() {
        System.out.println();
        System.out.println("=== Map Example ===");

        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("A", 1);
        hashMap.put("B", 2);
        hashMap.put("C", 3);
        hashMap.put("D", 4);

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            System.out.println(entry.getValue());
        }
    }
}

class HashMapExample {
    public static void init() {
        System.out.println();
        System.out.println("=== HashMap Example ===");

        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Alice", 1);
        hashMap.put("Bob", 2);
        hashMap.put("Charlie", 3);

        System.out.println("Map Size: " + hashMap.size());
        System.out.println("Map Contains: " + hashMap);

        if (hashMap.containsKey("Alice")) {
            Integer integer = hashMap.get("Alice");

            System.out.println("Value for Key \"Alice\" is " + integer);
        }
    }

    public static void addElementsToHashtableExampleOne() {
        System.out.println();
        System.out.println("=== Hashtable Example ===");

        Hashtable<Integer, String> hashtable1 = new Hashtable<>();
        Hashtable<Integer, String> hashtable2 = new Hashtable<>();

        hashtable1.put(1, "One");
        hashtable1.put(2, "Two");
        hashtable1.put(3, "Three");

        hashtable2.put(4, "Four");
        hashtable2.put(5, "Five");
        hashtable2.put(6, "Six");

        System.out.println("Mappings of Hashtable 1: " + hashtable1);
        System.out.println("Mappings of Hashtable 2: " + hashtable2);
    }

    public static void addElementsToHashtableExampleTwo() {
        System.out.println();
        System.out.println("=== Hashtable Example 2 ===");

        Hashtable<Integer, String> hashtable1 = new Hashtable<>();
        Hashtable<Integer, String> hashtable2 = new Hashtable<>();

        hashtable1.put(1, "Alpha");
        hashtable1.put(2, "Beta");
        hashtable1.put(3, "Gamma");

        hashtable2.put(1, "Alpha");
        hashtable2.put(2, "Beta");
        hashtable2.put(3, "Gamma");

        System.out.println("Mappings of Hashtable 1: " + hashtable1);
        System.out.println("Mappings of Hashtable 2: " + hashtable2);
    }

    public static void updatesOnHashtable() {
        System.out.println();
        System.out.println("=== Hashtable Example Update ===");

        Hashtable<Integer, String> hashtable = new Hashtable<>();

        hashtable.put(1, "Delta");
        hashtable.put(2, "Delta");
        hashtable.put(3, "Delta");

        System.out.println("Initial Hashtable: " + hashtable);

        hashtable.put(2, "Epsilon");

        System.out.println("Updated Hashtable: " + hashtable);
    }

    public static void removeMappingsFromHashtable() {
        System.out.println();
        System.out.println("=== Hashtable Example Remove ===");

        Map<Integer, String> hashtable = new Hashtable<>();

        hashtable.put(1, "Mu");
        hashtable.put(2, "Nu");
        hashtable.put(3, "Pi");
        hashtable.put(4, "Nu");

        System.out.println("Initial Map: " + hashtable);

        hashtable.remove(4);

        System.out.println("Updated Map: " + hashtable);
    }
}