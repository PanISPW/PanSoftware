package logic.util;

import logic.enumeration.EventRequestState;
import logic.enumeration.EventType;
import logic.enumeration.ProductType;
import logic.enumeration.UserRole;

import java.time.LocalDate;

import static logic.enumeration.EventType.PRIVATE;

// @author Danilo D'Amico

public class DaoUtils {

    private DaoUtils(){}

    public static ProductType databaseIntToProductType(int databaseInt) {

        switch (databaseInt) {

            case 0:
                return ProductType.MAKEUP;

            case 1:
                return ProductType.FOOD;

            case 2:
                return ProductType.LIFESTYLE;

            case 3:
                return ProductType.OTHER;

            default:
                return ProductType.NOTSPECIFIED;
        }
    }

    public static int productTypeToDatabaseInt(ProductType type) {

        switch (type) {

            case MAKEUP:
                return 0;

            case FOOD:
                return 1;

            case LIFESTYLE:
                return 2;

            case OTHER:
                return 3;

            default:
                return 4;
        }

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

        switch (databaseInt) {

            case 1:
                return UserRole.ACTIVIST;

            case 2:
                return UserRole.BRANDMANAGER;

            default:
                return UserRole.USER;
        }
    }

    public static int userRoleToDatabaseInt(UserRole role) {
        switch (role) {
            case ACTIVIST:
                return 1;
            case BRANDMANAGER:
                return 2;
            default:
                return 0;
        }
    }

    public static EventType databaseIntToEventType(int databaseInt) {

        if(databaseInt == 1){
            return PRIVATE;
        } else{
            return EventType.PUBLIC;
        }

    }

    public static int eventTypeToDatabaseInt(EventType type) {

        if(type == PRIVATE){
            return 1;
        } else{
            return 0;
        }
    }

    public static EventRequestState databaseIntToEventRequestState(int databaseInt) {
        switch (databaseInt) {

            case 0:
                return EventRequestState.PENDING;

            case 1:
                return EventRequestState.ACCEPTED;

            case 2:
                return EventRequestState.REJECTED;

            default:
                return EventRequestState.STARTING;
        }
    }

    public static int eventRequestStateToDatabaseInt(EventRequestState requestState) {
        switch (requestState) {

            case PENDING:
                return 0;

            case ACCEPTED:
                return 1;

            case REJECTED:
                return 2;

            default: // starting
                return 4;
        }
    }

}
