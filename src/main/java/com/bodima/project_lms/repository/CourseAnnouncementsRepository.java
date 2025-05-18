package com.bodima.project_lms.repository;

import com.bodima.project_lms.dto.CourseAnnouncements;
import com.bodima.project_lms.model.CourseAnnouncementsMoel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseAnnouncementsRepository extends MongoRepository<CourseAnnouncementsMoel,String> {


    List<CourseAnnouncements> findByCourseId(int id);
}
