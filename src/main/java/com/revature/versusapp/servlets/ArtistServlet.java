package com.revature.versusapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Artist;
import com.revature.versusapp.services.ersatz.ErsatzArtistService;
import com.revature.versusapp.services.ersatz.ErsatzUserService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArtistServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper objMapper = ObjectMapperUtil.getObjectMapper();
        ErsatzArtistService artistService = new ErsatzArtistService();
        
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
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
}
