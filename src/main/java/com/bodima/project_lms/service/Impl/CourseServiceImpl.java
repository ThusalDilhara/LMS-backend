package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.dto.LessonRequest;
import com.bodima.project_lms.model.CourseEntity;
import com.bodima.project_lms.repository.CourseRepository;
import com.bodima.project_lms.service.CourseService;
import com.bodima.project_lms.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

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

//        if (courseEntity.getId() == null) {
//            courseEntity.setId(sequenceGenerator.getNextSequence(COURSE_SEQUENCE_NAME));
//        }

        courseRepository.save(modelMapper.map(course, CourseEntity.class));
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
        courseRepository.findByTitleOrCatagoty(title, catagory).forEach(course ->
                courseList.add(modelMapper.map(course, Course.class)));
        return List.of();
    }

    @Override
    public List<Course> getCourcesByInstructorId(String id) {
        List<Course> courseList = new ArrayList<>();
         courseRepository.findByInstructorId(id).forEach(course ->
                courseList.add(modelMapper.map(course,Course.class)));
         return courseList;
    }

    @Override
    public void enrollStudentForCourse(Integer studentId, Integer courseId) {
    //TODO must be implemented
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findAll().forEach(course ->
                courseList.add(modelMapper.map(course,Course.class)));
    return courseList;
    }

    @Override
    public Course addLesson(String courseId, LessonRequest lessonRequest, String token) {
        return null;
    }
}
