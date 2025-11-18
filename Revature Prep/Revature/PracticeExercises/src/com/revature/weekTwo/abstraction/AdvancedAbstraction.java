package com.revature.weekTwo.abstraction;

public class AdvancedAbstraction {
    public static void main(String[] args) {
        System.out.println("=== Advanced Abstraction ===");

        Person drew = new Developer();
        drew.setName("Drew");
        System.out.println(drew.getName());
    }
}

/**
 * Developer IS-A Person
 */
class Developer extends Person {
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
