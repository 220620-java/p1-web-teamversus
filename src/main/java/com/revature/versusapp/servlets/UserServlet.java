package com.revature.versusapp.servlets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Person;
import com.revature.versusapp.services.UserService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//our servlets should extend HttpServlet
public class UserServlet extends ErrorReportingHttpServlet {
    
    private ObjectMapper objMapper;
    private UserService userService;

    {
        userService = new UserService();
        objMapper = ObjectMapperUtil.getObjectMapper();
    }
     @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         
         StringBuffer url = req.getRequestURL();
         URL urlObject = new URL(url.toString());
         
         System.out.println("========");
         System.out.println(urlObject.getPath());
         StringTokenizer st = new StringTokenizer(urlObject.getPath(),"/");
//         while (st.hasMoreTokens()) {
//             System.out.println(st.nextToken());
//         }
     
         //The tokens should be:
         //1)  versusapi
         //2) user
         //3) a username or empty
         //4) inventory or empty
         
         String username = "";
         String inv = "";
         boolean hasError = false;
         
         
         st.nextToken();
         st.nextToken();
         
         if ( st.hasMoreTokens() ) {
             username = st.nextToken();
         }
         
         if ( st.hasMoreTokens() ) {
             inv = st.nextToken();
         }
         
         if ( st.hasMoreTokens() ) {
             hasError = true;
         }
         
         if ( inv.length() >0 && !inv.equals("inventory") ) {
             hasError = true;
         }
         
         if ( hasError ) {
             writeErrorResponse(resp, 404, "Invalid request URL.");
             return;
         }

         
         List<Person> people = userService.getPeople();
         
         ArrayList<String> names = new ArrayList<>(people.size());
         
         for ( Person person : people ) {
             names.add(person.getUsername());
         }
         
         resp.setCharacterEncoding("UTF-8");
         resp.setContentType("application/json");
         objMapper.writeValue(resp.getWriter(), names);
     }
}
