public class Animal {
    void eat() {
        System.out.println("Eating...");
    }
}

public class Dog extends Animal {
    void eat() {
        System.out.println("Dog Eating...");
    }

    void bark() {
        System.out.println("Barking...");
    }

    void work() {
        super.eat();
        bark();
    }
}

public class TestSuper {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.work();
    }
}