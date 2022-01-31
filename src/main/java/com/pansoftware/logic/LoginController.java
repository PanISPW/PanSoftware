package com.pansoftware.logic;

import com.pansoftware.logic.bean.LoginBean;
import com.pansoftware.logic.dao.UserDao;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.Session;

import java.sql.SQLException;

// @author Danilo D'Amico

public class LoginController {

    private LoginController(){}

    public static void loginUser(final LoginBean bean) throws UserNotFoundException, SQLException, EmptyResultSetException {
        final String user = bean.getUsername();
        final String password = bean.getPassword();
        final UserRole role;

        role  = UserDao.checkUserPassword(user, password);

        Session.getSession().setCurrUser(user);
        Session.getSession().setRole(role);
    }

    public static String getCurrentUser() throws InvalidDataException {
        try {
            return Session.getSession().getUser();
        } catch(final Exception e){
            throw new InvalidDataException("Session data is invalid");
        }
    }

    public static UserRole getUserRole() throws InvalidDataException {
        try {
            return Session.getSession().getRole();
        } catch(Exception e){
            throw new InvalidDataException("Session data is invalid");
        }
    }

    public static void invalidateSession() {
        Session.invalidate();
    }
}
