package com.revature.weekTen.StudentApp.Service;

import com.revature.weekTen.StudentApp.Model.Classroom;
import com.revature.weekTen.StudentApp.Model.Student;
import com.revature.weekTen.StudentApp.Repository.ClassroomRepository;
import com.revature.weekTen.StudentApp.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;
    ClassroomRepository classroomRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, ClassroomRepository classroomRepository) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
    }

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void assignClassroomToStudent(long studentId, Classroom classroom) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setClassroom(classroom);
            studentRepository.save(student);
        }
    }

    public Classroom getClassroomOfStudent(long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return student.getClassroom();
        }

        return null;
    }

    public void unassignClassroomOfStudent(long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setClassroom(null);
            studentRepository.save(student);
        }
    }
}
