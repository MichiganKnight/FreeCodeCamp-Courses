public class ABC {
    public void disp() {
        System.out.println("disp() Method of Parent Class");
    }
}

public class Demo extends ABC {
    public void disp() {
        System.out.println("disp() Method of Child Class");
    }

    public void newMethod() {
        System.out.println("New Method of Child Class");
    }

    public static void main(String[] args) {
        ABC obj = new ABC();
        obj.disp();

        ABC obj2 = new Demo();
        obj2.disp();
    }
}