package com.revature.challenges;

public class ReturnWordCount {
    public static void main(String[] args) {
        System.out.println("=== Return Word Count ===");

        String str = "Hello World";
        System.out.println(count(str));
    }

    public static int count(String in) {
        return in.split(" ").length;
    }
}
