package com.pansoftware.logic.util;

import com.pansoftware.logic.exception.DatabaseException;

import java.sql.*;

// @author Danilo D'Amico

public class DatabaseConnection {

    private final Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/pandb";
    private static final String DBUSER = "esameispw10";
    private static final String DB_PASSWORD = "esameispw";

    public DatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, DBUSER, DB_PASSWORD);
    }

    public static void closeResultSet(ResultSet resultSet) throws DatabaseException {
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

    public static void closeStatement(Statement statement) throws DatabaseException {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failure during statement closure");
        }
    }
}