package com.bodima.project_lms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bodima.project_lms.model.Student;

public interface StudentService {
    Student registerStudent(Student student);

    List<Student> getAllStudents();

    Student updateStudent(String id, Student student);
}
