package com.bodima.project_lms.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bodima.project_lms.model.Lecturer;
import com.bodima.project_lms.repository.LecturerRepository;
import com.bodima.project_lms.service.LecturerService;

@Service
public class LecturerServiceImpl implements LecturerService {

@Autowired
private LecturerRepository lecturerRepository;

private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Lecturer registerLecturer(Lecturer lecturer) {
        if(lecturerRepository.existsByNic(lecturer.getNic())){
            throw new RuntimeException("Lecturer already exists with NIC: " + lecturer.getNic());
        }

        String username = lecturer.getFirstName().toLowerCase() + "." + lecturer.getLastName().toLowerCase();
        String rawPassword = UUID.randomUUID().toString().substring(0, 8); 
        String encodedPassword = encoder.encode(rawPassword);

        lecturer.setUsername(username);
        lecturer.setPassword(encodedPassword);

        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        return savedLecturer;


    }

    @Override
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    @Override
    public Lecturer updateLecturer(String id, Lecturer lecturer) {
        return lecturerRepository.findById(id).map(
            existingLecturer -> {
                existingLecturer.setFirstName(lecturer.getFirstName());
                existingLecturer.setLastName(lecturer.getLastName());
                existingLecturer.setNic(lecturer.getNic());
                existingLecturer.setEmail(lecturer.getEmail());
                existingLecturer.setPhoneNumber(lecturer.getPhoneNumber());
                existingLecturer.setAddress(lecturer.getAddress());
                existingLecturer.setCity(lecturer.getCity());
                existingLecturer.setFaculty(lecturer.getFaculty());                existingLecturer.setDepartment(lecturer.getDepartment());
                existingLecturer.setDepartment(lecturer.getDepartment());
                existingLecturer.setDesignation(lecturer.getDesignation());
                existingLecturer.setSpecialization(lecturer.getSpecialization());
                existingLecturer.setQualifications(lecturer.getQualifications());
                existingLecturer.setResearchInterests(lecturer.getResearchInterests());
                return lecturerRepository.save(existingLecturer);

            }
        ).orElseThrow(() -> new RuntimeException("Lecturer not found with id: " + id));
    }

}
