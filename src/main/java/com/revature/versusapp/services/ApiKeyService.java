package com.revature.versusapp.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.ApiKey;
import com.revature.versusapp.models.Person;

public class ApiKeyService {
	private static ORM dbORM;
	
	public boolean addToApiKeyTable(Person person, String key) {
		ApiKey apikey = new ApiKey(key, person.getId());
		
		apikey = (ApiKey) dbORM.create(key);
		
		if(apikey.equals(null)) {
			return false;
		}
		return true;
	}
	
	public boolean keysMatch(Person person, String key) {
		Boolean match = false;
		List<Object> allApiKeys = new ArrayList();
		allApiKeys = dbORM.findAll(ApiKey.class);
		
		for(Object object : allApiKeys) {
			ApiKey apiKey = (ApiKey) object;
			if (apiKey.getPerson_id() == person.getId()) {
				if (apiKey.getApikey_id().equals(key)) {
					match = true;
				}
			}
		}
		return match;
	}
}
