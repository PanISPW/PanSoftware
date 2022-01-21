package com.pansoftware.logic.bean;

import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.util.DataValidation;

// @author Danilo D'Amico


public class LoginBean {

    private String username = "";
    private String password = "";

    private String email = "";
    private String name = "";
    private String surname = "";
    private UserRole role = UserRole.USER;

    public LoginBean(){
        // empty
    }

    public LoginBean(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public LoginBean(String username, String password, String email, String name, String surname, UserRole role) throws InvalidDataException {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setName(name);
        this.setSurname(surname);
        this.setRole(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidDataException {
        if (DataValidation.isCorrectEmail(email))
            this.email = email;
        else throw new InvalidDataException("Please insert a valid email");
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
        if (role.equals(UserRole.ACTIVIST) | role.equals(UserRole.BRANDMANAGER))
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

}
