package com.pansoftware.logic.interfaces;

import com.pansoftware.logic.entity.User;

// @author Danilo D'Amico

public abstract class UserContent {

    protected int id;
    protected User user;
    protected String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
