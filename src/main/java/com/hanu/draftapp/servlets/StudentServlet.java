package com.hanu.draftapp.servlets;

import javax.servlet.annotation.WebServlet;

import com.hanu.draftapp.annotations.Resource;
import com.hanu.draftapp.base.RestfulServlet;
import com.hanu.draftapp.models.Student;

@WebServlet(name = "students", urlPatterns = { "/students/*" })
@Resource(name = "students", resourceClass = Student.class, 
          nestedResources = { "enrolments" })
public class StudentServlet extends RestfulServlet {
    private static final long serialVersionUID = 1L;
}
