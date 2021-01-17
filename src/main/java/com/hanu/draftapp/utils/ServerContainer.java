package com.hanu.draftapp.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;

public interface ServerContainer {
    ServerContainer defaultConfigure();
    ServerContainer addServlet(Class<? extends HttpServlet> servlet) throws Exception;
    ServerContainer addServlets(List<Class<? extends HttpServlet>> servletClasses);
    ServerContainer addServlets(String basePackage) throws Exception;
    void start() throws IOException;

    public static ServerContainer getInstance(String type) {
        switch (type.toLowerCase()) {
            case "tomcat":
                return new TomcatContainer();
            default:
                throw new IllegalArgumentException("Unsupported server container type!");
        }
    }
}
