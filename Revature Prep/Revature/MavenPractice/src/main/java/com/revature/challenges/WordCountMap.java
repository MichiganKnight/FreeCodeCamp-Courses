package com.revature.challenges;

import java.util.HashMap;
import java.util.Map;

public class WordCountMap {
    public static void main(String[] args) {
        System.out.println("=== Word Count Map Challenge ===");

        String input1 = "apple pear melon";

        Map<String, Integer> wordMap1 = returnWordCount(input1);
        System.out.println(wordMap1.get("apple"));
        System.out.println(wordMap1.get("pear"));
        System.out.println(wordMap1.get("melon"));

        String input2 = "giraffe zebra giraffe";

        Map<String, Integer> wordMap2 = returnWordCount(input2);
        System.out.println(wordMap2.get("giraffe"));
        System.out.println(wordMap2.get("zebra"));

        String input3 = "apple pear melon apple pear apple";

        Map<String, Integer> wordMap3 = returnWordCount(input3);
        System.out.println(wordMap3.get("apple"));
        System.out.println(wordMap3.get("pear"));
        System.out.println(wordMap3.get("melon"));
    }

    public static Map<String, Integer> returnWordCount(String words) {
        Map<String, Integer> wordMap = new HashMap<>();

        String[] wordArray = words.split(" ");
        for (String word : wordArray) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }

        return wordMap;
    }
}
