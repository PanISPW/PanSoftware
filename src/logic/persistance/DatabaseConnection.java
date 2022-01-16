package logic.persistance;

import logic.exception.DatabaseException;

import java.sql.*;

// @author Danilo D'Amico

public class DatabaseConnection {

    private static Connection connection = null;
    private static String url = "jdbc:mysql://localhost:3306/pandb";
    private static String dbUser = "esameispw10";
    private static String dbPassword = "esameispw";

    public DatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection(url, dbUser, dbPassword);
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    public void closeResultSet(ResultSet resultSet) throws DatabaseException {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {

                throw new DatabaseException("Failure during result set closure");
            }
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    public void closeStatement(Statement statement) throws DatabaseException {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failure during statement closure");
        }
    }
}