package logic.dao;

import logic.bean.AdviceGoalQueryBean;
import logic.bean.EventGoalQueryBean;
import logic.entity.Event;
import logic.entity.EventGoal;
import logic.entity.User;
import logic.enumeration.EventRequestState;
import logic.exception.DatabaseException;
import logic.exception.EmptyResultSetException;
import logic.exception.UserNotFoundException;
import logic.persistance.DatabaseConnection;
import logic.persistance.queries.CRUDQueries;
import logic.persistance.queries.SimpleQueries;
import logic.util.Constants;
import logic.util.DaoUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// @author Danilo D'Amico

public class EventGoalDao {

    private EventGoalDao() {
    }

    public static List<EventGoal> getEventGoalList(String user) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<EventGoal> goalList;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            resultSet = SimpleQueries.getEventGoalList(statement, user);

            if (!resultSet.first()) {
                throw new Exception("No Event Goal related to the User was found");
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

        } catch (SQLException | ClassNotFoundException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {

            if (databaseConnection != null) {
                databaseConnection.closeResultSet(resultSet);
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static List<EventGoal> getPendingEventGoalList(String user) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<EventGoal> goalList;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            resultSet = SimpleQueries.getPendingEventApprovalList(statement, user);

            if (!resultSet.first()) {
                throw new Exception("No Pending Event Goal related to the User was found");
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

        } catch (SQLException | ClassNotFoundException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {

            if (databaseConnection != null) {
                databaseConnection.closeResultSet(resultSet);
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static EventGoal getEventGoal(String user, int id) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        EventGoal goal;
        Event event;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            resultSet = SimpleQueries.getEventGoal(statement, user, id);

            if (!resultSet.first()) {
                throw new Exception("The Event Goal was not found");
            }

            User userEntity = UserDao.getUser(user);

            EventRequestState state = DaoUtils.databaseIntToEventRequestState(resultSet.getInt(Constants.REQUEST_STATE));


            event = EventDao.getEvent(resultSet.getInt(Constants.EVENT_ID), resultSet.getString(Constants.EVENT_ORGANIZER));

            goal = new EventGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                    resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                    resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("id"), userEntity, event,
                    state);

            return goal;

        } catch (SQLException |

                ClassNotFoundException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {

            if (databaseConnection != null) {
                databaseConnection.closeResultSet(resultSet);
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static int getLastUserEventGoalId(String user) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int lastId;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            resultSet = SimpleQueries.getLastUserEventGoalId(statement, user);

            if (!resultSet.first()) {
                throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
            }

            lastId = resultSet.getInt("maxId");

            return lastId;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {
            databaseConnection.closeResultSet(resultSet);
            databaseConnection.closeStatement(statement);
        }

    }

    public static EventRequestState getEventParticipationState(String user, int id) throws UserNotFoundException, Exception {

        EventGoal goal = getEventGoal(user, id);
        return goal.getState();
    }

    public static int addEventGoal(EventGoal goal) throws Exception {

        // String user, String name, String description, int numberOfSteps, int stepsCompleted,
        //                                   LocalDate deadline, int id, String eventOrganizer, int eventId, EventRequestState requestState

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int stateInt;
        int result;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            stateInt = DaoUtils.eventRequestStateToDatabaseInt(goal.getState());
            Date sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());

            EventGoalQueryBean bean = (EventGoalQueryBean) DaoUtils.getGoalQueryBean(goal, sqlDeadline);

            bean.setEventOrganizer(goal.getOrganizer().getUsername());
            bean.setEventId(goal.getEvent().getId());
            bean.setRequestState(stateInt);

            result = CRUDQueries.addEventGoal(statement, bean);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Event Goal in database");

        } finally {
            databaseConnection.closeStatement(statement);
        }

    }

    public static int updateStepsEventGoal(int stepsCompleted, int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            result = CRUDQueries.updateStepsEventGoal(statement, stepsCompleted, id, user);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        } finally {
            databaseConnection.closeStatement(statement);
        }

    }

    public static int joinEvent(Event event, EventRequestState requestState, int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;
        int requestStateInt;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            requestStateInt = DaoUtils.eventRequestStateToDatabaseInt(requestState);

            result = CRUDQueries.joinEvent(statement, event.getId(), event.getUser().getUsername(), requestStateInt, id, user);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        } finally {
            databaseConnection.closeStatement(statement);
        }

    }

    public static int acceptEventGoal(int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result = 0;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            result = CRUDQueries.acceptEventParticipation(statement, id, user);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        } finally {
            databaseConnection.closeStatement(statement);
        }

    }

    public static int rejectEventGoal(int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result = 0;

        if (!EventGoalDao.getEventParticipationState(user, id).equals(EventRequestState.PENDING)) {
            throw new Exception("Only EventGoals in a pending state can be rejected");
        }

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            result = CRUDQueries.rejectEventParticipation(statement, id, user);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        } finally {
            databaseConnection.closeStatement(statement);
        }

    }

    public static int pendingEventGoal(int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result = 0;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            result = CRUDQueries.pendingEventParticipation(statement, id, user);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_EVENT_GOAL_IN_DATABASE);

        } finally {
            databaseConnection.closeStatement(statement);
        }

    }
}
