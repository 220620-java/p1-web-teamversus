package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.revature.versusapp.models.ApiKey;
import com.revature.versusapp.models.Person;

@ExtendWith(MockitoExtension.class)
public class ApiKeyServiceTest {
	@InjectMocks
    private ApiKeyService apiKeyServ = new ApiKeyService();
    
    @Mock
    private ORM dbORM;
    
    @Test
    void testAddApiKey() {
    	Person mockPerson = new Person(5,"lzanini4", "7iFyUp3", "Les", "Zanini");
    	String apiKey = "xxxx-yyyy-zzzz";
    	
    	ApiKey mockKey = new ApiKey();
    	mockKey.setPerson_id(1);
    	mockKey.setApikey_id(apiKey);
    	
    	
    	Mockito.when(dbORM.create(any(ApiKey.class))).thenReturn(mockKey);
    	
    	Boolean addedApiKey = apiKeyServ.addToApiKeyTable(mockPerson, apiKey);
    	
    	assertTrue(addedApiKey);
    }
    
    @Test
    void testAddApiKeyFailure() {
        Person mockPerson = new Person(5,"lzanini4", "7iFyUp3", "Les", "Zanini");
        String apiKey = "xxxx-yyyy-zzzz";
        
        Mockito.when(dbORM.create(any(ApiKey.class))).thenReturn(null);
        
        Boolean addedApiKey = apiKeyServ.addToApiKeyTable(mockPerson, apiKey);
        
        assertFalse(addedApiKey);
    }
    
    @Test
    void testGoodKeyMatch() {
        Person mockPerson = new Person(5,"lzanini4", "7iFyUp3", "Les", "Zanini");
        String apiKey = "xxxx-yyyy-zzzz";
        
        ApiKey mockKey = new ApiKey();
        mockKey.setPerson_id(mockPerson.getId());
        mockKey.setApikey_id(apiKey);
        
        List<Object> mockKeyList = new ArrayList<>(1);
        mockKeyList.add(mockKey);
        
        Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(mockKeyList);
        
        boolean matchCheck = apiKeyServ.keysMatch(mockPerson, apiKey);
        
        assertTrue(matchCheck);
    }
    
    @Test
    void testBadKeyMatch() {
        Person mockPerson = new Person(5,"lzanini4", "7iFyUp3", "Les", "Zanini");
        String providedApiKey = "xxxx-yyyy-zzzz";
        String storedApiKey = "zzzz-yyyy-xxxx";
        
        ApiKey mockKey = new ApiKey();
        mockKey.setPerson_id(mockPerson.getId());
        mockKey.setApikey_id(storedApiKey);
        
        List<Object> mockKeyList = new ArrayList<>(1);
        mockKeyList.add(mockKey);
        
        Mockito.when(dbORM.findAll(any(Class.class))).thenReturn(mockKeyList);
        
        boolean matchCheck = apiKeyServ.keysMatch(mockPerson, providedApiKey);
        
        assertFalse(matchCheck);
    }

}
