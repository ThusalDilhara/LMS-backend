package com.bodima.project_lms.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CourseAnnouncements {
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
