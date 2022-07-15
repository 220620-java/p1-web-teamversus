package com.revature.versusapp.services;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Person;

public class UserService { 
    private ORM dbORM;
    
    UserService() {
        dbORM = new ORM();
    }
    
    public Person register(String username, String password) {
        //This is totally wrong and needs to be made to work.
        Person newPerson = new Person();
        newPerson.setId(3);
        
        newPerson = (Person) dbORM.findById(newPerson);
        
        return newPerson;
    }
}
