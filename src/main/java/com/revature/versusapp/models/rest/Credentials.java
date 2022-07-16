package com.revature.versusapp.models.rest;

public class Credentials {
    private int id;
    private int token;
    
    public Credentials(int id, int token) {
        this.id = id;
        this.token = token;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getToken() {
        return token;
    }
    public void setToken(int token) {
        this.token = token;
    }
}
