package com.hanu.draftapp.servlets;

import javax.servlet.annotation.WebServlet;

import com.hanu.draftapp.base.RestfulServlet;

@WebServlet(name = "students", urlPatterns = { "/students/*" })
public class StudentServlet extends RestfulServlet {
    private static final long serialVersionUID = 1L;
    
}
