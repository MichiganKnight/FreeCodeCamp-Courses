package com.revature.challenges;

public class SubArray {
    public static void main(String[] args) {
        System.out.println("=== Sub Array ===");

        sub1();
        sub2();
    }

    public static void sub1() {
        System.out.println();
        System.out.println("=== Sub Array Example 1 ===");

        int[] nums = {0, 1, 2, 3, 4, 5};
        int[] subArray = new SubArray().sub(nums, 0, 4);

        for (int i : subArray) {
            System.out.println(i);
        }
    }

    public static void sub2() {
        System.out.println();
        System.out.println("=== Sub Array Example 2 ===");

        int[] nums = {0, 1, 2, 3, 4, 5};
        int[] subArray = new SubArray().sub(nums, 2, 6);

        for (int i : subArray) {
            System.out.println(i);
        }
    }

    public int[] sub(int[] nums, int start, int end) {
        int[] subArray = new int[end - start];

        for (int i = start; i < end; i++) {
            subArray[i - start] = nums[i];
        }

        return subArray;
    }
}
