package logic.entity;

import logic.dao.UserDao;
import logic.enumeration.UserRole;
import logic.exception.DatabaseException;

// @author Danilo D'Amico

public class User {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private UserRole role;

    public User(String username, String password, String email, String name, String surname, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void save() throws DatabaseException {
        UserDao.addUser(this.username, this.password, this.email, this.name, this.surname, this.role);
    }
}
