
package com.bodima.project_lms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("lecturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String faculty;
    private String department;
    private String designation;
    private String specialization;
    private List<String> courses; // List of course IDs/names
    private String username;
    private String password;
    private String role = "LECTURER";
    private boolean active = true;
    private String profilePic;
    private String qualifications;
    private String researchInterests;
    private Date lastLogin;
    private Date createdAt;
    private Date updatedAt;
}
