package com.revature.weekTwo.interfaces;

class Mammal {

}

class Whale extends Mammal implements Swimmer {
    @Override
    public void swim() {
        System.out.println("Swimming");
    }
}
