package com.pansoftware.logic.util;

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

    public static void closeResultSet(final ResultSet resultSet) throws SQLException {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (final SQLException e) {

                throw new SQLException("Failure during result set closure");
            }
    }

    public Statement createStatement() throws SQLException {
        return this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    public static void closeStatement(final Statement statement) throws SQLException {
        try {
            if (statement != null)
                statement.close();
        } catch (final SQLException e) {
            throw new SQLException("Failure during statement closure");
        }
    }
}