package com.bodima.project_lms.model;

import com.bodima.project_lms.dto.Lesson;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "course")
public class CourseEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private String instructorTitle;
    private String instructor;
    private Integer instructorId; //TODO add key
    private String catagory;
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
    private List<Lesson> lessons;
}

