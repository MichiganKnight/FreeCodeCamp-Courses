public class A {
    protected void msg() {
        System.out.println("Hello Java");
    }
}

public class Simple extends A {
    void msg() {
        System.out.println("Hello Java"); // Error Because Simple Method msg() is More Restrictive Than Class A Method msg()
    }

    public static void main(String[] args) {
        Simple obj = new Simple();
        obj.msg();
    }
}