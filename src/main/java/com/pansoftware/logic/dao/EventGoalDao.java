package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.Constants;
import com.pansoftware.logic.util.DaoUtils;
import com.pansoftware.logic.util.DatabaseConnection;

import javax.security.auth.login.LoginException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.pansoftware.logic.util.Constants.NO_TRANSITION_OCCURS;
import static com.pansoftware.logic.util.DaoUtils.getLastIdFromSelectedGoalType;

// @author Danilo D'Amico

public class EventGoalDao {

    private EventGoalDao() {
    }

    public static List<EventGoal> getEventGoalList(final String user) throws DatabaseException, EmptyResultSetException {
        final List<EventGoal> goalList;
        ResultSet resultSet = null;
        try {
            resultSet = DaoUtils.executeCRUDQuery(String.format("SELECT * FROM eventgoal WHERE user = '%s';", user));

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No Event Goal related to the User was found");
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                final User userEntity = UserDao.getUser(user);
                final Event event;

                final EventRequestState state = DaoUtils.databaseIntToEventRequestState(resultSet.getInt(Constants.REQUEST_STATE));
                final int eventId = resultSet.getInt(Constants.EVENT_ID);
                if (eventId == -1) {
                    event = null;
                } else {
                    event = EventDao.getEvent(eventId, resultSet.getString(Constants.EVENT_ORGANIZER));
                }

                final EventGoal singleGoal = new EventGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION), resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED), resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("id"), userEntity, event, state);
                goalList.add(singleGoal);
            }

            return goalList;

        } catch (final SQLException e) {
            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
        }

    }

    public static List<EventGoal> getPendingEventGoalList(final String user) throws EmptyResultSetException, DatabaseException {
        final List<EventGoal> goalList;

        ResultSet resultSet = null;
        try {
            // 0 = PENDING
            final String sql = String.format("SELECT * FROM eventgoal WHERE eventOrganizer='%s' AND requestState=1;", user);
            resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No Pending Event Goal related to the User was found");
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                final User userEntity = UserDao.getUser(resultSet.getString("user"));

                final EventRequestState state = DaoUtils.databaseIntToEventRequestState(resultSet.getInt(Constants.REQUEST_STATE));

                final Event event = EventDao.getEvent(resultSet.getInt(Constants.EVENT_ID), resultSet.getString(Constants.EVENT_ORGANIZER));

                final EventGoal singleGoal = new EventGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                        resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                        resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("id"), userEntity, event, state);
                goalList.add(singleGoal);
            }

            return goalList;

        } catch (final SQLException e) {
            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
        }
    }

    public static EventGoal getEventGoal(final String user, final int id) throws EmptyResultSetException, DatabaseException {

        final EventGoal goal;
        final Event event;

        ResultSet resultSet = null;
        try {

            final String sql = String.format("SELECT * FROM eventgoal WHERE user = '%s' and id=%s;", user, id);
            resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("The Event Goal was not found");
            }

            final User userEntity = UserDao.getUser(user);

            final EventRequestState state = DaoUtils.databaseIntToEventRequestState(resultSet.getInt(Constants.REQUEST_STATE));


            event = EventDao.getEvent(resultSet.getInt(Constants.EVENT_ID), resultSet.getString(Constants.EVENT_ORGANIZER));

            goal = new EventGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                    resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                    resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("id"), userEntity, event,
                    state);

            return goal;

        } catch (final SQLException e) {
            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
        }
    }

    public static int getLastUserEventGoalId(final String user) throws DatabaseException {
        return getLastIdFromSelectedGoalType("eventgoal", user);
    }

    public static EventRequestState getEventParticipationState(final String user, final int id) throws EmptyResultSetException, DatabaseException {

        final EventGoal goal = EventGoalDao.getEventGoal(user, id);
        return goal.getState();
    }

    public static void addEventGoal(final EventGoal goal) throws DatabaseException {

        final int stateInt;

        try {
            stateInt = DaoUtils.eventRequestStateToDatabaseInt(goal.getState());
            final Date sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());

            final String insertStatement = String.format("INSERT INTO eventgoal (name, description, numberOfSteps, stepsCompleted, deadline, id, user, eventOrganizer, eventId, requestState) VALUES ('%s','%s',%s,%s,'%s',%s,'%s','%s',%s,%s);", goal.getName(), goal.getDescription(), goal.getNumberOfSteps(), goal.getStepsCompleted(), sqlDeadline, goal.getId(), goal.getUser().getUsername(), goal.getEvent().getUser().getUsername(), goal.getEvent().getId(), stateInt);
            DaoUtils.executeUpdate(insertStatement);

        } catch (final SQLException e) {

            throw new DatabaseException("Can't insert new Event Goal in database");

        }
    }

    public static void updateStepsEventGoal(final int stepsCompleted, final int id, final String user) throws DatabaseException {

        try {

            final String updateStatement = String.format("UPDATE  eventgoal set stepsCompleted=%s WHERE id = %s AND user = '%s';", stepsCompleted, id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (final SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }
    }

    public static void joinEvent(final Event event, final EventRequestState requestState, final int id, final String user) throws DatabaseException {

        final int requestStateInt;

        try {
            requestStateInt = DaoUtils.eventRequestStateToDatabaseInt(requestState);

            final String updateStatement = String.format("UPDATE eventgoal set eventId = %s and eventOrganizer = '%s' and requestState = %s WHERE id = %s AND user = '%s';", event.getId(), event.getUser().getUsername(), requestStateInt, id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (final SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }
    }

    public static void acceptEventGoal(final int id, final String user) throws DatabaseException {

        try {

            final String updateStatement = String.format("UPDATE eventgoal SET requestState=2 WHERE id=%s AND user='%s';", id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (final SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }

    }

    public static void rejectEventGoal(final int id, final String user) throws EmptyResultSetException, DatabaseException, NoTransitionException {

        if (!getEventParticipationState(user, id).equals(EventRequestState.PENDING)) {
            throw new NoTransitionException(NO_TRANSITION_OCCURS);
        }

        try {

            final String updateStatement = String.format("UPDATE eventgoal SET requestState=3 WHERE id=%s AND user = '%s';", id, user);
            DaoUtils.executeUpdate(updateStatement);
        } catch (final SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }
    }

    public static void pendingEventGoal(final int id, final String user) throws DatabaseException {

        try {

            final String updateStatement = String.format("UPDATE  eventgoal set requestState=0 WHERE id = %s AND user = '%s';", id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (final SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        }
    }
}
