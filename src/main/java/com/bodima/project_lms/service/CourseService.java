package com.bodima.project_lms.service;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.dto.LessonRequest;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);

    void deleteCourseById(String courseId);

    List<Course>getCoursesById(String courseId);

    List<Course> getCourcesByTitleOrCatagory(String title, String catagory);

    List<Course> getCourcesByInstructorId(String id);

    void enrollStudentForCourse(Integer studentId, Integer courseId);

    List<Course> getAllCourses();

    Course addLesson(String courseId, LessonRequest lessonRequest, String token);
}
