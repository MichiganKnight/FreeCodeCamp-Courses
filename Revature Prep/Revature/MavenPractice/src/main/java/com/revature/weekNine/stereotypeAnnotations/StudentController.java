package com.revature.weekNine.stereotypeAnnotations;

import org.springframework.stereotype.Controller;

@Controller
public class StudentController {
    private Student student;

    public StudentController(Student student) {
        this.student = student;
    }
}