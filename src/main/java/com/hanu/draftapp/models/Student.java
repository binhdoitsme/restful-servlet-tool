package com.hanu.draftapp.models;

import java.util.LinkedList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private List<Enrolment> enrolments;

    public Student() { }
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.enrolments = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(List<Enrolment> enrolments) {
        this.enrolments = enrolments;
    }
}
