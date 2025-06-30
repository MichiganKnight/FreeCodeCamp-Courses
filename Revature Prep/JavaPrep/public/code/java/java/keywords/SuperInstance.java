public class Animal {
    String color = "white";
}

public class Dog extends Animal {
    String color = "black";

    void printcolor() {
        System.out.println(color); // Prints Dog Class Color
        System.out.println(super.color); // Prints Animal Class Color
    }
}

public class TestSuper {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.printcolor();
    }
}