package com.solutions.strings;

public class FirstReverse {
    public static void main(String[] args) {
        System.out.println("=== Do While Loops ===");

        System.out.println(FirstReverseSolution1("coderbyte"));
        System.out.println(FirstReverseSolution2("I love code"));
    }

    private static String FirstReverseSolution1(String str) {
         char[] charArray = str.toCharArray();

         int left = 0;
         int right = charArray.length - 1;

         while(left < right) {
             char temp = charArray[left];
             charArray[left] = charArray[right];
             charArray[right] = temp;

             left++;
             right--;
         }

         return new String(charArray);
    }

    private static String FirstReverseSolution2(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}
