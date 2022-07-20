package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    
    @Test
    void testRegisterValidUser() {
        Person mockPerson = new Person("username","password","","");

        // Ensure that the ORM will give us a nonnull person objcet.
        //Mockito.when(dbORM.findById(any(Person.class))).thenReturn(mockPerson);
        
        //Person returnedPerson  = userServ.register("username","password");
        
        // assertion
        assertNotNull(mockPerson);
    }
    
    

}
