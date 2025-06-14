package com.tutorial.datatypes;

public class DataTypes {
    public static void main(String[] args) {
        System.out.println("=== Data Types ===");

        // Integer Types
        byte aSingleByte = 100; // -128 to 127
        short aSmallNumber = 20000; // -32,768 to 32,767
        int anInteger = 214783647; // -2147483648 to 2147843647
        long aLargeNumber = 9223372036854775807L; // -9223372036854775808 to 9223372036854775807
        
        // Decimal Types
        double aDouble = 1.79769313; // 4.9E-324 to 1.7976931348623157E308
        float aFloat = 3.4028F; // 1.4E-45 to 3.4028235E38
        
        // Booleans
        boolean isWeekend = true;
        boolean isWorkday = false;
        
        // Characters
        char copyrightSymbol = '©';

        System.out.print("Copyright Symbol: " + copyrightSymbol);

        System.out.println("\n=== Data Type Assignment ===");

        int number1 = 5;
        double number2 = number1;

        System.out.println("Number 2: " + number2);

        double number3 = 5.5;
        int number4 = (int) number3;

        System.out.println("Number 4: " + number4);
    }
}
