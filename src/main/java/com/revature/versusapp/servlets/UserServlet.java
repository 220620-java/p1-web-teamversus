package com.revature.versusapp.servlets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.Inventory;
import com.revature.versusapp.models.Person;
import com.revature.versusapp.models.rest.InventoryItem;
import com.revature.versusapp.models.rest.JsonAlbum;
import com.revature.versusapp.services.AlbumService;
import com.revature.versusapp.services.ArtistService;
import com.revature.versusapp.services.InventoryService;
import com.revature.versusapp.services.UserService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//our servlets should extend HttpServlet
public class UserServlet extends ErrorReportingHttpServlet {
    
    private ObjectMapper objMapper;
    private UserService userService;
    private InventoryService invService;
    private ArtistService artistService;
    AlbumService albumService;

    {
        userService = new UserService();
        objMapper = ObjectMapperUtil.getObjectMapper();
        invService = new InventoryService();
        artistService = new ArtistService();
        albumService = new AlbumService();
    }
     @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         
         StringBuffer url = req.getRequestURL();
         URL urlObject = new URL(url.toString());
         
         System.out.println("========");
         System.out.println(urlObject.getPath());
         StringTokenizer st = new StringTokenizer(urlObject.getPath(),"/");
//         while (st.hasMoreTokens()) {
//             System.out.println(st.nextToken());
//         }
     
         //The tokens should be:
         //1)  versusapi
         //2) user
         //3) a username or empty
         //4) inventory or empty
         
         String username = "";
         String invString = "";
         boolean hasError = false;
         
         
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
         
         if ( invString.length() >0 && !invString.equals("inventory") ) {
             hasError = true;
         }
         
         if ( hasError ) {
             writeErrorResponse(resp, 404, "Invalid request URL.");
             return;
         }
         
         List<Person> people = userService.getPeople();
         
         if ( username.length() > 0 ) {
             
             
             Person user = null;
             for ( Person person : people ) {
                 if ( person.getUsername().equals(username) ) {
                     user = person;
                     break;
                 }
                 

             }
             
             if ( user == null ) {
                 writeErrorResponse(resp, 404, "Invalid username URL.");
                 return;
             }

             List<Artist> artists = artistService.getArtists();
             HashMap<Integer, String> artistMap = new HashMap<>(artists.size());

             for (Artist artist : artists) {
                 artistMap.put(artist.getId(), artist.getStageName());
             }
             
             InventoryItem invItem = new InventoryItem();
             invItem.setUsername(user.getUsername());
             
             List<Inventory> inv = InventoryService.getInventories();

             for ( Inventory item : inv ) {
                 if (item.getPersonId() == user.getId() ) 
                 {
                     int albumId = item.getAlbumId();
                     String stagename = artistMap.get(albumId);
                     
                     Album album = new Album();
                     album.setId(albumId);
                     album = AlbumService.getAlbumById(album);
                     
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
         
         
         //List<Person> people = userService.getPeople();
         
         ArrayList<String> names = new ArrayList<>(people.size());
         
         for ( Person person : people ) {
             names.add(person.getUsername() );
         }
         
         resp.setCharacterEncoding("UTF-8");
         resp.setContentType("application/json");
         objMapper.writeValue(resp.getWriter(), names);
     }
}
