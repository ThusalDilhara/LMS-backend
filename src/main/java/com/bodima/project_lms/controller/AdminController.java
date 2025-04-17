package com.bodima.project_lms.controller;

import com.bodima.project_lms.model.Student;
import com.bodima.project_lms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register-student")
    @PreAuthorize("hasRole('ADMIN')")
    public Student registerStudent(@RequestBody Student student) {
        return studentService.registerStudent(student);
    }
}
