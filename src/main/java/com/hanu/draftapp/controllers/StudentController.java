package com.hanu.draftapp.controllers;

import java.util.List;
import java.util.Optional;

import com.hanu.draftapp.models.Student;

public interface StudentController {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(long id);
}
