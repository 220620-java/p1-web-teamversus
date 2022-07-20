package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    	Boolean addMockItem = inventoryServ.addItem(mockPerson, mockAlbum);
    	
    	assertTrue(addMockItem);
    }
    
    @Test
    void testAddInvalidItem1() {
    	// invalid Person
    	Person mockPerson = new Person(999);
    	Album mockAlbum = new Album(5);
    	Boolean addMockItem = inventoryServ.addItem(mockPerson, mockAlbum);
    	
    	assertFalse(addMockItem);
    }
    
    @Test
    void testAddInvalidItem2() {
    	// invalid album
    	Person mockPerson = new Person(5);
    	Album mockAlbum = new Album(999);
    	Boolean addMockItem = inventoryServ.addItem(mockPerson, mockAlbum);
    	
    	assertFalse(addMockItem);
    }
    
    @Test
    void testAddInvalidItem3() {
    	// person and album are invalid
    	Person mockPerson = new Person(999);
    	Album mockAlbum = new Album(999);
    	Boolean addMockItem = inventoryServ.addItem(mockPerson, mockAlbum);
    	
    	assertFalse(addMockItem);
    }
    
    @Test
    void testDeleteItem() {
    	Person mockPerson = new Person(5);
    	Album mockAlbum = new Album(5);
    	Boolean addMockItem = inventoryServ.addItem(mockPerson, mockAlbum);
    	inventoryServ.deleteItem(mockPerson, mockAlbum);
    	addMockItem = inventoryServ.addItem(mockPerson, mockAlbum);
    	
    	assertTrue(addMockItem);
    }
    
    @Test
    void testGetInventories() {
    	List<Inventory> mockInventories = inventoryServ.getInventories();
    	
    	assertNotNull(mockInventories);
    }
    
    @Test
    void testGetInventoriesByPersonId() {
    	Person mockPerson = new Person(5);
    	List<Inventory> mockInventories = inventoryServ.getInventoryByPersonId(mockPerson);
    	
    	assertNotNull(mockInventories);
    }
    

}
