package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.DaoUtils;
import com.pansoftware.logic.util.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

// @author Danilo D'Amico

public class UserDao {

    private UserDao() {
    }

    public static UserRole checkUserPassword(final String user, final String password) throws SQLException, UserNotFoundException {

        ResultSet resultSet = null;
        try {
            final String sql = String.format("SELECT * FROM user WHERE username='%s' AND password ='%s';", user, password);
            resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new UserNotFoundException("Username or password incorrect");
            }

            return DaoUtils.intToUserRole(resultSet.getInt("role"));
        } catch(final SQLException e){
            throw new SQLException("Can't validate credentials");
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
        }
    }


    public static User getUser(final String user) throws EmptyResultSetException, SQLException {
        try {
            final User userEntity;
            final UserRole role;

            final String sql = String.format("SELECT * FROM user WHERE username = '%s';", user);
            final ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("User not found");
            }

            role = DaoUtils.intToUserRole(resultSet.getInt("role"));
            userEntity = new User(user, resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("name"), resultSet.getString("surname"), role);

            DatabaseConnection.closeResultSet(resultSet);
            return userEntity;
        } catch(final SQLException e){
            throw new SQLException("SQL error");
        }
    }

    public static void addUser(final String username, final String password, final String email, final String name, final String surname, final UserRole role) throws SQLException {

        try {
            final int roleInt = DaoUtils.userRoleToInt(role);
            final String insertStatement = String.format("INSERT INTO user (username, password, email, name, surname, role) VALUES ('%s','%s','%s','%s','%s',%s);", username, password, email, name, surname, roleInt);
            DaoUtils.executeUpdate(insertStatement);
        } catch (final SQLException e) {
            throw new SQLException("Can't insert new Goal in database");
        }

    }

}
