package com.bodima.project_lms.controller;

import com.bodima.project_lms.dto.CourseAnnouncements;
import com.bodima.project_lms.model.CourseAnnouncementsMoel;
import com.bodima.project_lms.service.CourseAnnouncementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/course-announcements")
@RequiredArgsConstructor
public class CourseAnnouncementsController {

    private final CourseAnnouncementsService courseAnnouncementsService;

    @PostMapping(value = "/add-announcements",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> addAnnouncements(@RequestParam("file") MultipartFile file,
                                 @RequestParam("courseId") int courseId,
                                 @RequestParam("description") String description) {

        try{
            CourseAnnouncements announcement = new CourseAnnouncements();
            announcement.setCourseId(courseId);
            announcement.setDescription(description);

            courseAnnouncementsService.addAnnouncements(announcement,file);
            return ResponseEntity.ok("Announcement created successfully with file");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
}