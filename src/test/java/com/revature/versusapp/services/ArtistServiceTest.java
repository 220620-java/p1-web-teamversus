package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Artist;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {
	@InjectMocks
    private ArtistService artistServ = new ArtistService();
    
    @Mock
    private ORM dbORM;
    
    @Test
    void testAddValidArtist() {
    	Artist mockArtist = new Artist("mockArtist");
    	
    	Boolean addMockArtist = artistServ.addArtist(mockArtist);
    	
    	assertTrue(addMockArtist);
    }
    
    @Test
    void testGetValidArtist() {
    	Artist mockArtist = new Artist(5);
    	
    	mockArtist = artistServ.getArtistById(mockArtist);
    	
    	assertNotNull(mockArtist);
    }
    
    @Test
    void testGetInvalidArtist() {
    	Artist mockArtist = new Artist(999);
    	
    	mockArtist = artistServ.getArtistById(mockArtist);
    	
    	assertNotNull(mockArtist);
    }
    
    @Test
    void testGetAllArtists() {
    	List<Artist> mockAllArtists = artistServ.getArtists();
    	
    	assertNotNull(mockAllArtists);
    }
    
    @Test
    void testDeleteArtist() {
    	Artist mockArtist = new Artist(5);
    	
    	artistServ.delete(mockArtist);
    	
    	mockArtist = artistServ.getArtistById(mockArtist);
    	
    	assertNull(mockArtist);
    }
    
    
}
