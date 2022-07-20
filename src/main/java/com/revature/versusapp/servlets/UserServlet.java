package com.revature.versusapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Artist;
import com.revature.versusapp.services.ersatz.ErsatzArtistService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//our servlets should extend HttpServlet
public class UserServlet extends HttpServlet {
     // the servlet container (tomcat catalina) will call this method when
     // a GET request comes in to the right path and it will create the objects
     // for the request and response and pass those in
     @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//         // gets the response body writer object so that we can write to the response body
//         PrintWriter writer = resp.getWriter();
//         
//         //Person person = new Person("apiuser","1234","Api","User");
//         ObjectMapper objMapper = new ObjectMapper();
//         ErsatzArtistService artistService = new ErsatzArtistService();
//         
//         List<Artist> artists = artistService.getArtists();
//         
//         String serializedPerson = null;
//         try {
//             //System.out.println(objMapper.writeValueAsString(user));
//             serializedPerson = objMapper.writeValueAsString(artists);
//         } catch (JsonProcessingException e) {
//             e.printStackTrace();
//         }
//         
//         if ( serializedPerson != null ) {
//             writer.write(serializedPerson);
//         }
//         else {
//             writer.write("Serialization has failed.\n");
//         }
         
         
     }
}
