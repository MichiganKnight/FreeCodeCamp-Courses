package com.solutions.strings;

public class LongestWord {
    public static void main(String[] args) {
        System.out.println("=== Longest Word ===");

        System.out.println(LongestWordSolution("fun&!! time"));
        System.out.println(LongestWordSolution("I love dogs"));
    }

    private static String LongestWordSolution(String sentence) {
        String[] words = sentence.replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");

        String longestWord = "";

        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }

        return longestWord;
    }
}
