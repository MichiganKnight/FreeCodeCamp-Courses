package com.revature.weekTen.StudentApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double grade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroomFK")
    private Classroom classroom;

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", grade=" + grade + "]";
    }
}
