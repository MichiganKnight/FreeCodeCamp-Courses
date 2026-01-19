package com.revature.Model;

import java.util.Objects;

public class Chef {
    private int id;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;

    public Chef() {
    }

    public Chef(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Chef(int id, String username, String password, String email, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public Chef(String username, String password, String email, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
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
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Chef chef)) return false;
        return id == chef.id && username.equals(chef.username) && password.equals(chef.password) && email.equals(chef.email) && isAdmin == chef.isAdmin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, isAdmin);
    }

    public String toString() {
        return "Chef{ id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin + '}';
    }
}
