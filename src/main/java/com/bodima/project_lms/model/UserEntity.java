package com.bodima.project_lms.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;

    // Common fields (already in UserEntity or present in both Student/Lecturer)
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String verifyCode; // Specific to your current UserEntity, might be for general user verification
    private Boolean active;
    private Boolean verified; // Specific to your current UserEntity
    private String role; // "STUDENT", "LECTURER", "ADMIN", etc.
    private Date createDateTime = new Date();
    private Date updatedAt;
    private Date lastLogin;
    private String username; // Can be derived or explicitly set
    private String address;
    private String city;
    private String phoneNumber;
    private String faculty;
    private String department;
    private String profilePic; // Common for both, or can be lecturer-specific

    // Student-specific fields (will be null if role is not STUDENT)
    private String studentNo;

    // Lecturer-specific fields (will be null if role is not LECTURER)
    private String designation;
    private String specialization;
    private List<String> courses; // List of course IDs/names taught by lecturer
    private String qualifications;
    private String researchInterests;
    private String nic;
    // General fields (like enrolledCourses, if applicable to more than just students, or could be role-specific)
    private Set<String> enrolledCourses = new HashSet<>(); // If only for students, check role before using


}