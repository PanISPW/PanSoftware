package com.pansoftware.logic.entity;

import com.pansoftware.logic.dao.UserDao;
import com.pansoftware.logic.enumeration.UserRole;

import java.sql.SQLException;

// @author Danilo D'Amico

public class User {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private UserRole role;

    public User(final String username, final String password, final String email, final String name, final String surname, final UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getUsername() {
        if(username == null)
            return "";
        else
            return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(final UserRole role) {
        this.role = role;
    }

    public void save() throws SQLException {
        UserDao.addUser(username, password, email, name, surname, role);
    }
}
