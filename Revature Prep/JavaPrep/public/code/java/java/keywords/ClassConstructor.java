public class A {
    A() {
        System.out.println("Hello A");
    }

    A(int x) {
        this();

        System.out.println(x);
    }
}

public class TestThis {
    public static void main(String[] args) {
        A a = new A(10);
    }
}