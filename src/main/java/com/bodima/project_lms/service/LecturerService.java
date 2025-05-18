package com.bodima.project_lms.service;

import java.util.List;

import com.bodima.project_lms.model.Lecturer;

public interface LecturerService {

    Lecturer registerLecturer(Lecturer lecturer);

    List<Lecturer> getAllLecturers();

    Lecturer updateLecturer(String id, Lecturer lecturer);
} 