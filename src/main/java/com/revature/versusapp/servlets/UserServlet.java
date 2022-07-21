package com.revature.versusapp.servlets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.Inventory;
import com.revature.versusapp.models.Person;
import com.revature.versusapp.models.rest.AlbumId;
import com.revature.versusapp.models.rest.InventoryItem;
import com.revature.versusapp.models.rest.JsonAlbum;
import com.revature.versusapp.models.rest.JsonPerson;
import com.revature.versusapp.services.AlbumService;
import com.revature.versusapp.services.ApiKeyService;
import com.revature.versusapp.services.ArtistService;
import com.revature.versusapp.services.InventoryService;
import com.revature.versusapp.services.UserService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class ParseResult {
    private String username;
    boolean inventoryRequest;
    boolean hasError;
    
    public boolean isInventoryRequest() {
        return inventoryRequest; 
    };
    
    public String getUsername() {
        return username; 
    };
    
    public boolean parseSuccess() {
        return !hasError; 
    };
    
    boolean hasUser() {
        return username.length() > 0;
    }
    
    ParseResult(String url) {
        
        StringTokenizer st = new StringTokenizer(url, "/");

        //The tokens should be:
        //1)  versusapi
        //2) user
        //3) a username or empty
        //4) inventory or empty
        
        username = "";
        String invString = "";
        hasError = false;
        inventoryRequest = false;
        
        st.nextToken();
        st.nextToken();
        
        if ( st.hasMoreTokens() ) {
            username = st.nextToken();
        }
        
        if ( st.hasMoreTokens() ) {
            invString = st.nextToken();
        }
        
        if ( st.hasMoreTokens() ) {
            hasError = true;
        }
        
        if ( invString.length() > 0 ) {
            if ( invString.equals("inventory") ) {
                inventoryRequest = true;
            }
            else {
                hasError = true;
            }
        }
    }
    
    
}


//our servlets should extend HttpServlet
public class UserServlet extends ErrorReportingHttpServlet {
    
    private ObjectMapper objMapper;
    private UserService userService;
    private InventoryService invService;
    private ArtistService artistService;
    private ApiKeyService apiKeyService;
    private AlbumService albumService;

    {
        userService = new UserService();
        objMapper = ObjectMapperUtil.getObjectMapper();
        invService = new InventoryService();
        artistService = new ArtistService();
        albumService = new AlbumService();
        apiKeyService = new ApiKeyService();
    }
    
    void inventoryGetRequest(Person user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Artist> artists = artistService.getArtists();
        HashMap<Integer, String> artistMap = new HashMap<>(artists.size());

        for (Artist artist : artists) {
            artistMap.put(artist.getId(), artist.getStageName());
            //System.out.println(artist.getId() + ":" + artist.getStageName());
        }
        
        InventoryItem invItem = new InventoryItem();
        invItem.setUsername(user.getUsername());
        
        List<Inventory> inv = invService.getInventories();

        for ( Inventory item : inv ) {
            if (item.getPersonId() == user.getId() ) 
            {
                //System.out.println(item.getPersonId() + ":" + item.getAlbumId());
                int albumId = item.getAlbumId();
                
                Album album = new Album();
                album.setId(albumId);
                album = albumService.getAlbumById(album);
                
                int artistId = album.getArtistId();
                String stagename = artistMap.get(artistId);
                
                
                JsonAlbum jalbum = new JsonAlbum();
                jalbum.setAlbumId(item.getAlbumId());
                jalbum.setArtistId(album.getArtistId());
                jalbum.setTitle(album.getTitle());
                jalbum.setStagename(stagename);
                
                invItem.getAlbums().add(jalbum);
                
            }
        }
        
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        objMapper.writeValue(resp.getWriter(), invItem);
    }
    void basicUserGet( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Person> people = userService.getPeople();
        
        ArrayList<String> names = new ArrayList<>(people.size());
        
        for ( Person person : people ) {
            names.add(person.getUsername() );
        }
        
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        objMapper.writeValue(resp.getWriter(), names);
    }
    
    private void getProfile(Person user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonPerson jperson = new JsonPerson();
        jperson.setUsername(user.getUsername());
        jperson.setFirstname(user.getFirstName());
        jperson.setLastname(user.getLastName());
        
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        objMapper.writeValue(resp.getWriter(), jperson);
    }
    
    private Person getPerson(String username) {
        List<Person> people = userService.getPeople();
        
        Person user = null;
        for ( Person person : people ) {
            if ( person.getUsername().equals(username) ) {
                user = person;
                break;
            }
        }
        
        return user;
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String apiKey = req.getHeader("versus-api-key");
        
        if ( apiKey == null ) {
            String message = "versus-api-key must be supplied in request header.";
            writeErrorResponse(resp, HttpServletResponse.SC_UNAUTHORIZED, message);
            return;
        }
        
        
        StringBuffer url = req.getRequestURL();
        URL urlObject = new URL(url.toString());
        ParseResult result = new ParseResult(urlObject.getPath());

        if ( !result.parseSuccess() || !result.hasUser() || !result.isInventoryRequest()) {
            writeErrorResponse(resp, 404, "Invalid request URL.");
            return;
        }
        
        Person user = getPerson(result.getUsername());
        
        if ( user == null ) {
            writeErrorResponse(resp, 404, "Invalid username URL.");
            return;
        }
        
        if ( !apiKeyService.keysMatch(user,apiKey) ) {
            writeErrorResponse(resp, HttpServletResponse.SC_FORBIDDEN, "You're not allowed to modify this user's inventory.");
            return;
        }
        
        // Attempt to deserialize the request body as a AlbumId object. Return
        // SC_BAD_REQUEST if unable to do so.
        boolean hasError = false;
        AlbumId albumId = null;
        int code =  HttpServletResponse.SC_OK;
        String errorMessage = "";

        try {
            albumId = objMapper.readValue(req.getReader(), AlbumId.class);
        } catch (JsonProcessingException e) {
            hasError = true;
            errorMessage = e.getMessage();
            code = HttpServletResponse.SC_BAD_REQUEST;
        }
        
        if (hasError) {
            writeErrorResponse(resp, code, errorMessage);
            return;
        }
        
        Album album = new Album();
        album.setId(albumId.getAlbumId());
        
        System.out.println("deleting album " + albumId.getAlbumId() + " from user " + user.getId());
        
        invService.deleteItem(user, album);
        
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String apiKey = req.getHeader("versus-api-key");
        
        if ( apiKey == null ) {
            String message = "versus-api-key must be supplied in request header.";
            writeErrorResponse(resp, HttpServletResponse.SC_UNAUTHORIZED, message);
            return;
        }
        
        
        StringBuffer url = req.getRequestURL();
        URL urlObject = new URL(url.toString());
        ParseResult result = new ParseResult(urlObject.getPath());

        if ( !result.parseSuccess() || !result.hasUser() || !result.isInventoryRequest()) {
            writeErrorResponse(resp, 404, "Invalid request URL.");
            return;
        }
        
        Person user = getPerson(result.getUsername());
        
        if ( user == null ) {
            writeErrorResponse(resp, 404, "Invalid username URL.");
            return;
        }
        
        if ( !apiKeyService.keysMatch(user,apiKey) ) {
            writeErrorResponse(resp, HttpServletResponse.SC_FORBIDDEN, "You're not allowed to modify this user's inventory.");
            return;
        }
        
        
        // Attempt to deserialize the request body as a AlbumId object. Return
        // SC_BAD_REQUEST if unable to do so.
        boolean hasError = false;
        AlbumId albumId = null;
        int code =  HttpServletResponse.SC_OK;
        String errorMessage = "";

        try {
            albumId = objMapper.readValue(req.getReader(), AlbumId.class);
        } catch (JsonProcessingException e) {
            hasError = true;
            errorMessage = e.getMessage();
            code = HttpServletResponse.SC_BAD_REQUEST;
        }
        
        if (hasError) {
            writeErrorResponse(resp, code, errorMessage);
            return;
        }
        
        Album album = new Album();
        album.setId(albumId.getAlbumId());
        
        System.out.println("adding album " + albumId.getAlbumId() + " to user " + user.getId());
        
        boolean added = invService.addItem(user,album);
        
        if ( !added ) {
            errorMessage = "This album is already in the user's inventory.";
            writeErrorResponse(resp, HttpServletResponse.SC_CONFLICT, errorMessage);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
    
     @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         
         StringBuffer url = req.getRequestURL();
         URL urlObject = new URL(url.toString());
         ParseResult result = new ParseResult(urlObject.getPath());

         if ( !result.parseSuccess() ) {
             writeErrorResponse(resp, 404, "Invalid request URL.");
             return;
         }
         
         
         if ( result.hasUser() ) {
             Person user = getPerson(result.getUsername());
             
             if ( user == null ) {
                 writeErrorResponse(resp, 404, "Invalid username URL.");
             }
             
             if ( result.isInventoryRequest() ) {
                 inventoryGetRequest(user, req,resp);
             }
             else {
                 getProfile(user, req,resp);
             }
         }
         else {
             basicUserGet(req,resp);
         }

     }
}
