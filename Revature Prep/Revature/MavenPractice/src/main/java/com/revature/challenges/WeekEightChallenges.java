package com.revature.challenges;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class WeekEightChallenges {
    /**
     * Compare Two Arrays of Characters Lexicographically
     * @param a The First Array
     * @param b The Second Array
     * @return The Comparison Result
     */
    public int compare(char[] a, char[] b) {
        int minLength = Math.min(a.length, b.length);

        for (int i = 0; i < minLength; i++) {
            if (a[i] < b[i]) {
                return -1;
            } else if (a[i] > b[i]) {
                return 1;
            }
        }

        return Integer.compare(a.length, b.length);
    }

    /**
     * Create a New Array with the Absolute Value of Each Element
     * @param nums The Array of Numbers
     * @return The Array of Absolute Values
     */
    public int[] getArrayAbs(int[] nums) {
        int[] absNums = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            absNums[i] = (nums[i] < 0) ? -nums[i] : nums[i];
        }

        return absNums;
    }

    /**
     * Reverse a String
     * @param str The String to Reverse
     * @return The Reversed String
     */
    public String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Use Recursion to Calculate the nth Fibonacci Number
     * @param n The Number to Calculate
     * @return The Calculated Number
     */
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        return fib(n - 1) + fib(n - 2);
    }

    /**
     * Find the Largest Sum of a Pair of Numbers in a List
     * @param nums The List of Numbers
     * @return The Largest Sum
     */
    public int bigSum(List<Integer> nums) {
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int num : nums) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest) {
                secondLargest = num;
            }
        }

        return largest + secondLargest;
    }

    /**
     * Check if a String is an Isogram
     * @param str The String to Check
     * @return True if the String is an Isogram, False if not
     */
    public boolean isIsogram(String str) {
        HashSet<Character> characters = new HashSet<>();

        for (char c : str.toCharArray()) {
            if (characters.contains(c)) {
                return false;
            } else {
                characters.add(c);
            }
        }

        return true;
    }

    /**
     * Find the Character That Appears Most Often in a String
     * @param str The String to Check
     * @return The Most Common Character
     */
    public char recurringChar(String str) {
        HashMap<Character, Integer> charCounts = new HashMap<>();

        for (char c : str.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        char mostCommonChar = ' ';
        int maxCount = 0;

        for (Map.Entry<Character, Integer> entry : charCounts.entrySet()) {
            char currentChar = entry.getKey();
            int currentCount = entry.getValue();

            if (currentCount > maxCount) {
                maxCount = currentCount;
                mostCommonChar = currentChar;
            }
        }

        return mostCommonChar;
    }

    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }

        return nums.length;
    }
}
