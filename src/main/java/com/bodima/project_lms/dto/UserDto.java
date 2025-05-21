package com.bodima.project_lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
public class UserDto {

    private String id; // Changed from long to String to match UserEntity
    private long collageId; // Kept as is, assuming it's used elsewhere
    private String createDateTime;
    private boolean verified;
    private boolean active;
    private Set<String> enrolledCourses; // This was the selected field, already present

    @NotBlank(message = "Email is required!")
    @Pattern(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", message = "Invalid email address!")
    @Size(min = 4, max = 255, message = "Email should have 4 to 255 characters")
    private String email;

    @NotBlank(message = "First name is required!")
    @Size(min = 2, max = 255, message = "First name should have 2 to 255 characters") // Adjusted min for typical names
    private String firstName;

    @NotBlank(message = "Last name is required!")
    @Size(min = 2, max = 255, message = "Last name should have 2 to 255 characters") // Adjusted min
    private String lastName;

    @NotBlank(message = "Role is required!")
    @Size(min = 4, max = 50, message = "Role should have 4 to 50 characters") // Adjusted max
    private String role; // "STUDENT", "LECTURER", "ADMIN", etc.

    // Password might not always be sent in a DTO, especially for responses.
    // For requests (e.g., registration), it's needed.
    @NotBlank(message = "Password is required!")
    @Size(min = 4, max = 255, message = "Password should have 4 to 255 characters")
    private String password;

    // Fields from UserEntity
    private String verifyCode;
    private String updatedAt;
    private String lastLogin;
    private String username;
    private String address;
    private String city;
    private String phoneNumber;
    private String faculty;
    private String department;
    private String profilePic;

    // Student-specific fields
    private String studentNo;

    // Lecturer-specific fields
    private String designation;
    private String specialization;
    private List<String> courses; // List of course IDs/names taught by lecturer
    private String qualifications;
    private String researchInterests;
    private String nic; // Added from Lecturer model, if it's still relevant for lecturers

}