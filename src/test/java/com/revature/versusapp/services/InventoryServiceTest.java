package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

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
public class InventoryServiceTest {
	@InjectMocks
    private InventoryService inventoryServ = new InventoryService();
    
    @Mock
    private ORM dbORM;
    
    @Test
    void testAddValidItem() {
    	Person mockPerson = new Person(5);
    	Album mockAlbum = new Album(5);
    	Inventory mockItem = new Inventory(5,5);
    	
    	ArrayList<Object> mockInventory= new ArrayList<>(1);
    	mockInventory.add(new Inventory(6,6));
    	
    	Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(mockInventory);
    	Mockito.when(dbORM.create(any(Inventory.class))).thenReturn(mockItem);
    	
    	boolean itemAdded = inventoryServ.addItem(mockPerson,mockAlbum);

    	assertTrue(itemAdded);
    }
    
    @Test
    void testAddAlreadyPresentItem() {
        Person mockPerson = new Person(5);
        Album mockAlbum = new Album(5);
        
        ArrayList<Object> mockInventory= new ArrayList<>(1);
        mockInventory.add(new Inventory(5,5));
        
        Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(mockInventory);
        boolean itemAdded = inventoryServ.addItem(mockPerson,mockAlbum);

        assertFalse(itemAdded);
    }
    
    @Test
    void testAddInvalidItem() {
        Person mockPerson = new Person(5);
        Album mockAlbum = new Album(5);
        
        ArrayList<Object> mockInventory= new ArrayList<>(1);
        
        Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(mockInventory);
        Mockito.when(dbORM.create(any(Inventory.class))).thenReturn(null);
        
        boolean itemAdded = inventoryServ.addItem(mockPerson,mockAlbum);

        assertFalse(itemAdded);
    }
    
    @Test
    void testDeleteItem() {
        Person mockPerson = new Person(5);
        Album mockAlbum = new Album(5);
        
        Mockito.doNothing().when(dbORM).delete(any(Inventory.class));
        inventoryServ.deleteItem(mockPerson, mockAlbum);
        
        // verify that the ORM's delete method is called only once.
        verify(dbORM,times(1)).delete(any(Inventory.class));
    }
}
