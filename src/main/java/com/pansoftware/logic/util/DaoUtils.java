package com.pansoftware.logic.util;

import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.enumeration.EventType;
import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

// @author Danilo D'Amico

public class DaoUtils {

    private DaoUtils(){}

    public static ProductType intToProductType(final int databaseInt) {

        return switch (databaseInt) {
            case 0 -> ProductType.MAKEUP;
            case 1 -> ProductType.FOOD;
            case 2 -> ProductType.LIFESTYLE;
            case 3 -> ProductType.OTHER;
            default -> ProductType.NOTSPECIFIED;
        };
    }

    public static int productTypeToInt(final ProductType type) {

        return switch (type) {
            case MAKEUP -> 0;
            case FOOD -> 1;
            case LIFESTYLE -> 2;
            case OTHER -> 3;
            default -> 4;
        };

    }

    public static java.sql.Date localDateToSqlDateOrDefault(final LocalDate localDate) {
        final java.sql.Date date;
        try {
            date = java.sql.Date.valueOf(localDate);
        } catch (final NullPointerException e) {
            return java.sql.Date.valueOf(DataValidation.setDefaultDate());
        }

        return date;
    }

    public static UserRole intToUserRole(final int databaseInt) {

        return switch (databaseInt) {
            case 1 -> UserRole.ACTIVIST;
            case 2 -> UserRole.BRANDMANAGER;
            default -> UserRole.USER;
        };
    }

    public static int userRoleToInt(final UserRole role) {
        return switch (role) {
            case ACTIVIST -> 1;
            case BRANDMANAGER -> 2;
            default -> 0;
        };
    }

    public static EventType databaseIntToEventType(final int databaseInt) {

        if(databaseInt == 1){
            return EventType.PRIVATE;
        } else{
            return EventType.PUBLIC;
        }

    }

    public static int eventTypeToDatabaseInt(final EventType type) {

        if(type == EventType.PRIVATE){
            return 1;
        } else{
            return 0;
        }
    }

    public static EventRequestState databaseIntToEventRequestState(final int databaseInt) {
        return switch (databaseInt) {
            case 0 -> EventRequestState.PENDING;
            case 1 -> EventRequestState.ACCEPTED;
            case 2 -> EventRequestState.REJECTED;
            default -> EventRequestState.STARTING;
        };
    }

    public static int eventRequestStateToDatabaseInt(final EventRequestState requestState) {
        return switch (requestState) {
            case PENDING -> 0;
            case ACCEPTED -> 1;
            case REJECTED -> 2;
            default -> 4; // starting
        };
    }

    public static ResultSet executeCRUDQuery(final String sql) throws SQLException {
        final Statement statement;
        final DatabaseConnection databaseConnection;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        return statement.executeQuery(sql);
    }

    public static void executeUpdate(final String sql) throws SQLException, DatabaseException {
        final DatabaseConnection databaseConnection;
        final Statement statement;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        statement.executeUpdate(sql);
        DatabaseConnection.closeStatement(statement);
    }

    public static int getLastIdFromSelectedGoalType(final String goalType, final String user) throws DatabaseException {
        final int lastId;

        ResultSet resultSet = null;
        try {

            final String sql = String.format("SELECT MAX(Id) as maxId FROM %s WHERE user = '%s';", goalType, user);
            resultSet = executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
            }

            lastId = resultSet.getInt("maxId");
            return lastId;

        } catch (final SQLException | EmptyResultSetException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {
            DatabaseConnection.closeResultSet(resultSet);
        }
    }

}
