package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Person;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userServ = new UserService();
    
    @Mock
    private ORM dbORM;
    
    @Test
    void testRegisterValidUser() {
        Person mockPerson = userServ.tryToRegister("username","password","Sierra","Nicholes");

        // Ensure that the ORM will give us a nonnull person objcet.
        Mockito.when(dbORM.findById(any(Person.class))).thenReturn(mockPerson);
        
        assertNotNull(mockPerson);
    }
    
    @Test
    void testRegisterInvalidUser() {
    	Person mockPerson = userServ.tryToRegister("pslyde0", "password", "duplicate", "username");
    	
    	Mockito.when(dbORM.create(any(Person.class))).thenReturn(mockPerson);
    	
    	mockPerson = userServ.tryToRegister(mockPerson.getUsername(), mockPerson.getPassword(), mockPerson.getFirstName(), mockPerson.getLastName());
    	
    	assertNull(mockPerson);
    }
    
    @Test
    void testLoginValidUser() {
    	Person mockPerson = new Person();
    	
    	Mockito.when(dbORM.create(any(Person.class))).thenReturn(mockPerson);
    	
    	mockPerson = userServ.login("pslyde0", "x1Uul6CETGe");
    	
    	assertNotNull(mockPerson);
    }
    
    @Test 
    void testLoginInvalidUsername() {
    	Person mockPerson = new Person();
    	
    	Mockito.when(dbORM.create(any(Person.class))).thenReturn(mockPerson);
    	
    	mockPerson = userServ.login("wrong", "username");
    	
    	assertNull(mockPerson);
    }
    
    @Test
    void testLoginInvalidPassword() {
    	Person mockPerson = new Person();
    	
    	Mockito.when(dbORM.create(any(Person.class))).thenReturn(mockPerson);
    	
    	mockPerson = userServ.login("pslyde0", "wrong password");
    	
    	assertNull(mockPerson);
    }
    
//    @Test
//    void testDeleteUser() {
//    	Person mockPerson = new Person(5);
//    	
//    	userServ.delete(mockPerson);
//    	
//    	Boolean loggedIn = userServ.login("lzanini4", "7iFyUp3");
//    	
//    	assertFalse(loggedIn);
//    }

}
