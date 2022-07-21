package com.revature.versusapp.services;

import java.util.LinkedList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Person;

public class UserService { 
    private ORM dbORM;
    
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
    
    /**
     * Check if the there is an entry in the Person table with this 
     * username and password.
     * 
     * @param username
     * @param password
     * @return A Person object with the information from the Person table
     *         if there was a matching entry. If there was no matching entry
     *         null will be returned.
     */
    public Person login(String username, String password) {
        Person foundPerson = null;
    	List<Object> allAccounts = dbORM.findAll(Person.class);
    	
    	for ( Object object : allAccounts ) {
    	    Person personObject = (Person) object;
    	    
    	    if ( personObject.getUsername() != null && personObject.getUsername().equals(username) ) {
    	        if ( personObject.getPassword() != null && personObject.getPassword().equals(password) ) {
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
