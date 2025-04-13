package com.bodima.project_lms.repository;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.model.CourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<CourseEntity,String> {

    List<Course> findByTitleOrCatagory(String title, String catagory);

    List<Course> findByInstructorId(String id);

}
