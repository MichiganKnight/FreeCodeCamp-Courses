package com.revature.challenges;

public class RemoveAllCharacters {
    public static void main(String[] args) {
        System.out.println("=== Remove All Characters ===");
        String str = "Hello World";
        String ch = "l";

        System.out.println(removeAll(str, ch));
    }

    public static String removeAll(String str, String ch) {
        return str.replaceAll(ch, "");
    }
}
