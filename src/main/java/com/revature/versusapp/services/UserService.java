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
    
    public Person login(String username, String password) {
        Person foundPerson = new Person();
    	List<Object> allAccounts = dbORM.findAll(Person.class);
    	
    	
    	for ( Object object : allAccounts ) {
    	    Person personObject = (Person) object;
    	    
    	    if ( personObject.getUsername().equals(username) ) {
    	        if ( personObject.getPassword().equals(password) ) {
    	            foundPerson = personObject;
    	            break;
                }
    	        
    	        break;
    	    }
    	}
    	
    	return foundPerson;
    }
    
    public Person tryToRegister(String username, String passwrd, String firstName, String lastName) {
        //This is totally wrong and needs to be made to work.
    	Person person = new Person(username, passwrd, firstName, lastName);
    	
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
