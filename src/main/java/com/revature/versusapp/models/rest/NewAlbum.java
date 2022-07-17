package com.revature.versusapp.models.rest;

public class NewAlbum {
    private String title;
    private int artistId;

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
