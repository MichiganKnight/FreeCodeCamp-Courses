public class A {
    void m() {
        System.out.println("Hello M");
    }

    void n() {
        System.out.println("Hello N");
    }

    //m(); // Same as this.m();
    this.m(); // Added by Default by Compiler
}

public class TestThis {
    public static void main(String[] args) {
        A a = new A();

        a.n();
    }
}