package com.revature.versusapp.models.rest;

import java.util.List;
import java.util.Objects;

import com.revature.versusapp.models.Album;
import com.revature.versusapp.models.Person;

public class User {
	private Person person;
	private List<Album> inventory;
	
	
	public User() {
		super();
	}
	public User(Person person, List<Album> inventory) {
		super();
		this.person = person;
		this.inventory = inventory;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<Album> getInventory() {
		return inventory;
	}
	public void setInventory(List<Album> inventory) {
		this.inventory = inventory;
	}
	@Override
	public String toString() {
		return "User [person=" + person + ", inventory=" + inventory + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(inventory, person);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(inventory, other.inventory) && Objects.equals(person, other.person);
	}
	
	
}
