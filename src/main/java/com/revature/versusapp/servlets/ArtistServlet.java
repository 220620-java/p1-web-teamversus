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
import com.revature.versusapp.services.ArtistService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArtistServlet extends ErrorReportingHttpServlet{
    
    private ArtistService artistService;
    private ObjectMapper objMapper;
    
    {
        artistService = new ArtistService();
        objMapper = ObjectMapperUtil.getObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Artist> artists = artistService.getArtists();
        
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        objMapper.writeValue(resp.getWriter(), artists);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Attempt to deserialize the request body as a NewArtist object. Return
        // SC_BAD_REQUEST if unable to do so.
        boolean hasError = false;
        NewArtist newArtist = null;
        int code =  HttpServletResponse.SC_OK;
        String errorMessage = "";

        try {
            newArtist = objMapper.readValue(req.getReader(), NewArtist.class);
        } catch (JsonProcessingException e) {
            hasError = true;
            errorMessage = e.getMessage();
            code = HttpServletResponse.SC_BAD_REQUEST;
        }

        if (hasError) {
            writeErrorResponse(resp, code, errorMessage);
            return;
        }
        
        Artist artist = new Artist(newArtist.getStagename());
        
        boolean artistAdded = artistService.addArtist(artist);
        
        if ( artistAdded ) {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            errorMessage = "Unable to add artist to the database,";
            writeErrorResponse(resp, 422, errorMessage);
        }
    }
}
