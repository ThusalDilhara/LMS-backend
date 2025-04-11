package com.bodima.project_lms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "course")
public class Course {

    @Id
    private Integer courseId;
    private String title;
    private String description;
    private String instructor;
    private String catagoty;
    private String level; // intermediate,beginner,advanced
    private Set<String> modules;
    private double price;
    private Integer enrollmentLimit;
    private Date startData;
    private Date endDate;
    private String language;
    private String resources;
    private String reviewForCourse;
    private Double rating;
    private String status;
}

