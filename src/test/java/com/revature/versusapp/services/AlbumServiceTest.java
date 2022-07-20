package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Album;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {
	@InjectMocks
    private AlbumService albumServ = new AlbumService();
    
    @Mock
    private ORM dbORM;
    
    @Test
    void testAddValidAlbum() {
    	Album mockAlbum = new Album("Peaches", 5);
    	
    	Boolean addMockAlbum = albumServ.addAlbum(mockAlbum);
    	
    	assertTrue(addMockAlbum);
    }
    
    @Test
    void testAddInvalidAlbum() {
    	//Artist_id 999 does not exist in the database
    	Album mockAlbum = new Album("Peaches", 999);
    	
    	Boolean addMockAlbum = albumServ.addAlbum(mockAlbum);
    	
    	assertFalse(addMockAlbum);
    }
    
    @Test
    void testGetAlbumById() {
    	Album mockAlbum = new Album(1);
    	
    	mockAlbum = albumServ.getAlbumById(mockAlbum);
    	
    	assertNotNull(mockAlbum);
    }
    
    @Test
    void testGetInvalidAlbumById() {
    	Album mockAlbum = new Album(999);
    	
    	mockAlbum = albumServ.getAlbumById(mockAlbum);
    	
    	assertNull(mockAlbum);
    }
    
    @Test
    void testGetAlbums() {
    	List<Album> allMockAlbums = albumServ.getAlbums();
    	
    	assertNotNull(allMockAlbums);
    }
    
    @Test
    void testDeleteAlbum() {
    	Album mockAlbum = new Album(5);
    	
    	albumServ.delete(mockAlbum);
    	
    	mockAlbum = albumServ.getAlbumById(mockAlbum);
    	
    	assertNull(mockAlbum);
    }
}
