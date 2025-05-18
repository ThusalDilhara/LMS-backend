package com.bodima.project_lms.controller;

import com.bodima.project_lms.dto.CourseAnnouncements;
import com.bodima.project_lms.service.CourseAnnouncementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/course-announcements")
@RequiredArgsConstructor
public class CourseAnnouncementsController {

    private final CourseAnnouncementsService courseAnnouncementsService;

    @PostMapping(value = "/add-announcements", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> addAnnouncements(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "courseId", required = false) int courseId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "closeDate", required = false) String closeDate,
            @RequestParam(value = "openDate", required = false) String openDate,
            @RequestParam(value = "contentMonth", required = false) String contentMonth,
            @RequestParam(value = "contentWeek", required = false) String contentWeek,
            @RequestParam(value = "isAssignment", required = false) Boolean isAssignment,
            @RequestParam(value = "isNote", required = false) Boolean isNote,
            @RequestParam(value = "isAnnouncement", required = false) Boolean isAnnouncement


    ) {

        try {

            CourseAnnouncements announcement = new CourseAnnouncements();
            announcement.setCourseId(courseId);
            announcement.setDescription(description);
            announcement.setOpenDate(openDate);
            announcement.setCloseDate(closeDate);
            announcement.setContentMonth(contentMonth);
            announcement.setContentWeek(contentWeek);
            announcement.setIsAssignment(isAssignment);
            announcement.setIsNote(isNote);
            announcement.setIsAnnouncement(isAnnouncement);

            courseAnnouncementsService.addAnnouncements(announcement, file);
            return ResponseEntity.ok("Announcement created successfully with file");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/get-annousements-and-content")
    public List<CourseAnnouncements> getAnnouementsAndContents(@RequestParam int id){
        return courseAnnouncementsService.getAnnouementsAndContents(id);
    }
}