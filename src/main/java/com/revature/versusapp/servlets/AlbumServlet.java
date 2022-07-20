package com.revature.versusapp.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.rest.NewAlbum;
import com.revature.versusapp.services.AlbumService;
import com.revature.versusapp.services.ersatz.ErsatzAlbumService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AlbumServlet extends HttpServlet{
    private ObjectMapper objMapper;
    private AlbumService albumService;
    
    {
        albumService = new AlbumService();
        objMapper = ObjectMapperUtil.getObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
            List<Album> albums = albumService.getAlbums();
            
            String albumList = null;
            try {
                albumList = objMapper.writeValueAsString(albums);
            } catch (JsonProcessingException e) {
                
            }
            
            if ( albumList != null ) {
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json");
                //resp.setContentLength(albumList.length()+3);
                resp.getWriter().write(albumList);
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
        NewAlbum newAlbum = null;
        
        try {
            newAlbum = objMapper.readValue(body, NewAlbum.class);
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
        
        //boolean albumAdded = albumService.addAlbum(newAlbum);
        
        if ( true ) {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            resp.setStatus(422);
        }
    }
}
