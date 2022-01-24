package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.DaoUtils;

import javax.security.auth.login.LoginException;
import java.sql.ResultSet;
import java.sql.SQLException;

// @author Danilo D'Amico

public class UserDao {

    private UserDao() {
    }

    public static UserRole checkUserPassword(String user, String password) throws UserNotFoundException, SQLException {

        String sql = String.format("SELECT * FROM user WHERE username='%s' AND password ='%s';", user, password);
        ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

        if (!resultSet.first()) {
            throw new UserNotFoundException("Username or password incorrect");
        }

        return DaoUtils.databaseIntToUserRole(resultSet.getInt("role"));
    }


    public static User getUser(String user) throws UserNotFoundException, LoginException, DatabaseException, SQLException {

        User userEntity;
        UserRole role;

        String sql = String.format("SELECT * FROM user WHERE username = '%s';", user);
        ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

        if (!resultSet.first()) {
            throw new LoginException("User incorrect");
        }

        role = DaoUtils.databaseIntToUserRole(resultSet.getInt("role"));
        userEntity = new User(user, resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("name"), resultSet.getString("surname"), role);

        return userEntity;
    }

    public static void addUser(String username, String password, String email, String name, String surname, UserRole role) throws DatabaseException, SQLException {

        try {
            int roleInt = DaoUtils.userRoleToDatabaseInt(role);
            String insertStatement = String.format("INSERT INTO user (username, password, email, name, surname, role) VALUES ('%s','%s','%s','%s','%s',%s);", username, password, email, name, surname, roleInt);
            DaoUtils.executeCRUDQuery(insertStatement);
        } catch (SQLException e) {
            throw new DatabaseException("Can't insert new Goal in database");
        }

    }

}
