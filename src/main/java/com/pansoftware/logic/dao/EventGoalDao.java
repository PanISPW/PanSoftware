package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.DatabaseConnection;
import com.pansoftware.logic.util.Constants;
import com.pansoftware.logic.util.DaoUtils;

import javax.security.auth.login.LoginException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.pansoftware.logic.util.Constants.NO_TRANSITION_OCCURS;

// @author Danilo D'Amico

public class EventGoalDao {

    private EventGoalDao() {
    }

    public static List<EventGoal> getEventGoalList(String user) throws UserNotFoundException, DatabaseException, LoginException, EmptyResultSetException {
        List<EventGoal> goalList;

        try {

            String sql = String.format("SELECT * FROM eventgoal WHERE user = '%s';", user);
            ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No Event Goal related to the User was found");
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                User userEntity = UserDao.getUser(user);
                Event event;

                EventRequestState state = DaoUtils.databaseIntToEventRequestState(resultSet.getInt(Constants.REQUEST_STATE));
                int eventId = resultSet.getInt(Constants.EVENT_ID);
                if (eventId == -1) {
                    event = null;
                } else {
                    event = EventDao.getEvent(eventId, resultSet.getString(Constants.EVENT_ORGANIZER));
                }


                EventGoal singleGoal = new EventGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION), resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED), resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("id"), userEntity, event, state);
                goalList.add(singleGoal);
            }

            return goalList;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        }

    }

    public static List<EventGoal> getPendingEventGoalList(String user) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        List<EventGoal> goalList;

        try {
            // 0 = PENDING
            String sql = String.format("SELECT * FROM eventgoal WHERE eventOrganizer='%s' AND requestState=0;", user);
            ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No Pending Event Goal related to the User was found");
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                User userEntity = UserDao.getUser(resultSet.getString("user"));

                EventRequestState state = DaoUtils.databaseIntToEventRequestState(resultSet.getInt(Constants.REQUEST_STATE));

                Event event = EventDao.getEvent(resultSet.getInt(Constants.EVENT_ID), resultSet.getString(Constants.EVENT_ORGANIZER));

                EventGoal singleGoal = new EventGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                        resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                        resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("id"), userEntity, event, state);
                goalList.add(singleGoal);
            }

            return goalList;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        }
    }

    public static EventGoal getEventGoal(String user, int id) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {

        EventGoal goal;
        Event event;

        try {

            String sql = String.format("SELECT * FROM eventgoal WHERE user = '%s' and id=%s;", user, id);
            ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("The Event Goal was not found");
            }

            User userEntity = UserDao.getUser(user);

            EventRequestState state = DaoUtils.databaseIntToEventRequestState(resultSet.getInt(Constants.REQUEST_STATE));


            event = EventDao.getEvent(resultSet.getInt(Constants.EVENT_ID), resultSet.getString(Constants.EVENT_ORGANIZER));

            goal = new EventGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                    resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                    resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("id"), userEntity, event,
                    state);

            return goal;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        }
    }

    public static int getLastUserEventGoalId(String user) throws EmptyResultSetException, DatabaseException {

        int lastId;

        try {

            String sql = String.format("SELECT MAX(Id) as maxId FROM eventgoal WHERE user = '%s';", user);
            ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
            }

            lastId = resultSet.getInt("maxId");

            return lastId;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        }

    }

    public static EventRequestState getEventParticipationState(String user, int id) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {

        EventGoal goal = getEventGoal(user, id);
        return goal.getState();
    }

    public static void addEventGoal(EventGoal goal) throws DatabaseException {

        int stateInt;

        try {
            stateInt = DaoUtils.eventRequestStateToDatabaseInt(goal.getState());
            Date sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());

            String insertStatement = String.format("INSERT INTO eventgoal (name, description, numberOfSteps, stepsCompleted, deadline, id, user, eventOrganizer, eventId, requestState) VALUES ('%s','%s',%s,%s,'%s',%s,'%s','%s',%s,%s);", goal.getName(), goal.getDescription(), goal.getNumberOfSteps(), goal.getStepsCompleted(), sqlDeadline, goal.getId(), goal.getUser().getUsername(), goal.getOrganizer().getUsername(), goal.getEvent().getId(), stateInt);
            DaoUtils.executeCRUDQuery(insertStatement);

        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Event Goal in database");

        }
    }

    public static void updateStepsEventGoal(int stepsCompleted, int id, String user) throws DatabaseException {

        try {

            String updateStatement = String.format("UPDATE  eventgoal set stepsCompleted=%s WHERE id = %s AND user = '%s';", stepsCompleted, id, user);
            ResultSet resultSet = DaoUtils.executeCRUDQuery(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }
    }

    public static void joinEvent(Event event, EventRequestState requestState, int id, String user) throws DatabaseException {

        int requestStateInt;

        try {
            requestStateInt = DaoUtils.eventRequestStateToDatabaseInt(requestState);

            String updateStatement = String.format("UPDATE eventgoal set eventId = %s and eventOrganizer = '%s' and requestState = %s WHERE id = %s AND user = '%s';", event.getId(), event.getUser().getUsername(), requestStateInt, id, user);
            ResultSet resultSet = DaoUtils.executeCRUDQuery(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }
    }

    public static void acceptEventGoal(int id, String user) throws DatabaseException {

        try {

            String updateStatement = String.format("UPDATE eventgoal SET requestState=1 WHERE id=%s AND user='%s';", id, user);
            DaoUtils.executeCRUDQuery(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }

    }

    public static void rejectEventGoal(int id, String user) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, NoTransitionException {

        if (!EventGoalDao.getEventParticipationState(user, id).equals(EventRequestState.PENDING)) {
            throw new NoTransitionException(NO_TRANSITION_OCCURS);
        }

        try {

            String updateStatement = String.format("UPDATE eventgoal SET requestState=2 WHERE id=%s AND user = '%s';", id, user);
            DaoUtils.executeCRUDQuery(updateStatement);
        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }
    }

    public static void pendingEventGoal(int id, String user) throws DatabaseException {

        try {

            String updateStatement = String.format("UPDATE  eventgoal set requestState=0 WHERE id = %s AND user = '%s';", id, user);
            ResultSet resultSet = DaoUtils.executeCRUDQuery(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }
    }
}
