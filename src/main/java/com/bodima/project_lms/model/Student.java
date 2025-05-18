package com.bodima.project_lms.model;

import lombok.*;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private String id;
    private String username;
    private String email;
    private String studentNo;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String profilePic;
    private String phoneNumber;
    private String faculty;
    private String department;

    private String password; 
    private String role = "STUDENT";
    private boolean active = true;
    private Date lastLogin;
    private Date createdAt;
    private Date updatedAt;
}
