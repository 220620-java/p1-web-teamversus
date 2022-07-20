package com.revature.versusapp.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.rest.JsonAlbum;
import com.revature.versusapp.models.rest.NewAlbum;
import com.revature.versusapp.services.AlbumService;
import com.revature.versusapp.services.ArtistService;
import com.revature.versusapp.utils.ObjectMapperUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AlbumServlet extends ErrorReportingHttpServlet {
    private ObjectMapper objMapper;
    private AlbumService albumService;
    private ArtistService artistService;

    {
        artistService = new ArtistService();
        albumService = new AlbumService();
        objMapper = ObjectMapperUtil.getObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Artist> artists = artistService.getArtists();
        HashMap<Integer, String> artistMap = new HashMap<>(artists.size());

        for (Artist artist : artists) {
            artistMap.put(artist.getId(), artist.getStageName());
        }

        List<Album> albums = albumService.getAlbums();
        List<JsonAlbum> jsonAlbums = new LinkedList<>();

        for (Album album : albums) {
            int id = album.getArtistId();
            String stagename = artistMap.get(id);

            JsonAlbum jsonalbum = new JsonAlbum();
            jsonalbum.setStagename(stagename);
            jsonalbum.setArtistId(id);
            jsonalbum.setAlbumId(album.getId());
            jsonalbum.setTitle(album.getTitle());

            jsonAlbums.add(jsonalbum);
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        objMapper.writeValue(resp.getWriter(), jsonAlbums);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Attempt to deserialize the request body as a NewAlbum object. Return
        // SC_BAD_REQUEST if unable to do so.
        boolean hasError = false;
        NewAlbum newAlbum = null;
        int code =  HttpServletResponse.SC_OK;
        String errorMessage = "";

        try {
            newAlbum = objMapper.readValue(req.getReader(), NewAlbum.class);
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
        album.setTitle(newAlbum.getTitle());
        album.setArtistId(newAlbum.getArtistId());
        boolean albumAdded = albumService.addAlbum(album);

        if (albumAdded) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            errorMessage = "Unable to add album to the database,";
            writeErrorResponse(resp, 422, errorMessage);
        }
    }
}
