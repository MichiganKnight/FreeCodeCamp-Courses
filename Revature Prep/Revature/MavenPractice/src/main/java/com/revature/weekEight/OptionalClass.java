package com.revature.weekEight;

import java.util.Optional;

public class OptionalClass {
    public static void main(String[] args) {
        System.out.println("=== Optional Class ===");

        optionalExample1();
        optionalExample2();
    }

    private static void optionalExample1() {
        System.out.println();
        System.out.println("=== Optional Example 1 ===");

        String[] str = new String[5];
        str[2] = "Hello World";

        Optional<String> empty = Optional.empty();
        System.out.println(empty);

        Optional<String> value = Optional.of(str[2]);
        System.out.println(value);
    }

    private static void optionalExample2() {
        System.out.println();
        System.out.println("=== Optional Example 2 ===");

        String[] str = new String[5];
        str[2] = "Hello World";

        Optional<String> value = Optional.of(str[2]);

        System.out.println(value.get());
        System.out.println(value.hashCode());
        System.out.println(value.isPresent());
    }
}
