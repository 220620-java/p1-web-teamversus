package com.revature.versusapp.servlets;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Person;
import com.revature.versusapp.models.rest.Credentials;
import com.revature.versusapp.models.rest.JsonRegistration;
import com.revature.versusapp.services.ApiKeyService;
import com.revature.versusapp.services.UserService;
import com.revature.versusapp.utils.ApiKeyUtil;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationServlet extends ErrorReportingHttpServlet{
    private ObjectMapper objMapper;
    private UserService userService;
    private ApiKeyService apikeyService;

    {
        userService = new UserService();
        objMapper = ObjectMapperUtil.getObjectMapper();
        apikeyService = new ApiKeyService();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Attempt to deserialize the request body as a JsonRegistration object.
        // Return SC_BAD_REQUEST if unable to do so.
        boolean hasError = false;
        JsonRegistration registration = null;
        int code =  HttpServletResponse.SC_OK;
        String errorMessage = "";

        try {
            registration = objMapper.readValue(req.getReader(), JsonRegistration.class);
        } catch (JsonProcessingException e) {
            hasError = true;
            errorMessage = e.getMessage();
            code = HttpServletResponse.SC_BAD_REQUEST;
        }

        if (hasError) {
            writeErrorResponse(resp, code, errorMessage);
            return;
        }

        Person person = userService.tryToRegister(registration.getUsername(),
                                                  registration.getPassword(),
                                                  registration.getFirstname(),
                                                  registration.getLastname());
        
        if ( person == null ) {
            errorMessage = "Username is already taken.";
            code = HttpServletResponse.SC_UNAUTHORIZED;
            writeErrorResponse(resp, code, errorMessage);
            return;
        }

        // Generate an apikey for this login and return it to the user.
        String key = ApiKeyUtil.generateApiKey();
        //authService.addToApiKeyTable(registration.getUsername(), key);
        apikeyService.addToApiKeyTable(person,key);
        Credentials credentials = new Credentials();
        credentials.setVersusApiKey(key);
        
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        objMapper.writeValue(resp.getWriter(), credentials);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
