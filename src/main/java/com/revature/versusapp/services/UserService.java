package com.revature.versusapp.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Inventory;
import com.revature.versusapp.models.Person;
import com.revature.versusapp.models.rest.User;

public class UserService { 
    private ORM dbORM;
    private AlbumService albumService = new AlbumService();
    
    UserService() {
        dbORM = new ORM();
    }
    
    public User login(String username, String password) {
    	Person person = new Person();
    	List<Object> allAccounts = dbORM.findAll(Person.class);
    	Field field;
    	Boolean found = false;
		try {
			field = person.getClass().getDeclaredField("username");
			for (Object object : allAccounts) {
	    		if (field.get(object).equals(username)) {
	    			Field passwordField = person.getClass().getDeclaredField("passwrd");
	    			if(passwordField.get(object).equals(password)) {
	    				found = true;
		    			person = (Person) object;
	    			} else {
	    				System.out.println("Password is incorrect");
	    				return null;
	    			}
	    		}
	    	}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (found) {
			System.out.println("username not found");
			return null;
		}
		List<Inventory> inventory = InventoryService.getInventoryByPersonId(person);
		List<Album> userInventory = new ArrayList();
		try {
			for (Inventory item : inventory) {
				Album album = new Album(item.getAlbumId());
				album = (Album) dbORM.findById(album);
				userInventory.add(album);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = new User(person, userInventory);
    	return user;
    }
    
    public Person tryToRegister(String username, String passwrd, String firstName, String lastName) {
        //This is totally wrong and needs to be made to work.
    	Person person = new Person(username, passwrd, firstName, lastName);
    	List<Object> allAccounts = dbORM.findAll(Person.class);
    	
    	person = (Person) dbORM.create(person);
        return person;
    }
    
    public void delete(Person person) {
    	dbORM.delete(person);
    }
    
    public void update(Person person) {
    	dbORM.update(person);
    }
}
