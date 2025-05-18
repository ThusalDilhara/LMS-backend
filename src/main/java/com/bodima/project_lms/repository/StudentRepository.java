package com.bodima.project_lms.repository;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByUsername(String username);
    boolean existsByStudentNo(String studentNo);

}
