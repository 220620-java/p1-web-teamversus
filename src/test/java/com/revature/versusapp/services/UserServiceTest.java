package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

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
        Person mockPerson = new Person();
        mockPerson.setUsername("pslyde0");
        mockPerson.setPassword("x1Uul6CETGe");
        
        Mockito.when(dbORM.create(any(Person.class))).thenReturn(mockPerson);
        
        Person returnedPerson = userServ.tryToRegister("abbraddon12", "x1Uul6CETGe","Brad","Lastname");
        
        assertNotNull(returnedPerson);
    }
    
    @Test
    void testRegisterInvalidUser() {
        Mockito.when(dbORM.create(any(Person.class))).thenReturn(null);
        
        Person returnedPerson = userServ.tryToRegister("abbraddon12", "x1Uul6CETGe","Brad","Lastname");

    	assertNull(returnedPerson);
    }
    
    @Test
    void testLoginValidUser() {
    	Person mockPerson = new Person();
    	mockPerson.setUsername("pslyde0");
    	mockPerson.setPassword("x1Uul6CETGe");
    	List<Object> people = new ArrayList<>(1);
    	people.add(mockPerson);
    	
    	Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(people);
    	
    	mockPerson = userServ.login("pslyde0", "x1Uul6CETGe");
    	
    	assertNotNull(mockPerson);
    }
    
    @Test 
    void testLoginInvalidUsername() {
        Person mockPerson = new Person();
        mockPerson.setUsername("pslyde0");
        mockPerson.setPassword("x1Uul6CETGe");
        List<Object> people = new ArrayList<>(1);
        people.add(mockPerson);
        
        Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(people);
        
        mockPerson = userServ.login("abbraddon12", "x1Uul6CETGe");
        
        assertNull(mockPerson);
    }
    
    @Test
    void testLoginInvalidPassword() {
        Person mockPerson = new Person();
        mockPerson.setUsername("pslyde0");
        mockPerson.setPassword("x1Uul6CETGe");
        List<Object> people = new ArrayList<>(1);
        people.add(mockPerson);
        
        Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(people);
        
        mockPerson = userServ.login("pslyde0", "ngjkrefbngkjfbnf");
        
        assertNull(mockPerson);
    }
    
    @Test
    void testGetPeople() {
        Person mockPerson = new Person();
        mockPerson.setUsername("pslyde0");
        mockPerson.setPassword("x1Uul6CETGe");
        List<Object> people = new ArrayList<>(1);
        people.add(mockPerson);
        
        Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(people);
        
        List<Person> returnedList = userServ.getPeople();
        
        assertTrue(returnedList.size() == 1);
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
