package com.pansoftware.logic.util;

import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.enumeration.UserRole;

// session è un singleton che mantiene l'utente corrente

// @author Danilo D'Amico

public class Session {

    private static Session instance;
    private String username;
    private Pages page;
    private UserRole role;

    private Session() {
        this.username = "";
    }

    public static Session getSession() {
        if (Session.instance == null)
            Session.instance = new Session();

        return Session.instance;
    }

    public static void invalidate(){
        Session.instance = null;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(final UserRole role) {
        this.role = role;
    }

    public Pages getPage() {
        return this.page;
    }

    public void setPage(final Pages page) {
        this.page = page;
    }

    public String getUser() {
        return this.username;
    }

    public void setCurrUser(final String currUser) {
        username = currUser;
    }

}
