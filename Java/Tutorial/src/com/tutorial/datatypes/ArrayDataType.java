package com.tutorial.datatypes;

import java.util.Arrays;

public class ArrayDataType {
    public static void main(String[] args) {
        System.out.println("=== Arrays ===");

        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        int[] numbers = {1, 2, 3, 4, 5};

        int startIndex = 1;
        int endIndex = 4;

        int[] copyOfNumbers = Arrays.copyOf(numbers, 10);
        int[] copyOfNumbers2 = Arrays.copyOfRange(numbers, startIndex, endIndex);

        Arrays.fill(numbers, 0);

        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(copyOfNumbers));
        System.out.println(Arrays.toString(copyOfNumbers2));

        basicArrayManipulation();
        fillArray(vowels);
    }

    private static void basicArrayManipulation() {
        System.out.println("\n=== Basic Array Manipulation ===");

        char[] vowels = new char[5];

        vowels[0] = 'a';
        vowels[1] = 'e';
        vowels[2] = 'i';
        vowels[3] = 'o';
        vowels[4] = 'u';

        System.out.println(Arrays.toString(vowels));

        char[] vowels2 = {'a', 'e', 'i', 'o', 'u'};

        vowels2[2] = 'x';

        System.out.println(Arrays.toString(vowels2));
        System.out.println(vowels2.length);

        char[] vowels3 = {'e', 'u', 'i', 'o', 'a'};

        Arrays.sort(vowels3);

        System.out.println(Arrays.toString(vowels3));
        System.out.println(vowels3.length);

        char[] vowels4 = {'e', 'u', 'a', 'o', 'i'};

        int startingIndex = 1;
        int endingIndex = 4;

        //Arrays.sort(vowels4);
        Arrays.sort(vowels4, startingIndex, endingIndex);

        System.out.println(Arrays.toString(vowels4));

        char key = 'o';

        int foundItemIndex = Arrays.binarySearch(vowels4, key);

        System.out.println(Arrays.toString(vowels4));
        System.out.println(foundItemIndex);

        int foundIndex = Arrays.binarySearch(vowels4, startingIndex, endingIndex, key);
        System.out.println(foundIndex);
    }

    private static void fillArray(char[] vowels) {
        System.out.println("\n=== Fill Array ===");

        int startingIndex = 1;
        int endingIndex = 4;

        Arrays.fill(vowels, startingIndex, endingIndex, 'x');

        System.out.println(Arrays.toString(vowels));
    }
}
