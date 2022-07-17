package com.revature.versusapp.services;

import java.lang.reflect.Field;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Person;

public class UserService { 
    private ORM dbORM;
    
    UserService() {
        dbORM = new ORM();
    }
    
    public Person login(String username, String password) {
    	Person person = new Person();
    	List<Object> allAccounts = dbORM.findAll(person);
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
    	return person;
    }
    
    public Person register(String username, String password) {
        //This is totally wrong and needs to be made to work.
        Person newPerson = new Person();
        newPerson.setId(3);
        
        newPerson = (Person) dbORM.findById(newPerson);
        
        return newPerson;
    }
}
