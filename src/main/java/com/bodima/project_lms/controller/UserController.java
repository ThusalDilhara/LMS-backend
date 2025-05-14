package com.bodima.project_lms.controller;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

private final UserServiceImpl userService;

@GetMapping("/getEnrolledCourses")
    public List<Course> getEnrolledCourses(@RequestParam String id){
    return userService.getEnrolledCourses(id);
}

@PostMapping("add-course-enroll")
public void addCourseToUser(@RequestParam String courseId ,@RequestParam String userId){
    userService.addCourseToUser(userId,courseId);

}

}
