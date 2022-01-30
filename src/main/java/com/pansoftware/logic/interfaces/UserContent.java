package com.pansoftware.logic.interfaces;

import com.pansoftware.logic.entity.User;

// @author Danilo D'Amico

public abstract class UserContent {

    protected int id;
    protected User user;
    protected String name;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
