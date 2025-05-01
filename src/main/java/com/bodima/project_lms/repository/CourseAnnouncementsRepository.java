package com.bodima.project_lms.repository;

import com.bodima.project_lms.model.CourseAnnouncementsMoel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseAnnouncementsRepository extends MongoRepository<CourseAnnouncementsMoel,String> {


}
