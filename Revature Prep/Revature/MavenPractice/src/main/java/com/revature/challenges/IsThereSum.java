package com.revature.challenges;

public class IsThereSum {
    public static void main(String[] args) {
        System.out.println("=== Is There Sum ===");

        int[] arr = {2, 2, 3, 4, 5};
        int target = 4;

        System.out.println(check(arr, target));
    }

    /**
     * Checks if there is a pair of numbers in the array that sum to the target
     * @param arr Array of Integers
     * @param target Target Sum
     * @return True if there is a pair, False if not
     */
    public static boolean check(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return true;
                }
            }
        }

        return false;
    }
}
