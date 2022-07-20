package com.revature.versusapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Person;
import com.revature.versusapp.services.UserService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//our servlets should extend HttpServlet
public class UserServlet extends HttpServlet {
    
    private ObjectMapper objMapper;
    private UserService userService;

    {
        userService = new UserService();
        objMapper = ObjectMapperUtil.getObjectMapper();
    }
     @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
