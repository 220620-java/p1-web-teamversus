package com.revature.versusapp.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Artist;

public class ArtistService {
	private ORM dbORM;
	
	ArtistService() {
	    dbORM = new ORM();
	}
	
	public boolean addArtist(Artist newArtist) {
		newArtist = (Artist) dbORM.create(newArtist);
		if (newArtist.equals(null)) {
			return false;
		}
		return true;
	}
	
	public List<Artist> getArtists() {
		List<Object> findAllArtists = dbORM.findAll(Artist.class);
		List<Artist> allArtists = new ArrayList();
		int size = findAllArtists.size();
		for (Object artist : findAllArtists) {
			allArtists.add((Artist) artist);
		}
		return allArtists;
	}
	
	public void update(Artist artist) {
		dbORM.update(artist);
	}
	
	public void delete(Artist artist) {
		dbORM.delete(artist);
	}
}
