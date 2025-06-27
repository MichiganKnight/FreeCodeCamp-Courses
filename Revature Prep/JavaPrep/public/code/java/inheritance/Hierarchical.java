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

public class Cat extends Animal {
    void meow() {
        System.out.println("Meowing...");
    }
}

public class TestInheritance {
    public static void main(String[] args) {
        Cat c = new Cat();

        c.meow();
        c.eat();
    }
}