package com.tutorial.datatypes;

import java.util.HashMap;

public class HashMapDataType {
    public static void main(String[] args) {
        System.out.println("=== HashMap ===");

        basicHashMap();
        advancedHashMap();
    }

    private static void basicHashMap() {
        System.out.println("=== Basic HashMap ===");

        HashMap<String, Integer> examScores = new HashMap<String, Integer>();

        examScores.put("Math", 75);
        examScores.put("Science", 90);
        examScores.put("English", 95);
        examScores.put("Computer Science", 100);
        examScores.put("History", 98);

        System.out.println(examScores);

        System.out.println(examScores.get("English"));

        examScores.putIfAbsent("Orchestra", 100);

        System.out.println(examScores);

        examScores.replace("Math", 70);

        System.out.println(examScores);

        System.out.println(examScores.getOrDefault("Music", -1));

        System.out.println(examScores);

        System.out.println(examScores.size());

        examScores.remove("Science");

        System.out.println(examScores);

        System.out.println(examScores.containsKey("History"));
        System.out.println(examScores.containsValue(100));

        System.out.println(examScores.size());

        examScores.clear();

        System.out.println(examScores);
        System.out.println(examScores.isEmpty());
    }

    private static void advancedHashMap() {
        System.out.println("\n=== Advanced HashMap ===");

        HashMap<String, Integer> examScores = new HashMap<String, Integer>();

        examScores.put("Math", 75);
        examScores.put("Science", 90);
        examScores.put("English", 95);
        examScores.put("Computer Science", 100);
        examScores.put("History", 98);

        examScores.forEach((subject, score) -> {
            System.out.printf("%s: %d%n", subject, score);
        });

        examScores.forEach((subject, score) -> {
            examScores.replace(subject, score - 10);
        });

        System.out.println(examScores);
    }
}
