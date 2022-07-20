package com.revature.versusapp.models;

public class ApiKey {
	private String apikey_id;
	private int person_id;
	
	
	public ApiKey(String apikey_id, int person_id) {
		super();
		this.apikey_id = apikey_id;
		this.person_id = person_id;
	}
	public String getApikey_id() {
		return apikey_id;
	}
	public void setApikey_id(String apikey_id) {
		this.apikey_id = apikey_id;
	}
	public int getPerson_id() {
		return person_id;
	}
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	@Override
	public String toString() {
		return "ApiKey [apikey_id=" + apikey_id + ", person_id=" + person_id + "]";
	}
	
	
}
