package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.dto.Course;
import com.bodima.project_lms.dto.UserDto;
import com.bodima.project_lms.model.CourseEntity;
import com.bodima.project_lms.model.UserEntity;
import com.bodima.project_lms.repository.AuthRepository;
import com.bodima.project_lms.repository.CourseRepository;
import com.bodima.project_lms.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final AuthRepository authRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional // Good practice for operations involving multiple steps or saves
    public UserEntity registerUser(UserDto userDto) {
        if (authRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("User already exists with email: " + userDto.getEmail());
        }

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        // Role-specific validation or setup
        String role = userDto.getRole().toUpperCase();
        userEntity.setRole(role);

        if ("LECTURER".equals(role)) {
            if (userDto.getNic() == null || userDto.getNic().isEmpty()) {
                throw new RuntimeException("NIC is required for lecturers.");
            }
            if (authRepository.existsByNicAndRole(userDto.getNic(), "LECTURER")) {
                throw new RuntimeException("Lecturer already exists with NIC: " + userDto.getNic());
            }
            // ModelMapper should handle these if names match, otherwise set explicitly
            userEntity.setNic(userDto.getNic());
            userEntity.setDesignation(userDto.getDesignation());
            userEntity.setSpecialization(userDto.getSpecialization());
            userEntity.setQualifications(userDto.getQualifications());
            userEntity.setResearchInterests(userDto.getResearchInterests());
        } else if ("STUDENT".equals(role)) {
            if (userDto.getStudentNo() == null || userDto.getStudentNo().isEmpty()) {
                throw new RuntimeException("Student Number is required for students.");
            }
            // Add student-specific validation if any (e.g., uniqueness of studentNo)
            // boolean studentNoExists = authRepository.existsByStudentNoAndRole(userDto.getStudentNo(), "STUDENT");
            // if(studentNoExists) throw new RuntimeException("Student with this number already exists");
            userEntity.setStudentNo(userDto.getStudentNo());
        }
        // Add other roles as needed

        // Common setup
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            userEntity.setUsername(userDto.getFirstName().toLowerCase() + "." + userDto.getLastName().toLowerCase());
        } else {
            userEntity.setUsername(userDto.getUsername());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        } else {
            throw new IllegalArgumentException("Password is required for registration.");
        }

        String verifyCode = new Utilities().generateRandomNumber(); // From your AuthService
        userEntity.setVerifyCode(verifyCode);
        userEntity.setActive(true); // Default to active, or based on DTO
        userEntity.setVerified(false); // Default to not verified
        userEntity.setCreateDateTime(new Date());
        // userEntity.setId(null); // Ensure ID is null for MongoDB to generate it

        return authRepository.save(userEntity);
    }


    public UserEntity getUserById(String id) {
        return authRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }


    public UserEntity getUserByEmail(String email) {
        return authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }


    public List<UserEntity> getAllUsers() {
        return authRepository.findAll();
    }


    public List<UserDto> getUsersByRole(String role) {
        List<UserDto> users = new ArrayList<>();
       authRepository.findByRole(role.toUpperCase()).forEach(user -> users.add(modelMapper.map(user, UserDto.class)));
       return users;
    }


    @Transactional
    public UserEntity updateUser(String id, UserDto userDto) {
        UserEntity existingUser = getUserById(id);

        // Map common fields, careful with sensitive data like password, role, verified status
        // modelMapper.map(userDto, existingUser); // Use with caution, or map field by field

        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setAddress(userDto.getAddress());
        existingUser.setCity(userDto.getCity());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        existingUser.setFaculty(userDto.getFaculty());
        existingUser.setDepartment(userDto.getDepartment());
        existingUser.setProfilePic(userDto.getProfilePic());
        existingUser.setUsername(userDto.getUsername()); // Or re-generate if needed
        existingUser.setActive(userDto.isActive()); // Allow updating active status

        // Role-specific updates (Role change itself should be a very controlled operation)
        if ("LECTURER".equalsIgnoreCase(existingUser.getRole())) {
            existingUser.setDesignation(userDto.getDesignation());
            existingUser.setSpecialization(userDto.getSpecialization());
            existingUser.setQualifications(userDto.getQualifications());
            existingUser.setResearchInterests(userDto.getResearchInterests());
            if (userDto.getNic() != null && !userDto.getNic().equals(existingUser.getNic())) {
                if (authRepository.existsByNicAndRole(userDto.getNic(), "LECTURER")) {
                    throw new RuntimeException("Another Lecturer already exists with NIC: " + userDto.getNic());
                }
                existingUser.setNic(userDto.getNic());
            }
        } else if ("STUDENT".equalsIgnoreCase(existingUser.getRole())) {
            // Add student-specific update logic if studentNo can be updated
            // if (userDto.getStudentNo() != null && !userDto.getStudentNo().equals(existingUser.getStudentNo())) {
            //    check uniqueness for new studentNo
            //    existingUser.setStudentNo(userDto.getStudentNo());
            // }
            existingUser.setStudentNo(userDto.getStudentNo());
        }

        existingUser.setUpdatedAt(new Date());

        // Update student number with uniqueness check
        if (userDto.getStudentNo() != null && !userDto.getStudentNo().equals(existingUser.getStudentNo())) {
            // Check if another student already has this student number
            if (authRepository.existsByStudentNoAndRole(userDto.getStudentNo(), "STUDENT")) {
                throw new RuntimeException("Another student already exists with Student Number: " + userDto.getStudentNo());
            }
            existingUser.setStudentNo(userDto.getStudentNo());
        }
        return authRepository.save(existingUser);
    }


    @Transactional
    public void deleteUser(String id) {
        if (!authRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        // Consider soft delete (setting active=false) vs hard delete
        authRepository.deleteById(id);
    }

    // --- Course Enrollment Methods ---

    public List<Course> getEnrolledCourses(String userId) {
        UserEntity userEntity = authRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Set<String> enrolledCourseIds = userEntity.getEnrolledCourses();
        if (enrolledCourseIds == null || enrolledCourseIds.isEmpty()) {
            return List.of();
        }

        return enrolledCourseIds.stream()
                .map(courseId -> courseRepository.findById(courseId)
                        .map(courseEntity -> modelMapper.map(courseEntity, Course.class))
                        .orElse(null)) // Or handle missing courses differently
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }


    @Transactional
    public void addCourseToUser(String userId, String courseId) {
        UserEntity userEntity = authRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Optional: Check if course exists
        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        userEntity.getEnrolledCourses().add(courseId);
        authRepository.save(userEntity);
    }


    @Transactional
    public void removeCourseFromUser(String userId, String courseId) {
        UserEntity userEntity = authRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        userEntity.getEnrolledCourses().remove(courseId);
        authRepository.save(userEntity);
    }
}