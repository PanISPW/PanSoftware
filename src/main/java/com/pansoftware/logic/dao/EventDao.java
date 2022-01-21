package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.EventType;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.persistance.DatabaseConnection;
import com.pansoftware.logic.persistance.queries.CRUDQueries;
import com.pansoftware.logic.persistance.queries.SimpleQueries;
import com.pansoftware.logic.util.Constants;
import com.pansoftware.logic.util.DaoUtils;

import javax.security.auth.login.LoginException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// @author Danilo D'Amico

public class EventDao {


    private EventDao() {
    }

    public static List<Event> getEventList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Event> eventList;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            resultSet = SimpleQueries.getEventList(statement);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No Event Found");
            }

            eventList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                User userEntity = UserDao.getUser(resultSet.getString("organizer"));
                EventType eventType = DaoUtils.databaseIntToEventType(resultSet.getInt(Constants.PRIVATE));


                Event singleEvent = new Event(userEntity, resultSet.getString("name"), resultSet.getDate("startingDate").toLocalDate(), resultSet.getDate("endingDate").toLocalDate(), eventType, resultSet.getInt("Id"));
                eventList.add(singleEvent);
            }

            return eventList;


        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {
            databaseConnection.closeResultSet(resultSet);
            databaseConnection.closeStatement(statement);
        }

    }

    public static Event getEvent(int id, String organizer) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Event event;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            resultSet = SimpleQueries.getEvent(statement, id, organizer);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("Event not found");
            }

            User userEntity = UserDao.getUser(resultSet.getString("organizer"));
            EventType eventType = DaoUtils.databaseIntToEventType(resultSet.getInt(Constants.PRIVATE));

            event = new Event(userEntity, resultSet.getString("name"), resultSet.getDate("startingDate").toLocalDate(), resultSet.getDate("endingDate").toLocalDate(), eventType, resultSet.getInt("Id"));

            return event;


        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {
            databaseConnection.closeResultSet(resultSet);
            databaseConnection.closeStatement(statement);
        }

    }


    public static int addEvent(String organizer, int id, String name, LocalDate startingDate, LocalDate endingDate, EventType type) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int typeInt;
        int result;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            typeInt = DaoUtils.eventTypeToDatabaseInt(type);
            Date sqlStartingDate = DaoUtils.localDateToSqlDateOrDefault(startingDate);
            Date sqlEndingDate = DaoUtils.localDateToSqlDateOrDefault(endingDate);
            result = CRUDQueries.addEvent(statement, id, organizer, name, sqlStartingDate, sqlEndingDate, typeInt);

            return result;


        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Goal in database");

        } finally {
            databaseConnection.closeStatement(statement);
        }

    }


    public static EventType getEventType(String organizer, int id) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            resultSet = SimpleQueries.getEvent(statement, id, organizer);

            return DaoUtils.databaseIntToEventType(resultSet.getInt(Constants.PRIVATE));

        } catch (SQLException e) {

            throw new DatabaseException("Can't update Goal in database");

        } finally {
            databaseConnection.closeResultSet(resultSet);
            databaseConnection.closeStatement(statement);
        }

    }

}
