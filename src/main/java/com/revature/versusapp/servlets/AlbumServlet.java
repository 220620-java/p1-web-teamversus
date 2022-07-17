package com.revature.versusapp.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.services.ersatz.ErsatzArtistService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AlbumServlet extends HttpServlet{
    private ObjectMapper objMapper;
    
    {
        objMapper = ObjectMapperUtil.getObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
}
