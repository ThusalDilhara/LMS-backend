package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.config.JwtAuthenticationController;
import com.bodima.project_lms.dto.ResponseDto;
import com.bodima.project_lms.dto.UserDto;
import com.bodima.project_lms.model.UserEntity;
import com.bodima.project_lms.repository.AuthRepository;
import com.bodima.project_lms.service.SequenceGeneratorService;
import com.bodima.project_lms.util.Utilities;
import com.itextpdf.text.pdf.qrcode.Mode;
import jakarta.persistence.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {


    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationController authenticationController;
    private final ModelMapper modelMapper;



    public UserEntity findValidUserByEmail(String email) {
        // This method might still be useful for login checks
        return authRepository.findFirstByEmailAndActiveTrueAndVerifiedTrue(email);
    }

    public UserEntity findUserByEmail(String email) {
        // This can be replaced by userService.getUserByEmail if preferred for consistency
        // For now, keeping it as it's used by AuthAPI
        return authRepository.findByEmail(email)
                .orElse(null); // Or throw an exception as in UserService
    }


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

            // Check if student number is already registered
            if (authRepository.existsByStudentNoAndRole(userDto.getStudentNo(), "STUDENT")) {
                throw new RuntimeException("Student with this number already exists: " + userDto.getStudentNo());
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

}
