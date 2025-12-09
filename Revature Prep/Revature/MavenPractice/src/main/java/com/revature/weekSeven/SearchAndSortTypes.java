package com.revature.weekSeven;

import java.util.Arrays;

public class SearchAndSortTypes {
    public static void main(String[] args) {
        System.out.println("=== Search & Sort Types ===");

        int[] arr = {2, 3, 4, 10, 40};
        int x = 10;

        int result = linearSearch(arr, x);
        if (result == -1) {
            System.out.println("Element Not Found at Position: " + result);
        } else {
            System.out.println("Element Found at Position: " + result);
        }

        int[] arr2 = {1, 2, 3, 4, 5};
        int x2 = 3;

        result = binarySearch(arr2, x2);
        if (result == -1) {
            System.out.println("Element Not Found at Position: " + result);
        } else {
            System.out.println("Element Found at Position: " + result);
        }

        int[] unsortedArray = {4, 65, 2, -31, 0, 99, 2, 83, 782, 1};
        int[] bubbleSortedArray = bubbleSort(unsortedArray);
        System.out.println("Sorted Array: " + Arrays.toString(bubbleSortedArray));


        System.out.println();
        System.out.println("=== Merge Sort ===");
        int[] unsortedArray2 = {4, 65, 2, -31, 0, 99, 2, 83, 782, 1};
        System.out.println("Unsorted Array: " + Arrays.toString(unsortedArray2));
        int[] mergeSortedArray = mergeSort(unsortedArray2, 0, unsortedArray2.length - 1);
        System.out.println("Sorted Array: " + Arrays.toString(mergeSortedArray));
    }

    private static int linearSearch(int[] arr, int x) {
        System.out.println();
        System.out.println("=== Linear Search ===");

        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] == x) {
                return i;
            }
        }

        return -1;
    }

    private static int binarySearch(int[] arr, int x) {
        System.out.println();
        System.out.println("=== Binary Search ===");

        int lowestPossibleLocation = 0;
        int highestPossibleLocation = arr.length - 1;

        while (highestPossibleLocation >= lowestPossibleLocation) {
            int middle = (lowestPossibleLocation + highestPossibleLocation) / 2;
            if (arr[middle] == x) {
                return middle;
            } else if (arr[middle] > x) {
                highestPossibleLocation = middle - 1;
            } else {
                lowestPossibleLocation = middle + 1;
            }
        }

        return -1;
    }

    private static int[] bubbleSort(int[] arr) {
        System.out.println();
        System.out.println("=== Bubble Sort ===");
        System.out.println("Unsorted Array: " + Arrays.toString(arr));

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        return arr;
    }

    private static int[] mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);

            return merge(arr, left, middle, right);
        }

        return arr;
    }

    private static int[] merge(int[] arr, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }

        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[middle + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }

        return arr;
    }
}
