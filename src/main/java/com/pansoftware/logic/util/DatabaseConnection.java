package com.pansoftware.logic.util;

import com.pansoftware.logic.exception.DatabaseException;

import java.sql.*;

// @author Danilo D'Amico

public class DatabaseConnection {

    private final Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/pandb";
    private static final String dbUser = "esameispw10";
    private static final String dbPassword = "esameispw";

    public DatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection(url, dbUser, dbPassword);
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