package com.bodima.project_lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    private String id;
    private String title;
    private String description;
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

}
