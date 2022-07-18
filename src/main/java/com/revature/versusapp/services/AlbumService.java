package com.revature.versusapp.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Artist;
import com.revature.versusapp.models.rest.NewAlbum;

public class AlbumService {
	private static ORM dbORM;
	
	AlbumService() {
        dbORM = new ORM();
    }
	
	public boolean addAlbum(Album newAlbum) {
		newAlbum = (Album) dbORM.create(newAlbum);
		if (newAlbum.equals(null)) {
			return false;
		}
		return true;
	}
	
	public static Album getAlbumById(Album album) {
		try {
			album = (Album) dbORM.findById(album);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return album;
	}
	
	public List<Album> getAlbums() {
		List<Object> findAllAlbums = dbORM.findAll(Album.class);
		List<Album> allAlbums = new ArrayList();
		int size = findAllAlbums.size();
		for (Object albums : findAllAlbums) {
			Album album = (Album) albums;
			allAlbums.add(album);
		}
		return allAlbums;
	}
	
	public void update(Album album) {
		dbORM.update(album);
	}
	
	public void delete(Album album) {
		dbORM.delete(album);
	}
}
