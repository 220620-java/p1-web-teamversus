package com.revature.versusapp.models;

import com.revature.versusapp.data.PrimaryKey;

@PrimaryKey(name={"id"})
public class Inventory {
    private int id;
    private int personId;
    private int albumId;
    
    public Inventory() {
		super();
	}

	public Inventory(int personId, int albumId) {
        this.personId = personId;
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
