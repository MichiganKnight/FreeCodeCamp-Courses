package com.revature.weekTwo.polymorphism;

public class Polymorphism {
    public static void main(String[] args) {
        System.out.println("=== Static Polymorphism ===");

        staticPolymorphism();
        dynamicPolymorphism();
    }

    public static void staticPolymorphism() {
        DisplayOverloading obj = new DisplayOverloading();
        obj.disp('a');
        obj.disp('a', 10);
    }

    public static void dynamicPolymorphism() {
        Animal obj = new Dog();
        obj.animalSound();
    }
}

class Dog extends Animal {
    public void animalSound() {
        System.out.println("Woof");
    }
}