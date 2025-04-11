package com.bodima.project_lms.repository;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.model.CourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<CourseEntity,Integer> {

    List<Course> findByCourseId(Integer courseId);

    List<Course> findByTitleOrCatagoty(String title, String catagory);

    List<Course> findByInstructorId(Integer id);
}
