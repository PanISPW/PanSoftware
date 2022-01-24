package com.pansoftware.logic.interfaces;

import com.pansoftware.logic.entity.User;

// @author Danilo D'Amico

public interface UserContent {

    int getId();

    void setId(int id);

    // basta stringa user?
    User getUser();

    void setUser(User user);

    String getName();

    void setName(String name);
}
