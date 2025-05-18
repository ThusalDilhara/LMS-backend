package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.exception.types.EntityAlreadyExistsException;
import com.bodima.project_lms.model.CourseEntity;
import com.bodima.project_lms.model.UserEntity;
import com.bodima.project_lms.repository.AuthRepository;
import com.bodima.project_lms.repository.CourseRepository;
import com.bodima.project_lms.service.CourseService;
import com.bodima.project_lms.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final SequenceGeneratorService sequenceGenerator;
    private static final String COURSE_SEQUENCE_NAME = "course_sequence";
    private final AuthRepository authRepository;

    @Override
    public void addCourse(Course course) {
        CourseEntity courseEntity = modelMapper.map(course, CourseEntity.class);

        if (courseEntity.getId() == null) {
            courseEntity.setId(String.valueOf(sequenceGenerator.getNextSequence(COURSE_SEQUENCE_NAME)));
        }

        courseRepository.save(courseEntity);
    }

    @Override
    public void deleteCourseById(String id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getCoursesById(String courseId) {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findById(courseId).ifPresent(course -> courseList.add(modelMapper.map(course, Course.class)));
        return courseList;
    }

    @Override
    public List<Course> getCourcesByTitleOrCatagory(String title, String catagory) {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findByTitleStartingWith(title, catagory).forEach(course -> courseList.add(modelMapper.map(course, Course.class)));
        return courseList;
    }


    @Override
    public List<Course> getCourcesByInstructorId(String id) {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findByInstructorId(id).forEach(course -> courseList.add(modelMapper.map(course, Course.class)));
        return courseList;
    }

    @Override
    public void enrollStudentForCourse(String studentId, String courseId) {
        Optional<CourseEntity> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent()) {
            CourseEntity course = courseOptional.get();

            if (course.getStudents() == null) {
                course.setStudents(new HashSet<>());
            }
            course.getStudents().add(studentId);
            courseRepository.save(course);

            if (course.getStudents().contains(studentId)) {
                throw new EntityAlreadyExistsException("Student is already enrolled in this course");
            }

            //add courseId to user

            Optional<UserEntity> userOptional = authRepository.findById(studentId);
            if (userOptional.isEmpty()) {
                return;
            }
            UserEntity userEntity = userOptional.get();
            userEntity.getEnrolledCourses().add(courseId);
            System.out.println("okay");
            authRepository.save(userEntity);


            if (userEntity.getEnrolledCourses().contains(courseId)) {
                throw new EntityAlreadyExistsException("Student is already enrolled in this course");
            }


        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }


    @Override
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> courseList.add(modelMapper.map(course, Course.class)));
        return courseList;
    }

    @Override
    public Set<String> enrolledStudents(String id) {
        Optional<CourseEntity> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get().getStudents() != null ? course.get().getStudents() : new HashSet<>();
        }
        return new HashSet<>();
    }


}
