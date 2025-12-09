package com.revature.weekSeven;

import java.util.Arrays;

public class StacksVsVectors {
    public static void main(String[] args) {
        System.out.println("=== Stacks vs Vectors ===");

        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);

        int value = stack.pop();

        System.out.println(value);
        System.out.println(value);
    }
}

class Stack {
    private int lastItem = 0;
    private int[] items = new  int[10];

    public void push(int newItem) {
        int index = items.length;
        int[] newArray = new int[items.length + 1];

        System.arraycopy(items, 0, newArray, 0, items.length);

        newArray[index] = newItem;

        items = newArray;
        lastItem = newItem;

        System.out.println("Updated Stack: " + Arrays.toString(items));
    }

    public int pop() {
        int tempItem = lastItem;
        int[] newArray = new int[items.length - 1];

        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = items[i];
        }

        items = newArray;
        lastItem = items[items.length - 1];

        return tempItem;
    }

    public int peek() {
        return 0;
    }
}
