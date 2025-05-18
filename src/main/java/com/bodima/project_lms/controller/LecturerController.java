package com.bodima.project_lms.controller;

import com.bodima.project_lms.model.Lecturer;
import com.bodima.project_lms.service.LecturerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/lecturer")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @PostMapping("/register-lecturer")
    // @PreAuthorize("hasRole('ADMIN')")
    public Lecturer registerLecturer(@RequestBody Lecturer lecturer) {
        return lecturerService.registerLecturer(lecturer);
    }



    @GetMapping("/get-all-students")
    // @PreAuthorize("hasRole('ADMIN')")
    public List<Lecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @PutMapping("/update-lecturer/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable String id, @RequestBody Lecturer lecturer) {
        return ResponseEntity.ok(lecturerService.updateLecturer(id , lecturer));
    }


}
