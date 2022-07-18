package com.revature.versusapp.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.rest.InventoryItem;
import com.revature.versusapp.models.rest.JsonError;
import com.revature.versusapp.models.rest.NewAlbum;
import com.revature.versusapp.services.ersatz.ErsatzInventoryService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InventoryServlet extends HttpServlet{
    private ObjectMapper objMapper;
    private ErsatzInventoryService inventoryService;
    
    {
        objMapper = ObjectMapperUtil.getObjectMapper();
        inventoryService = new ErsatzInventoryService();
    }
    
    static void writeError(HttpServletResponse resp, int code, String message) {
        JsonError error = new JsonError();
        
        error.setCode(code);
        error.setMessage(message);
        
        String jsonMessage = "";
        
        try {
            jsonMessage = ObjectMapperUtil.getObjectMapper().writeValueAsString(error);
        } catch (JsonProcessingException e) {
            
        }
        
        resp.setStatus(code);
        try {
            resp.getWriter().write(jsonMessage);
        } catch (IOException e) {
        }
    }
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<InventoryItem> items = inventoryService.getInventory();
        
        String inventory = null;
        try {
            inventory = objMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            
        }
        
        if ( inventory != null ) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(inventory);
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
        NewAlbum album = null;
        
        try {
            album = objMapper.readValue(body, NewAlbum.class);
        } catch (JsonMappingException e) {
            hasError = true;
            errorString = e.getMessage();
        } catch (JsonProcessingException e) {
            hasError = true;
            errorString = e.getMessage();
        }
        
        if ( hasError ) {
            writeError(resp, HttpServletResponse.SC_BAD_REQUEST, errorString);
            return;
        }
        
        String apiKey = req.getHeader("versus-api-key");
        
        if ( apiKey == null ) {
            String message = "versus-api-key must be supplied in request headers";
            writeError(resp, HttpServletResponse.SC_BAD_REQUEST, message);
            return;
        }
        
        // need to be able to convert apiKey to a userid, for now:
        int userId = 20;
        
        
        // should also ensure that it's not already there.
        boolean albumAdded = inventoryService.addAblumToInventory(userId,album);
        
        if ( albumAdded ) {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Unable to add album to inventory.\n");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Attempt to deserialize the request body as a Login object. Return
        // SC_BAD_REQUEST if unable to do so.
        String body = req.getReader()
                         .lines()
                         .collect(Collectors.joining(System.lineSeparator()));
        boolean hasError = false;
        String errorString = "";
        NewAlbum album = null;
        
        try {
            album = objMapper.readValue(body, NewAlbum.class);
        } catch (JsonMappingException e) {
            hasError = true;
            errorString = e.getMessage();
        } catch (JsonProcessingException e) {
            hasError = true;
            errorString = e.getMessage();
        }
        
        if ( hasError ) {
            writeError(resp, HttpServletResponse.SC_BAD_REQUEST, errorString);
            return;
        }
        
        String apiKey = req.getHeader("versus-api-key");
        
        if ( apiKey == null ) {
            String message = "versus-api-key must be supplied in request headers";
            writeError(resp, HttpServletResponse.SC_BAD_REQUEST, message);
            return;
        }
        
        // need to be able to convert apiKey to a userid, for now:
        int userId = 20;
        
        
        // should also ensure that it's not already there.
        boolean albumDeleted = inventoryService.deleteAlbumFromInventory(userId,album);
        
        if ( albumDeleted ) {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Unable to add album to inventory.\n");
        }
    }
}
