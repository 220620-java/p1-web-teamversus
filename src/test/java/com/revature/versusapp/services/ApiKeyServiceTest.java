package com.revature.versusapp.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.Person;
import com.revature.versusapp.utils.ApiKeyUtil;

@ExtendWith(MockitoExtension.class)
public class ApiKeyServiceTest {
	@InjectMocks
    private ApiKeyService apiKeyServ = new ApiKeyService();
    
    @Mock
    private ORM dbORM;
    
//    @Test
//    void testAddApiKey() {
//    	Person mockPerson = new Person(5,"lzanini4", "7iFyUp3", "Les", "Zanini");
//    	String apiKey = ApiKeyUtil.generateApiKey();
//    	
//    	Boolean addedApiKey = apiKeyServ.addToApiKeyTable(mockPerson, apiKey);
//    	
//    	assertTrue(addedApiKey);
//    }

}
