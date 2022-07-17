package com.revature.versusapp.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.rest.Credentials;
import com.revature.versusapp.models.rest.Login;
import com.revature.versusapp.models.rest.NewArtist;
import com.revature.versusapp.services.ersatz.ErsatzArtistService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArtistServlet extends HttpServlet{
    
    private ErsatzArtistService artistService;
    private ObjectMapper objMapper;
    
    {
        artistService = new ErsatzArtistService();
        objMapper = ObjectMapperUtil.getObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Artist> artists = artistService.getArtists();
        
        String artistList = null;
        try {
            artistList = objMapper.writeValueAsString(artists);
        } catch (JsonProcessingException e) {
            
        }
        
        if ( artistList != null ) {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.setContentLength(artistList.length());
            resp.getWriter().write(artistList);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Serialization has failed.\n");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Attempt to deserialize the request body as a Login object. Return
        // SC_BAD_REQUEST if unable to do so.
        String body = req.getReader()
                         .lines()
                         .collect(Collectors.joining(System.lineSeparator()));
        boolean hasError = false;
        String errorString = "";
        NewArtist newArtist = null;
        
        try {
            newArtist = objMapper.readValue(body, NewArtist.class);
        } catch (JsonMappingException e) {
            hasError = true;
            errorString = e.getMessage();
        } catch (JsonProcessingException e) {
            hasError = true;
            errorString = e.getMessage();
        }
        
        if ( hasError ) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(errorString);
            return;
        }
        
        boolean artistAdded = artistService.addArtist(newArtist);
        
        if ( artistAdded ) {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            resp.setStatus(422);
        }
    }
}
