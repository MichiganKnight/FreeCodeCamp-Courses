public class A {
    void msg() {
        System.out.println("Hello");
    }
}

public class B {
    void msg() {
        System.out.println("Welcome");
    }
}

public class C extends A, B {
    public static void main(String[] args) {
        C obj = new C();

        c.msg(); // Ambiguity
    }
}