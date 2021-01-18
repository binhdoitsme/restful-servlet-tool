package com.hanu.draftapp.base;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CreatableDataServlet {
    default void doResourcePost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        // POST /resources
        // POST /resources/{id}/nestedResources
        String extraRouteMatch = req.getPathInfo();
        if (extraRouteMatch.length() <= 1) {
            // doPostOnResources
            return;
        }
        Pattern pattern = Pattern.compile("/(\\d+)/{0,1}(\\w*)");
        Matcher matcher = pattern.matcher(extraRouteMatch);
        if (!matcher.matches() || 
            extraRouteMatch.replace("/","").length() - extraRouteMatch.length() > 2) {
            // 415 METHOD NOT ALLOWED
            return;
        }
    }
}
