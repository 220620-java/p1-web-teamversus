package com.revature.versusapp.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Inventory;
import com.revature.versusapp.models.Person;

public class InventoryService {
	private static ORM dbORM;
	
	public InventoryService() {
        dbORM = new ORM();
    }
	
	public Boolean addItem(Person person, Album album) {
		Inventory newItem = new Inventory(person.getId(), album.getId());
		newItem = (Inventory) dbORM.create(newItem);
		if (newItem ==null ) {
			return false;
		}
		return true;
	}
	
	public void deleteItem(Person person, Album album) {
		Inventory item = new Inventory(person.getId(), album.getId());
		dbORM.delete(item);
	}
	
	public List<Inventory> getInventories() {
		List<Inventory> allInventories = new ArrayList();
		Inventory inventory = new Inventory();
		
		try {
			for (Object object : dbORM.findAll(Inventory.class)) {
				inventory = inventory.getClass().getConstructor().newInstance();
				inventory = (Inventory) object;
				allInventories.add(inventory);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return allInventories;
	}
	
	public List<Inventory> getInventoryByPersonId(Person person) {
		int personId = person.getId();
		List<Inventory> inventory = getInventories();
		for(Inventory item : inventory) {
			if (item.getPersonId() == personId) {
				inventory.add(item);
			}
		}
		return inventory;
	}
}
