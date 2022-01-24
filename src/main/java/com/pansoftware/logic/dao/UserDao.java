package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.DatabaseConnection;
import com.pansoftware.logic.util.Constants;
import com.pansoftware.logic.util.DaoUtils;

import javax.security.auth.login.LoginException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// @author Danilo D'Amico

public class UserDao {

    private UserDao() {
    }

    public static UserRole checkUserPassword(String user, String password) throws UserNotFoundException, SQLException {

        DatabaseConnection databaseConnection;
        Statement statement;
        ResultSet resultSet;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        String sql = String.format("SELECT * FROM user WHERE username='%s' AND password ='%s';", user, password);
        resultSet = statement.executeQuery(sql);

        if (!resultSet.first()) {
            throw new UserNotFoundException("Username or password incorrect");
        }

        return DaoUtils.databaseIntToUserRole(resultSet.getInt("role"));
    }


    public static User getUser(String user) throws UserNotFoundException, LoginException, DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User userEntity;
        UserRole role;

        try {
            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            String sql = String.format("SELECT * FROM user WHERE username = '%s';", user);
            resultSet = statement.executeQuery(sql);

            if (!resultSet.first()) {
                throw new LoginException("User incorrect");
            }

            role = DaoUtils.databaseIntToUserRole(resultSet.getInt("role"));
            userEntity = new User(user, resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("name"), resultSet.getString("surname"), role);

            return userEntity;


        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeResultSet(resultSet);
            databaseConnection.closeStatement(statement);
        }

    }

    public static void addUser(String username, String password, String email, String name, String surname, UserRole role) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int roleInt;

        try {
            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            roleInt = DaoUtils.userRoleToDatabaseInt(role);

            String insertStatement = String.format("INSERT INTO user (username, password, email, name, surname, role) VALUES ('%s','%s','%s','%s','%s',%s);", username, password, email, name, surname, roleInt);
            statement.executeUpdate(insertStatement);

        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Goal in database");

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeStatement(statement);
        }

    }

}
