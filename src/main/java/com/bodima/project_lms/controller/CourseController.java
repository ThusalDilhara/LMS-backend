package com.bodima.project_lms.controller;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    //Get all cources
    @PostMapping("/add-course")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    //update course
    @PutMapping("/update-course")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    //delete course by id
    @DeleteMapping("/delete-course-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourseById(@PathVariable("id") String courseId) {
        courseService.deleteCourseById(courseId);
    }

    //get all cources
    @GetMapping("/get-all-courses")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    //get cources by id
    @GetMapping("get-course-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getCoursesById(@PathVariable("id") String courseId) {
        return courseService.getCoursesById(courseId);
    }

    //Search Courses by Title/Category
    @GetMapping("search-course-by-title-or-catagory")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Course> getCourcesByTitleOrCatagory(@RequestParam String title, @RequestParam String catagory) {
        return courseService.getCourcesByTitleOrCatagory(title, catagory);
    }

    //Get Courses by Instructor
    @GetMapping("set-all-cources-by-instructureId/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Course> getCourcesByInstructorId(@PathVariable String id) {
        return courseService.getCourcesByInstructorId(id);
    }

    //Enroll a Student in a Course
    @PostMapping("/enroll-student/{studentId}-for-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public void enrollStudentForCourse(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        courseService.enrollStudentForCourse(studentId, courseId);
    }


}
