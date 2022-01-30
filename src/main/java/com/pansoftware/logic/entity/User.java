package com.pansoftware.logic.entity;

import com.pansoftware.logic.dao.UserDao;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.DatabaseException;

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
        if(this.username == null)
            return "";
        else
            return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(final UserRole role) {
        this.role = role;
    }

    public void save() throws DatabaseException {
        UserDao.addUser(username, password, email, name, surname, role);
    }
}
