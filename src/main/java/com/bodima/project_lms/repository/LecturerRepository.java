package com.bodima.project_lms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bodima.project_lms.model.Lecturer;

public interface LecturerRepository extends MongoRepository<Lecturer, String> {
    
    
}
