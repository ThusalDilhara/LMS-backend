package com.bodima.project_lms.service;

import com.bodima.project_lms.dto.Course;

import java.util.List;
import java.util.Set;

public interface CourseService {
    void addCourse(Course course);

    void deleteCourseById(String courseId);

    List<Course>getCoursesById(String courseId);

    List<Course> getCourcesByTitleOrCatagory(String title, String catagory);

    List<Course> getCourcesByInstructorId(String id);

    public void enrollStudentForCourse(String studentId, String courseId);
    List<Course> getAllCourses();

    Set<String> enrolledStudents(String courseId);


}
