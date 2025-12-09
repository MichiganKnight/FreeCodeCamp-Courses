package com.revature.weekSeven;

import java.util.ArrayList;
import java.util.Collections;

public class ComparingBasic {
    public static void main(String[] args) {
        System.out.println("=== Basic Comparing ===");

        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(101, "Vijay", 23));
        students.add(new Student(106, "Ajay", 28));
        students.add(new Student(105, "Jai", 21));

        Collections.sort(students);
        for (Student student : students) {
            System.out.println(student.rollno + " " + student.name + " " + student.age);
        }
    }
}

class Student implements Comparable<Student> {
    int rollno;
    String name;
    int age;

    Student(int rollno, String name, int age) {
        this.rollno = rollno;
        this.name = name;
        this.age = age;
    }

    public int compareTo(Student student) {
        if (age == student.age) {
            return 0;
        } else if (age > student.age) {
            return 1;
        } else {
            return -1;
        }
    }
}
