package com.revature.versusapp.models;

public class Album {

    private int id;
    private String title;
    private int artistId;
    
    public Album(String title, int artistId) {
        this.id = -1;
        this.title = title;
        this.artistId = artistId;
    }
    
    public Album(int id, String title, int artistId) {
        this.id = -1;
        this.title = title;
        this.artistId = artistId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
}
