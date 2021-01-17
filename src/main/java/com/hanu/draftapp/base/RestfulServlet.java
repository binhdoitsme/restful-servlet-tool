package com.hanu.draftapp.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An extended abstract Servlet to serve data
 */
public abstract class RestfulServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // apply application/json content type to all responses
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpServletResponseWrapper.wrap(resp)
            .body(Arrays.toString(req.getPathInfo().substring(1).split("/")))
            .ok()
            .end();
    }

    static class HttpServletResponseWrapper {
        public static HttpServletResponseWrapper wrap(HttpServletResponse resp) {
            return new HttpServletResponseWrapper(resp); }

        private final HttpServletResponse response;
        private HttpServletResponseWrapper(HttpServletResponse response) {
            this.response = response;
        }

        public HttpServletResponseWrapper ok() {
            response.setStatus(HttpServletResponse.SC_OK);
            return this; }
    
        public HttpServletResponseWrapper created() {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return this; }
    
        public HttpServletResponseWrapper notFound() { 
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return this; }
    
        public HttpServletResponseWrapper unauthorized() { 
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return this; }
    
        public HttpServletResponseWrapper serverError(HttpServletResponse resp) { 
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return this; }

        public HttpServletResponseWrapper body(String body) {
            try {
                PrintWriter writer = response.getWriter();
                writer.println(body);
                writer.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return this; 
        }

        public void end() { 
            try { response.flushBuffer(); } 
            catch (IOException ex) { throw new RuntimeException(ex); }
        }
    }
}
