package logic.dao;

import logic.entity.User;
import logic.enumeration.UserRole;
import logic.exception.DatabaseException;
import logic.exception.UserNotFoundException;
import logic.persistance.DatabaseConnection;
import logic.persistance.queries.CRUDQueries;
import logic.persistance.queries.SimpleQueries;
import logic.util.Constants;
import logic.util.DaoUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// @author Danilo D'Amico

public class UserDao {

    private UserDao() {
    }

    public static UserRole checkUserPassword(String user, String password) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.checkUserCredentials(statement, user, password);


        if (!resultSet.first()) {
            throw new Exception("Username or password incorrect");
        }

        return DaoUtils.databaseIntToUserRole(resultSet.getInt("role"));

        //} catch (SQLException | ClassNotFoundException e) {

        //throw new DatabaseException("Can't retrieve data from database");

        //} finally {

        //if(databaseConnection!=null) {
        //	databaseConnection.closeResultSet(resultSet);
        //	databaseConnection.closeStatement(statement);
        //}
        //}
    }


    public static User getUser(String user) throws UserNotFoundException, Exception {

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
                throw new Exception("User incorrect");
            }

            role = DaoUtils.databaseIntToUserRole(resultSet.getInt("role"));

            userEntity = new User(user, resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("name"), resultSet.getString("surname"), role);

            return userEntity;


        } catch (SQLException | ClassNotFoundException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {

            if (databaseConnection != null) {
                databaseConnection.closeResultSet(resultSet);
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static int addUser(String username, String password, String email, String name, String surname, UserRole role) throws Exception {

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
