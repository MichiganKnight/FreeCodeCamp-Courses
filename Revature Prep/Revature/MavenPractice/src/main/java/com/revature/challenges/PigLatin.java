package com.revature.challenges;

public class PigLatin {
    public static void main(String[] args) {
        System.out.println("=== Pig Latin Challenge ===");

        System.out.println(returnPigLatin("word"));
    }

    public static String returnPigLatin(String in) {
        String subString = in.substring(1);

        return subString + in.charAt(0) + "ay";
    }
}
