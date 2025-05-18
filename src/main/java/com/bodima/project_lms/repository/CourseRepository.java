package com.bodima.project_lms.repository;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.model.CourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<CourseEntity,String> {


    List<Course> findByInstructorId(String id);
    
    Iterable<Object> findByTitleStartingWith(String title, String catagory);
}
