package com.bodima.project_lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    private String id;
    private String title;
    private String description;
    private LessonType type; // VIDEO, PDF, QUIZ
    private String contentUrl; // URL to the file (S3, Firebase,local)
    private int durationMinutes; // For videos
    private int order; // To sort lessons sequentially
}
