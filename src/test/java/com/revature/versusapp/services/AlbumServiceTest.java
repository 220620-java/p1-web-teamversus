package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Inventory;
import com.revature.versusapp.models.Person;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {
	@InjectMocks
    private AlbumService albumServ = new AlbumService();
    
    @Mock
    private ORM dbORM;
    
    @Test
    void testAddValidAlbum() {
    	Album mockAlbum = new Album("Peaches", 5);
    	
    	Mockito.when(dbORM.create(any(Object.class))).thenReturn(mockAlbum);
    	
    	Boolean addMockAlbum = albumServ.addAlbum(mockAlbum);
    	
    	assertTrue(addMockAlbum);
    }
    
    @Test
    void testAddInvalidAlbum() {
    	//Artist_id 999 does not exist in the database
    	Album mockAlbum = new Album("Peaches", 999);
    	
    	Mockito.when(dbORM.create(any(Object.class))).thenReturn(null);
    	
    	Boolean addMockAlbum = albumServ.addAlbum(mockAlbum);
    	
    	assertFalse(addMockAlbum);
    }
    
    @Test
    void testGetAlbumById() {
        Album mockAlbum = new Album(1);
        Album albumWithTitle = new Album(1);
        albumWithTitle.setTitle("Album Title");
        
        Mockito.when(dbORM.findById(mockAlbum)).thenReturn(albumWithTitle);
        
        Album returnedAlbum = albumServ.getAlbumById(mockAlbum);
        
        // If the album was found, we should get back an object with more
        // information than was sent in.
        assertNotEquals(returnedAlbum,mockAlbum);
    }
    
    @Test
    void testBadGetAlbumById() {
        Album mockAlbum = new Album(1);

        Mockito.when(dbORM.findById(mockAlbum)).thenReturn(null);
        
        Album returnedAlbum = albumServ.getAlbumById(mockAlbum);
        
        assertNull(returnedAlbum);
    }
    
    @Test
    void testGetInvalidAlbumById() {
    	Album mockAlbum = new Album(999);
    	
    	Mockito.when(dbORM.findById(mockAlbum)).thenReturn(mockAlbum);
    	
    	Album returnedAlbum = albumServ.getAlbumById(mockAlbum);
    	
    	// If the album was not found, we should get back no new information
    	// than what was sent in.
    	assertEquals(returnedAlbum, mockAlbum);
    }
    
    @Test
    void testGetAlbums() {
    	List<Object> mockList = new ArrayList<>();
    	mockList.add(new Album(1));
    	
    	Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(mockList);
    	
    	List<Album> returnedList = albumServ.getAlbums();
    	
    	assertFalse(returnedList.isEmpty());
    }
    
    @Test
    void testDeleteAlbum() {
    	Album mockAlbum = new Album(5);

    	Mockito.doNothing().when(dbORM).delete(any(Album.class));
    	
    	assertDoesNotThrow(()->albumServ.delete(mockAlbum));
    }
    
    @Test
    void testUpdateAlbum() {
        Album mockAlbum = new Album(5);
        
        Mockito.doNothing().when(dbORM).update(any(Album.class));
        albumServ.update(mockAlbum);
        
        // verify that the ORM's delete method is called only once.
        verify(dbORM,times(1)).update(any(Album.class));
    }
}
