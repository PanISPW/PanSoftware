package com.pansoftware.logic;

import com.pansoftware.logic.bean.LoginBean;
import com.pansoftware.logic.dao.UserDao;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.Session;

import java.sql.SQLException;

// @author Danilo D'Amico

public class LoginController {

    public static void loginUser(LoginBean bean) throws UserNotFoundException, SQLException, DatabaseException {
        String user = bean.getUsername();
        String password = bean.getPassword();
        UserRole role;

        // may throw UserNotFoundException
        role  = UserDao.checkUserPassword(user, password);

        Session.getSession().setCurrUser(user);
        Session.getSession().setRole(role);
    }

    public void signup(LoginBean bean) throws DatabaseException {

        User user = new User(bean.getUsername(), bean.getPassword(), bean.getEmail(), bean.getLoginName(), bean.getLoginSurname(), bean.getLoginRole());
        try {
            user.save();
        } catch (Exception e) {
            throw new DatabaseException("signup failed");
        }
    }
}
