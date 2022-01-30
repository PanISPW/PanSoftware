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

    public LoginBean(){}

    public LoginBean(final String username, final String password) {
        setUsername(username);
        setPassword(password);
    }

    public LoginBean(final String username, final String password, final String email, final String name, final String surname, final UserRole role) throws InvalidDataException {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setLoginName(name);
        setLoginSurname(surname);
        setLoginRole(role);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) throws InvalidDataException {
        if (DataValidation.isCorrectEmail(email))
            this.email = email;
        else throw new InvalidDataException("Please insert a valid email");
    }

    public String getLoginName() {
        return this.name;
    }

    public void setLoginName(final String name) {
        this.name = name;
    }

    public String getLoginSurname() {
        return this.surname;
    }

    public void setLoginSurname(final String surname) {
        this.surname = surname;
    }

    public UserRole getLoginRole() {
        return this.role;
    }

    public void setLoginRole(final UserRole role) {
        if (role.equals(UserRole.ACTIVIST) | role.equals(UserRole.BRANDMANAGER))
            this.role = role;
    }

    public String getUsername() {
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

}
