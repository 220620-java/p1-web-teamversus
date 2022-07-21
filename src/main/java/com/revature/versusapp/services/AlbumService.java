package com.revature.versusapp.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Artist;

public class AlbumService {
	private ORM dbORM;
	
	public AlbumService() {
	    dbORM = new ORM();
    }
	
	public boolean addAlbum(Album newAlbum) {
		newAlbum = (Album) dbORM.create(newAlbum);
		if ( newAlbum == null ) {
			return false;
		}
		return true;
	}
	
	public Album getAlbumById(Album album) {
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
			Album album;
			try {
                album = Album.class.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

			album = (Album) albums;
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
