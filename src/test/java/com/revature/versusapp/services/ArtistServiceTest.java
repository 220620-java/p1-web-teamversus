package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

        Mockito.when(dbORM.create(any(Artist.class))).thenReturn(mockArtist);
        
        boolean artistAdded = artistServ.addArtist(mockArtist);
        
        assertTrue(artistAdded);
    }
    
    @Test
    void testAddInvalidArtist() {
        Artist mockArtist = new Artist("mockArtist");

        Mockito.when(dbORM.create(any(Artist.class))).thenReturn(null);
        
        boolean artistAdded = artistServ.addArtist(mockArtist);
        
        assertFalse(artistAdded);
    }
    
    @Test
    void testGetValidArtist() {
        Artist mockArtist = new Artist("mockArtist");

        Mockito.when(dbORM.findById(any(Artist.class))).thenReturn(mockArtist);
        
        Artist returnedArtist = artistServ.getArtistById(mockArtist);
        
        assertNotNull(returnedArtist);
    }
    
    @Test
    void testGetInvalidArtist() {
        Artist mockArtist = new Artist("mockArtist");

        Mockito.when(dbORM.findById(any(Artist.class))).thenReturn(null);
        
        Artist returnedArtist = artistServ.getArtistById(mockArtist);
        
        assertNull(returnedArtist);
    }

    @Test
    void testGetArtists() {

        List<Object> mockArtists = new ArrayList<>(2);
        mockArtists.add(new Artist(999));
        mockArtists.add(new Artist(844));
        
        Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(mockArtists);
        
        List<Artist> returnedArtists = artistServ.getArtists();
        
        assertTrue(returnedArtists.size() > 0);
    }
}
