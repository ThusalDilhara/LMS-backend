package com.bodima.project_lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequest {
    private String title;
    private String description;
    private LessonType type;
    private String contentUrl; // From file upload
    private int durationMinutes;
    private int order;
}
