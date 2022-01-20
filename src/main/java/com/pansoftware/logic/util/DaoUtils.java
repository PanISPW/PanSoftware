package com.pansoftware.logic.util;

import com.pansoftware.logic.bean.GoalQueryBean;
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

    public static GoalQueryBean getGoalQueryBean(Goal goal, Date sqlDeadline) {
        GoalQueryBean bean = new GoalQueryBean();
        bean.setName(goal.getName());
        bean.setDescription(goal.getDescription());
        bean.setNumberOfSteps(goal.getNumberOfSteps());
        bean.setStepsCompleted(goal.getStepsCompleted());
        bean.setDeadline(sqlDeadline);
        bean.setId(goal.getId());
        bean.setUser(goal.getUser().getUsername());
        return bean;
    }
}
