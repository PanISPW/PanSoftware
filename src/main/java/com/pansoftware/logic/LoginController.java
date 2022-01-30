package com.pansoftware.logic;

import com.pansoftware.logic.bean.LoginBean;
import com.pansoftware.logic.dao.UserDao;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.Session;

import java.sql.SQLException;

// @author Danilo D'Amico

public class LoginController {

    public static void loginUser(final LoginBean bean) throws UserNotFoundException, SQLException, DatabaseException, EmptyResultSetException {
        final String user = bean.getUsername();
        final String password = bean.getPassword();
        final UserRole role;

        // may throw UserNotFoundException
        role  = UserDao.checkUserPassword(user, password);

        Session.getSession().setCurrUser(user);
        Session.getSession().setRole(role);
    }

    public void signup(final LoginBean bean) throws DatabaseException {

        final User user = new User(bean.getUsername(), bean.getPassword(), bean.getEmail(), bean.getLoginName(), bean.getLoginSurname(), bean.getLoginRole());
        try {
            user.save();
        } catch (final Exception e) {
            throw new DatabaseException("signup failed");
        }
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
        } catch(final Exception e){
            throw new InvalidDataException("Session data is invalid");
        }
    }

    public static void invalidateSession() {
        Session.invalidate();
    }
}
