package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.exception.types.EntityAlreadyExistsException;
import com.bodima.project_lms.model.CourseEntity;
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
        courseRepository.findById(courseId).ifPresent(course ->
                courseList.add(modelMapper.map(course, Course.class)));
        return courseList;
    }

    @Override
    public List<Course> getCourcesByTitleOrCatagory(String title, String catagory) {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findByTitleOrCatagory(title, catagory).forEach(course ->
                courseList.add(modelMapper.map(course, Course.class)));
        return List.of();
    }

    @Override
    public List<Course> getCourcesByInstructorId(String id) {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findByInstructorId(id).forEach(course ->
                courseList.add(modelMapper.map(course, Course.class)));
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

            if (course.getStudents().contains(studentId)) {
                throw new EntityAlreadyExistsException("Student is already enrolled in this course");
            }

            course.getStudents().add(studentId);

            courseRepository.save(course);
        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }


    @Override
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findAll().forEach(course ->
                courseList.add(modelMapper.map(course, Course.class)));
        return courseList;
    }

    @Override
    public Set<String> enrolledStudents(String id) {
        Optional<CourseEntity> course = courseRepository.findById(id);
        if(course.isPresent()) {
            return course.get().getStudents() != null ? course.get().getStudents() : new HashSet<>();
        }
        return new HashSet<>();
    }





}
