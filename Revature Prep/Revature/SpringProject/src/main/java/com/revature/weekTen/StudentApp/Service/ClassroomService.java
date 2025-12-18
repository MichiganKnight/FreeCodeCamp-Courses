package com.revature.weekTen.StudentApp.Service;

import com.revature.weekTen.StudentApp.Model.Classroom;
import com.revature.weekTen.StudentApp.Model.Student;
import com.revature.weekTen.StudentApp.Repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {
    ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public Classroom addClassroom(Classroom classroom){
        return classroomRepository.save(classroom);
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public void addStudentToClassroom(long classroomId, Student student) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
        if (classroomOptional.isPresent()) {
            Classroom classroom = classroomOptional.get();
            classroom.getStudents().add(student);
            classroomRepository.save(classroom);
        }
    }

    public List<Student> getStudentsInClassroom(long classroomId) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
        if (classroomOptional.isPresent()) {
            Classroom classroom = classroomOptional.get();
            return classroom.getStudents();
        }

        return null;
    }

    public void removeStudentFromClassroom(long classroomId, long studentId) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
        if (classroomOptional.isPresent()) {
            Classroom classroom = classroomOptional.get();
            classroom.getStudents().removeIf(student -> student.getId() == studentId);
            classroomRepository.save(classroom);
        }
    }
}
