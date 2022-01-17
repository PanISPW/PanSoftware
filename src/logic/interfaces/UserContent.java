package logic.interfaces;

import logic.entity.User;

// @author Danilo D'Amico

public interface UserContent {

    public int getId();

    public void setId(int id);

    // basta stringa user?
    public User getUser();

    public void setUser(User user);

    public String getName();

    public void setName(String name);
}
