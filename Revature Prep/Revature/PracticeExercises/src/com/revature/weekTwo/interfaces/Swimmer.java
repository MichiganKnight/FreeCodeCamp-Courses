package com.revature.weekTwo.interfaces;

public interface Swimmer {
    void swim();
    default void dive() {
        System.out.println("Diving");
    }
}
