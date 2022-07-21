package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Person;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userServ = new UserService();
    
    @Mock
    private ORM dbORM;
    
//    @Test
//    void testRegisterValidUser() {
//        Person mockPerson = userServ.tryToRegister("username","password","Sierra","Nicholes");
//
//        // Ensure that the ORM will give us a nonnull person objcet.
//        //Mockito.when(dbORM.findById(any(Person.class))).thenReturn(mockPerson);
//        
//        // assertion
//        assertNotNull(mockPerson);
//    }
    
//    @Test
//    void testRegisterInvalidUser() {
//    	Person mockPerson = userServ.tryToRegister("pslyde0", "", "duplicate", "username");
//    	
//    	assertNotNull(mockPerson);
//    }
    
//    @Test
//    void testLoginValidUser() {
//    	Boolean loggedIn = userServ.login("pslyde0", "x1Uul6CETGe");
//    	
//    	assertTrue(loggedIn);
//    }
    
//    @Test 
//    void testLoginInvalidUsername() {
//    	Boolean loggedIn = userServ.login("wrong", "username");
//    	
//    	assertFalse(loggedIn);
//    }
//    
//    @Test
//    void testLoginInvalidPassword() {
//    	Boolean loggedIn = userServ.login("pslyde0", "wrong password");
//    	
//    	assertFalse(loggedIn);
//    }
//    
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
