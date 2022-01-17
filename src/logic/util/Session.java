package logic.util;

import logic.enumeration.Pages;
import logic.enumeration.UserRole;

// session è un singleton che mantiene l'utente corrente

// @author Danilo D'Amico

public class Session {

    private static Session instance = null;
    private String username;
    private Pages page;
    private UserRole role;

    private Session() {
        username = "";
    }

    public static Session getSession() {
        if (instance == null)
            instance = new Session();

        return instance;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Pages getPage() {
        return page;
    }

    public void setPage(Pages page) {
        this.page = page;
    }

    public String getUser() {
        return username;
    }

    public void setCurrUser(String currUser) {
        this.username = currUser;
    }

}
