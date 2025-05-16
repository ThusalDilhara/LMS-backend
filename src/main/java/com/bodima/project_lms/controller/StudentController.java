package com.bodima.project_lms.controller;

import com.bodima.project_lms.model.Student;
import com.bodima.project_lms.service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register-student")
    // @PreAuthorize("hasRole('ADMIN')")
    public Student registerStudent(@RequestBody Student student) {
        return studentService.registerStudent(student);
    }



    @GetMapping("/get-all-students")
    // @PreAuthorize("hasRole('ADMIN')")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PutMapping("/update-student/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id , student));
    }


}
