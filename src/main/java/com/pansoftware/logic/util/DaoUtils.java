package com.pansoftware.logic.util;

import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.entity.Goal;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.enumeration.EventType;
import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.enumeration.UserRole;

import java.sql.Date;
import java.time.LocalDate;

// @author Danilo D'Amico

public class DaoUtils {

    private DaoUtils(){}

    public static ProductType databaseIntToProductType(int databaseInt) {

        return switch (databaseInt) {
            case 0 -> ProductType.MAKEUP;
            case 1 -> ProductType.FOOD;
            case 2 -> ProductType.LIFESTYLE;
            case 3 -> ProductType.OTHER;
            default -> ProductType.NOTSPECIFIED;
        };
    }

    public static int productTypeToDatabaseInt(ProductType type) {

        return switch (type) {
            case MAKEUP -> 0;
            case FOOD -> 1;
            case LIFESTYLE -> 2;
            case OTHER -> 3;
            default -> 4;
        };

    }

    public static java.sql.Date localDateToSqlDateOrDefault(LocalDate localDate) {
        java.sql.Date date;
        try {
            date = java.sql.Date.valueOf(localDate);
        } catch (NullPointerException e) {
            return java.sql.Date.valueOf(DataValidation.setDefaultDate());
        }

        return date;
    }

    public static UserRole databaseIntToUserRole(int databaseInt) {

        return switch (databaseInt) {
            case 1 -> UserRole.ACTIVIST;
            case 2 -> UserRole.BRANDMANAGER;
            default -> UserRole.USER;
        };
    }

    public static int userRoleToDatabaseInt(UserRole role) {
        return switch (role) {
            case ACTIVIST -> 1;
            case BRANDMANAGER -> 2;
            default -> 0;
        };
    }

    public static EventType databaseIntToEventType(int databaseInt) {

        if(databaseInt == 1){
            return EventType.PRIVATE;
        } else{
            return EventType.PUBLIC;
        }

    }

    public static int eventTypeToDatabaseInt(EventType type) {

        if(type == EventType.PRIVATE){
            return 1;
        } else{
            return 0;
        }
    }

    public static EventRequestState databaseIntToEventRequestState(int databaseInt) {
        return switch (databaseInt) {
            case 0 -> EventRequestState.PENDING;
            case 1 -> EventRequestState.ACCEPTED;
            case 2 -> EventRequestState.REJECTED;
            default -> EventRequestState.STARTING;
        };
    }

    public static int eventRequestStateToDatabaseInt(EventRequestState requestState) {
        return switch (requestState) {
            case PENDING -> 0;
            case ACCEPTED -> 1;
            case REJECTED -> 2;
            default -> 4; // starting
        };
    }

}
