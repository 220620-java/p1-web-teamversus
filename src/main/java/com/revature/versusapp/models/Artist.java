package com.revature.versusapp.models;

public class Artist {
    private int id;
    private String stageName;
    
    public Artist(String stageName) {
        this.id = id;
        this.stageName = stageName;
    }
    
    public Artist(int id, String stageName) {
        this.id = id;
        this.stageName = stageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
}
