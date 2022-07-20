package com.revature.versusapp.models;

import com.revature.versusapp.data.PrimaryKey;

@PrimaryKey(name={"id"})
public class Artist {
    private int id;
    private String stageName;
    
    public Artist() {
		super();
	}
    

	public Artist(int id) {
		super();
		this.id = id;
	}


	public Artist(String stageName) {
        super();
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
