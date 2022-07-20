package com.revature.versusapp.servlets;

import java.io.IOException;

import com.revature.versusapp.models.rest.JsonError;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

public class ErrorReportingHttpServlet extends HttpServlet{
    
    static void writeErrorResponse(HttpServletResponse resp, int code, String message) {
        JsonError error = new JsonError();
        
        error.setCode(code);
        error.setMessage(message);
        
        String jsonMessage = "";
        
        try {
            jsonMessage = ObjectMapperUtil.getObjectMapper().writeValueAsString(error);
            resp.getWriter().write(jsonMessage);
        } catch (IOException e) {
            // The status code will be set, so just ignore this if it fails.
        }
        
        resp.setStatus(code);
    }

}
