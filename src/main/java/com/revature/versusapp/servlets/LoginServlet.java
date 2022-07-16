package com.revature.versusapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.rest.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        
        String payload  = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        
        writer.write("You have posted to the login servlet. congrats.");
        ObjectMapper objMapper = new ObjectMapper();
        
        try {
            Login car =  objMapper.readValue(payload, Login.class);
            
            writer.write("login deserialized\n");
            writer.write("user name: " + car.getUsername() + "\n");
            writer.write("password: " + car.getPassword() + "\n");
            
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
    
    
}
