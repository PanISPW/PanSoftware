package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.persistance.DatabaseConnection;
import com.pansoftware.logic.persistance.queries.CRUDQueries;
import com.pansoftware.logic.persistance.queries.SimpleQueries;
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

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.checkUserCredentials(statement, user, password);


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
            resultSet = SimpleQueries.getUser(statement, user);

            if (!resultSet.first()) {
                throw new LoginException("User incorrect");
            }

            role = DaoUtils.databaseIntToUserRole(resultSet.getInt("role"));

            userEntity = new User(user, resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("name"), resultSet.getString("surname"), role);

            return userEntity;


        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {
            databaseConnection.closeResultSet(resultSet);
            databaseConnection.closeStatement(statement);
        }

    }

    public static int addUser(String username, String password, String email, String name, String surname, UserRole role) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int roleInt;
        int result;

        try {
            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            roleInt = DaoUtils.userRoleToDatabaseInt(role);
            result = CRUDQueries.addUser(statement, username, password, email, name, surname, roleInt);

            return result;


        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Goal in database");

        } finally {
            databaseConnection.closeStatement(statement);
        }

    }

}
