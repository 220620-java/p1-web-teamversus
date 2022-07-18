package com.revature.versusapp.models.rest;

import java.util.ArrayList;
import java.util.List;

public class InventoryItem {
    private String username;
    List<NewAlbum> albums;
    
    {
        albums = new ArrayList<NewAlbum>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<NewAlbum> getAlbums() {
        return albums;
    }

    public void setAlbums(List<NewAlbum> albums) {
        this.albums = albums;
    }
}
