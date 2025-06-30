public class Student {
    int rollno;
    String name;
    static String college = "Valpo";;

    Student(int r, String n) {
        rollno = r;
        name = n;
    }

    void display() {
        System.out.println(rollno + " " + name + " " + college);
    }
}

public class TestStaticVariable {
    public static void main(String[] args) {
        Student s1 = new Student(111, "John");
        Student s2 = new Student(2222, "Jane");

        // Student.college = "newCollege";
        s1.display();
        s2.display();
    }
}