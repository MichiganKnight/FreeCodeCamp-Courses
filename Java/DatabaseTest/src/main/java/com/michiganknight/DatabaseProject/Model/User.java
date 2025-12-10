package com.michiganknight.DatabaseProject.Model;

import java.sql.Date;

public class User {
    int id;

    String firstName;
    String lastName;
    String email;
    String username;
    String password;

    Date dateCreated;

    public User() {}

    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String username, String password, Date dateCreated) {
        this(firstName, lastName, email, username, password);
        this.dateCreated = dateCreated;
    }

    public User(String fullName, String email, String username, String password) {
        this(fullName.split(" ")[0], fullName.split(" ")[1], email, username, password);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "{ User: " + String.format("%s %s (%s) %s", firstName, lastName, email, dateCreated) + " }\n";
    }
}
