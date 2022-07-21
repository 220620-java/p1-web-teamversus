package com.revature.versusapp.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.versusapp.data.ORM;
import com.revature.versusapp.models.ApiKey;
import com.revature.versusapp.models.Person;

public class ApiKeyService {
	private static ORM dbORM;
	
	static {
	    dbORM = new ORM();
	}
	
	public boolean addToApiKeyTable(Person person, String keyString) {
		ApiKey apikey = new ApiKey(keyString, person.getId());
		
		apikey = (ApiKey) dbORM.create(apikey);
		
		if(apikey.equals(null)) {
			return false;
		}
		return true;
	}
	
	public boolean keysMatch(Person person, String key) {
		Boolean match = false;
		List<Object> allApiKeys = new ArrayList();
		allApiKeys = dbORM.findAll(ApiKey.class);
		
		System.out.println("looking fir: " + person.getId() + " " + key);
		System.out.println(allApiKeys.size() + " items");
		
		for(Object object : allApiKeys) {
			ApiKey apiKey = (ApiKey) object;
			System.out.println("looking at: " + apiKey.getPerson_id() + " " + apiKey.getApikey_id() );
			if (apiKey.getPerson_id() == person.getId()) {
				if (apiKey.getApikey_id().equals(key)) {
					match = true;
				}
			}
		}
		return match;
	}
}
