package com.bodima.project_lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "courseAnnouncement")
public class CourseAnnouncementsMoel {

    @Id
    private String id;
    private int courseId;
    private String fileUrl;
    private String description;
    private String originalFileName;
    private String fileType;
    private long fileSize;
    private String openDate;
    private String closeDate;
    private String contentMonth;
    private String contentWeek;
    private Boolean isAssignment;
    private Boolean isNote;
    private Boolean isAnnouncement;
}
