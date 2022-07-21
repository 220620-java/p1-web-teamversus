package com.revature.versusapp.services;

import java.util.LinkedList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Person;

public class UserService { 
    private ORM dbORM;
    private AlbumService albumService = new AlbumService();
    
    public UserService() {
        dbORM = new ORM();
    }
    
    public List<Person> getPeople() {
        List<Object> findAllPeople = dbORM.findAll(Person.class);
        List<Person> allPeople = new LinkedList<>();
        for (Object person : findAllPeople) {
            allPeople.add((Person) person);
        }
        return allPeople;
    }
    
    public boolean login(String username, String password) {
    	//Person person = new Person();
        boolean successfulLogin = false;
    	List<Object> allAccounts = dbORM.findAll(Person.class);
    	
    	for ( Object object : allAccounts ) {
    	    Person personObject = (Person) object;
    	    if ( personObject.getUsername().equals(username) ) {
    	        if ( personObject.getPassword().equals(password) ) {
    	            successfulLogin = true;
                }
    	        
    	        break;
    	    }
    	}
    	
    	return successfulLogin;
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
