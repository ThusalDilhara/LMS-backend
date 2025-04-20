package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.model.Student;
import com.bodima.project_lms.repository.StudentRepository;
import com.bodima.project_lms.service.EmailService;
import com.bodima.project_lms.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

     @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
public Student registerStudent(Student student) {

    // Check for duplicates
    if (studentRepository.existsByStudentNo(student.getStudentNo())) {
        throw new RuntimeException("Student with this number already exists.");
    }

    // Auto generate username and password
    String username = student.getFirstName().toLowerCase() + "." + student.getLastName().toLowerCase();
    String rawPassword = UUID.randomUUID().toString().substring(0, 8); // "aB12xYz9"
    String encodedPassword = encoder.encode(rawPassword);

    student.setUsername(username);
    student.setPassword(encodedPassword);

    Student savedStudent = studentRepository.save(student);

    //Send login credentials via email
    emailService.sendStudentCredentials(
        student.getEmail(),      
        student.getUsername(),   
        rawPassword              // raw (non-encoded) password for login
    );

    return savedStudent;
}


    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

}
