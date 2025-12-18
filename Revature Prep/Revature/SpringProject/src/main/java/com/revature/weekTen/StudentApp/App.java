package com.revature.weekTen.StudentApp;

import com.revature.weekTen.StudentApp.Model.Classroom;
import com.revature.weekTen.StudentApp.Model.Student;
import com.revature.weekTen.StudentApp.Service.ClassroomService;
import com.revature.weekTen.StudentApp.Service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("=== Spring Data JPA App ===");

        ApplicationContext applicationContext = SpringApplication.run(App.class, args);
        ClassroomService classroomService = applicationContext.getBean(ClassroomService.class);
        StudentService studentService = applicationContext.getBean(StudentService.class);

        System.out.println();
        System.out.println("=== Creating Persistent Classroom and Student Entities ===");

        Classroom classroom1 = classroomService.addClassroom(new Classroom("A201"));
        Classroom classroom2 = classroomService.addClassroom(new Classroom("A202"));
        Student student1 = studentService.addStudent(new Student("John Doe", 1));
        Student student2 = studentService.addStudent(new Student("Jane Doe", 1));
        List<Classroom> classrooms = classroomService.getAllClassrooms();

        System.out.println("=== All Classrooms in Database ===");
        classrooms.forEach(c -> System.out.println(c.toString()));

        System.out.println();
        System.out.println("=== Assigning Students to Classrooms ===");
        classroomService.addStudentToClassroom(classroom1.getId(), student1);
        List<Student> allStudents = classroomService.getStudentsInClassroom(classroom1.getId());
        allStudents.forEach(s -> System.out.println(s.toString()));

        System.out.println();
        System.out.println("=== Unassigning Student from Classroom ===");
        classroomService.removeStudentFromClassroom(classroom1.getId(), student1.getId());
        System.out.println(classroomService.getStudentsInClassroom(classroom1.getId()).toString());

        System.out.println();
        System.out.println("=== Student Side Using Student Service ===");
        studentService.assignClassroomToStudent(student2.getId(), classroom2);
        System.out.println("=== Seconds Student's Classroom ===");
        System.out.println(studentService.getClassroomOfStudent(student2.getId()).toString());
        System.out.println("=== Unassigning Student from Classroom ===");
        studentService.unassignClassroomOfStudent(student2.getId());
        System.out.println(studentService.getClassroomOfStudent(student2.getId()));
    }
}
