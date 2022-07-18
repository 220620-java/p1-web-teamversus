package com.revature.versusapp.servlets;

import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.rest.Credentials;
import com.revature.versusapp.models.rest.Login;
import com.revature.versusapp.services.ersatz.ErsatzAlbumService;
import com.revature.versusapp.services.ersatz.ErsatzUserService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    private ObjectMapper objMapper;
    private ErsatzAlbumService albumService;
    
    {
        albumService = new ErsatzAlbumService();
        objMapper = ObjectMapperUtil.getObjectMapper();
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        ObjectMapper objMapper = ObjectMapperUtil.getObjectMapper();
        ErsatzUserService userService = new ErsatzUserService();
        
        // Attempt to deserialize the request body as a Login object. Return
        // SC_BAD_REQUEST if unable to do so.
        String body = req.getReader()
                         .lines()
                         .collect(Collectors.joining(System.lineSeparator()));
        boolean hasError = false;
        String errorString = "";
        Login login = null;
        
        try {
            login = objMapper.readValue(body, Login.class);
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
        
        Credentials credentials = userService.tryToLogin(login);
        
        if ( credentials == null || credentials.getVersusApiKey().length() == 0 ) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        String serialCredential = objMapper.writeValueAsString(credentials);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(serialCredential);
    }
}
