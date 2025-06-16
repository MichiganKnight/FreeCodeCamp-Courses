package com.tutorial.strings;

public class Strings {
    public static void main(String[] args) {
        System.out.println("=== Strings ===");

        basicStrings();
        complexStrings();
        stringMethods();
        stringComparison();
        stringReplacement();
    }

    private static void basicStrings() {
        System.out.println("=== Basic Strings ===");

        String name = "Drew";
        String newName = new String("Drew");

        System.out.println("Name: " + name);
        System.out.println("Name: " + newName);

        String literalString1 = "abc";
        String literalString2 = "abc";

        String objectString1 = new String("xyz");
        String objectString2 = new String("xyz");

        System.out.println(literalString1 == literalString2);
        System.out.println(objectString1 == objectString2);
    }

    private static void complexStrings() {
        System.out.println("\n=== Complex Strings ===");

        String name = "Drew"; // %s
        String country = "United States"; // %s

        int age = 26; // %d

        double gpa = 4.0; // %f

        char percentSign = '%'; // %c

        boolean tellingTheTruth = false; // %b

        String formattedString = String.format("Hello World! My name is %s, I am %d years old and I live in %s. My GPA is %f. I have attended 100%c of my university classes. These are all %b claims...", name, age, country, gpa, percentSign, tellingTheTruth);

        System.out.println("Hello World! My name is " + name + ", I am " + age + " years old and I live in " + country + ".");
        System.out.println(formattedString);
    }

    private static void stringMethods() {
        System.out.println("\n=== String Methods ===");

        String name = "Drew";
        String emptyString = "";

        System.out.println(name.length());
        System.out.println(emptyString.isEmpty());
        System.out.println(name.toUpperCase());
        System.out.println(name.toLowerCase());
    }

    private static void stringComparison() {
        System.out.println("\n=== String Comparison ===");

        String string1 = new String("abc");
        String string2 = new String("ABC");

        System.out.println(string1.equals(string2));
        System.out.println(string1.equalsIgnoreCase(string2));
    }

    private static void stringReplacement() {
        System.out.println("\n=== String Replacement ===");

        String string = "The sky is blue";
        String updatedString = string.replace("blue", "green");

        System.out.println(string);
        System.out.println(string.replace("blue", "red"));
        System.out.println(updatedString);

        System.out.println(string.contains("sky"));
    }
}
