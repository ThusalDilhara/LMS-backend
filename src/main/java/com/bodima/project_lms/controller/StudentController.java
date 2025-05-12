package com.bodima.project_lms.controller;

import com.bodima.project_lms.model.Student;
import com.bodima.project_lms.service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register-student")
    // @PreAuthorize("hasRole('ADMIN')")
    public Student registerStudent(@RequestBody Student student) {
        return studentService.registerStudent(student);
    }



    @GetMapping("/get-all-students")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
