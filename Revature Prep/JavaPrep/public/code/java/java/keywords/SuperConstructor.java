public class Animal {
    Animal() {
        System.out.println("Animal Constructor");
    }
}

public class Dog extends Animal {
    Dog() {
        super();
        System.out.println("Dog Constructor");
    }
}

public class TestSuper {
    public static void main(String[] args) {
        Dog d = new Dog();
    }
}