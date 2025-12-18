package com.revature.weekTen.StudentApp.Repository;

import com.revature.weekTen.StudentApp.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}
