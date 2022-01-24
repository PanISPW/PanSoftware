package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.EventType;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
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

// @author Danilo D'Amico

public class EventDao {


    private EventDao() {
    }

    public static List<Event> getEventList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {

        List<Event> eventList;

        ResultSet resultSet = null;
        try {
            resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM event;");

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

        } finally{
            DatabaseConnection.closeResultSet(resultSet);
        }
    }

    public static Event getEvent(int id, String organizer) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {

        Event event;

        ResultSet resultSet = null;
        try {

            String sql = String.format("SELECT * FROM event WHERE id = %s AND organizer = '%s';", id, organizer);
            resultSet = DaoUtils.executeCRUDQuery(sql);

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
            DatabaseConnection.closeResultSet(resultSet);
        }
    }


    public static void addEvent(Event event) throws DatabaseException {

        int typeInt;

        try {

            typeInt = DaoUtils.eventTypeToDatabaseInt(event.getType());
            Date sqlStartingDate = DaoUtils.localDateToSqlDateOrDefault(event.getStartingDate());
            Date sqlEndingDate = DaoUtils.localDateToSqlDateOrDefault(event.getEndingDate());

            String insertStatement = String.format("INSERT INTO event (id, organizer, name, startingDate, endingDate, private) VALUES (%s,'%s','%s','%s','%s',%s);", event.getId(), event.getUser().getUsername(), event.getName(), sqlStartingDate, sqlEndingDate, typeInt);
            DaoUtils.executeUpdate(insertStatement);

        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Goal in database");

        }
    }


    public static EventType getEventType(String organizer, int id) throws DatabaseException {

        ResultSet resultSet = null;
        try {
            String sql = String.format("SELECT * FROM event WHERE id = %s AND organizer = '%s';", id, organizer);
            resultSet = DaoUtils.executeCRUDQuery(sql);

            return DaoUtils.databaseIntToEventType(resultSet.getInt(Constants.PRIVATE));

        } catch (SQLException e) {
            throw new DatabaseException("Can't update Goal in database");
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
        }
    }
}
