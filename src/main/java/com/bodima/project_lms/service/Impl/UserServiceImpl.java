package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.model.UserEntity;
import com.bodima.project_lms.repository.AuthRepository;
import com.bodima.project_lms.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final AuthRepository authRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public List<Course> getEnrolledCourses(String id) {
        Optional<UserEntity> userOptional = authRepository.findById(id);
        if (userOptional.isEmpty()) {
            return List.of(); // Return empty list if user not found
        }

        UserEntity userEntity = userOptional.get();
        Set<String> enrolledCoursesIds = userEntity.getEnrolledCourses();

        if (!enrolledCoursesIds.isEmpty()) {
            List<Course> enrolledCourse = new ArrayList<>();
            enrolledCoursesIds.forEach(courseId -> enrolledCourse.add(modelMapper.map(courseRepository.findById(courseId), Course.class)));
            return enrolledCourse;
        }
        return null;
    }


    public void addCourseToUser(String userId, String courseId) {
        Optional<UserEntity> userOptional = authRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return;
        }
        UserEntity userEntity = userOptional.get();
        userEntity.getEnrolledCourses().add(courseId);


    }
}
