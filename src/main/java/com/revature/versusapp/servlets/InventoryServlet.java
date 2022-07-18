package com.revature.versusapp.servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.rest.InventoryItem;
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
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<InventoryItem> items = inventoryService.getInventory();
        
        String inventory = null;
        try {
            inventory = objMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            
        }
        
        if ( inventory != null ) {
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
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doDelete(req, resp);
    }
}
