package com.pansoftware.logic.interfaces;

import com.pansoftware.logic.entity.User;

// @author Danilo D'Amico

public abstract class UserContent {

    protected int id;
    protected User user;
    protected String name;

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
