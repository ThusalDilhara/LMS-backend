package com.bodima.project_lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseAnnouncements {
    private String id;
    private int courseId;
    private String fileUrl;
    private String description;
    private String originalFileName;
    private String fileType;
    private long fileSize;
}
