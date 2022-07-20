package com.revature.versusapp.models.rest;

import java.util.LinkedList;
import java.util.List;

public class InventoryItem {
    private String username;
    List<JsonAlbum> albums;
    
    {
        albums = new LinkedList<JsonAlbum>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<JsonAlbum> getAlbums() {
        return albums;
    }

    public void setAlbums(List<JsonAlbum> albums) {
        this.albums = albums;
    }
}
