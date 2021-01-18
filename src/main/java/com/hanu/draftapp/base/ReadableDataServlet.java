package com.hanu.draftapp.base;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanu.draftapp.exceptions.ErrorObject;
import com.hanu.draftapp.utils.ResponseWrapper;

public interface ReadableDataServlet {
    default void doResourceGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final ResponseWrapper responseWrapper = ResponseWrapper.wrap(resp);
        // GET /resources/{id}
        // GET /resources/{id}/nestedResources
        final String extraRouteMatch = req.getPathInfo();
        // GET /resources/
        if (extraRouteMatch.length() <= 1) {
            // doGetAllResources
            System.out.println("doGetAllResources");
            return;
        }
        Pattern pattern = Pattern.compile("/(\\d+)(/\\w*){0,1}");
        Matcher matcher = pattern.matcher(extraRouteMatch);
        if (!matcher.matches() || 
                extraRouteMatch.replace("/","").length() - extraRouteMatch.length() > 2) {
            responseWrapper.body(new ErrorObject(null)).end();
            return;
        }
        String idGroup = matcher.group(1);
        String nestedResourceGroup = matcher.group(2);
        if (Objects.isNull(nestedResourceGroup)) {
            System.out.println("doGetResourceById");
            // doGetResourceById
            return;
        } else {
            // doGetNestedResourceById
            System.out.println("doGetNestedResourceById");
            return;
        }
    }

    default void doGetAllResources() {}
}
