package com.bodima.project_lms.service;

import com.bodima.project_lms.dto.CourseAnnouncements;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseAnnouncementsService {
    void addAnnouncements(CourseAnnouncements courseAnnouncements, MultipartFile file) throws IOException;

    List<CourseAnnouncements> getAnnouementsAndContents(int id);
}
