package com.hanu.draftapp.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

public class ResponseWrapper {
    public static ResponseWrapper wrap(HttpServletResponse resp) {
        return new ResponseWrapper(resp);
    }

    private final Converter converter = Converter.getInstance("json");
    private final HttpServletResponse response;
    private Object bodyObject;

    private ResponseWrapper(HttpServletResponse response) {
        this.response = response;
    }

    public ResponseWrapper ok() {
        response.setStatus(HttpServletResponse.SC_OK);
        return this;
    }

    public ResponseWrapper created() {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return this;
    }

    public ResponseWrapper methodNotAllowed() {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return this;
    }

    public ResponseWrapper notFound() {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return this;
    }

    public ResponseWrapper unauthorized() {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return this;
    }

    public ResponseWrapper serverError(HttpServletResponse resp) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return this;
    }

    public ResponseWrapper body(Object body) {
        if (!Objects.isNull(this.bodyObject)) {
            throw new IllegalStateException("Do not set the request body twice!");
        }
        this.bodyObject = body;
        return this;
    }

    public void end() {
        if (bodyObject == null) return;
        try {
            PrintWriter writer = response.getWriter();
            writer.println(converter.forwardConvert(bodyObject));
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}