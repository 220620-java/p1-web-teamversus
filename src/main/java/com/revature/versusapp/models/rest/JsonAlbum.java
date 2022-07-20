package com.revature.versusapp.models.rest;

public class JsonAlbum {
    private String stagename;
    private int artistId;
    private String title;
    private int albumId;
    public String getStagename() {
        return stagename;
    }
    public void setStagename(String stagename) {
        this.stagename = stagename;
    }
    public int getArtistId() {
        return artistId;
    }
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getAlbumId() {
        return albumId;
    }
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
