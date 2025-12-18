package com.revature.weekTen.StudentAppTest;

import com.revature.weekTen.StudentApp.Model.Classroom;
import com.revature.weekTen.StudentApp.Model.Student;
import com.revature.weekTen.StudentApp.Repository.ClassroomRepository;
import com.revature.weekTen.StudentApp.Repository.StudentRepository;
import com.revature.weekTen.StudentApp.Service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class StudentServiceTest {
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassroomRepository classroomRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        studentService = new StudentService(studentRepository, classroomRepository);
    }

    @Test
    public void testAssignClassroomToStudent() {
        long studentId = 1L;
        Classroom classroom = new Classroom();

        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        studentService.assignClassroomToStudent(studentId, classroom);
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testGetClassroomOfStudent() {
        long studentId = 1L;
        Classroom classroom = new Classroom();

        Student student = new Student();
        student.setId(studentId);
        student.setClassroom(classroom);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        Classroom returnedClassroom = studentService.getClassroomOfStudent(studentId);

        Assertions.assertEquals(classroom, returnedClassroom);
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    public void testUnassignClassroomOfStudent() {
        long studentId = 1L;

        Student student = new Student();
        student.setId(studentId);
        student.setClassroom(new Classroom());

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        studentService.unassignClassroomOfStudent(studentId);

        Assertions.assertNull(student.getClassroom());
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(student);
    }
}
