public class Animal {
    void eat() {
        System.out.println("Eating...");
    }
}

public class Dog extends Animal {
    void bark() {
        System.out.println("Barking...");
    }
}

public class BabyDog extends Dog {
    void weep() {
        System.out.println("Weeping...");
    }
}

public class TestInheritance {
    public static void main(String[] args) {
        BabyDog d = new BabyDog();

        d.weep();
        d.bark();
        d.eat();
    }
}