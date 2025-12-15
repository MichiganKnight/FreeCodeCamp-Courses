package com.revature.weekNine.lambokTesting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Student {
    public String studentID;
    public String firstName;
    public String lastName;
    public String major;

    public Student(String studentID, String firstName, String lastName, String major) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
    }
}
