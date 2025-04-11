package com.bodima.project_lms.service;

import com.bodima.project_lms.dto.Course;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);

    List<Course> deleteCourseById(Integer courseId);

    List<Course>getCoursesById(Integer courseId);

    List<Course> getCourcesByTitleOrCatagory(String title, String catagory);

    List<Course> getCourcesByInstructorId(Integer id);

    void enrollStudentForCourse(Integer studentId, Integer courseId);

    List<Course> getAllCourses();
}
