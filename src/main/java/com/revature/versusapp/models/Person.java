package com.revature.versusapp.models;

import com.revature.versusapp.data.PrimaryKey;

import java.util.Objects;
@PrimaryKey(name={"id"})
public class Person {
    private int id;
    private String username;
    private String passwrd;
    private String firstName;
    private String lastName;
    
    public Person() {
        super();
    }
    public Person(int id) {
        super();
        this.id = id;
    }
    public Person(String username, String password, String firstName, String lastName) {
        super();
        this.username = username;
        this.passwrd = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Person(int id, String username, String password, String firstName, String lastName) {
        super();
        this.id = id;
        this.username = username;
        this.passwrd = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return passwrd;
    }
    public void setPassword(String password) {
        this.passwrd = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName, id, lastName, passwrd, username);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName)
                && Objects.equals(passwrd, other.passwrd) && Objects.equals(username, other.username);
    }
    @Override
    public String toString() {
        return "Person [id=" + id + ", username=" + username + ", password=" + passwrd + ", firstName=" + firstName
                + ", lastName=" + lastName + "]";
    }
}