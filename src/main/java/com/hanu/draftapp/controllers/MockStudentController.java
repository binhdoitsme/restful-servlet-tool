package com.hanu.draftapp.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.hanu.draftapp.models.Student;

public class MockStudentController implements StudentController {
    final List<Student> students = new LinkedList<>();
    public MockStudentController() {
        
    }

    @Override
    public List<Student> getAllStudents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Student> getStudentById(long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
