package logic.dao;

import logic.entity.Event;
import logic.entity.User;
import logic.enumeration.EventType;
import logic.exception.DatabaseException;
import logic.exception.UserNotFoundException;
import logic.persistance.DatabaseConnection;
import logic.persistance.queries.CRUDQueries;
import logic.persistance.queries.SimpleQueries;
import logic.util.DaoUtils;

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

    public static List<Event> getEventList() throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Event> eventList;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            resultSet = SimpleQueries.getEventList(statement);

            if (!resultSet.first()) {
                throw new Exception("No Event Found");
            }

            eventList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                User userEntity = UserDao.getUser(resultSet.getString("organizer"));
                EventType eventType = DaoUtils.databaseIntToEventType(resultSet.getInt("private"));


                Event singleEvent = new Event(userEntity, resultSet.getString("name"), resultSet.getDate("startingDate").toLocalDate(), resultSet.getDate("endingDate").toLocalDate(), eventType, resultSet.getInt("Id"));
                eventList.add(singleEvent);
            }

            return eventList;


        } catch (SQLException | ClassNotFoundException e) {

            throw new DatabaseException("Can't retrieve data from database");

        } finally {

            if (databaseConnection != null) {
                databaseConnection.closeResultSet(resultSet);
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static Event getEvent(int id, String organizer) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Event event;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            //System.out.println("getEvent " + String.valueOf(id) + " " + organizer);

            resultSet = SimpleQueries.getEvent(statement, id, organizer);

            if (!resultSet.first()) {
                throw new Exception("Event not found");
            }

            User userEntity = UserDao.getUser(resultSet.getString("organizer"));
            EventType eventType = DaoUtils.databaseIntToEventType(resultSet.getInt("private"));

            event = new Event(userEntity, resultSet.getString("name"), resultSet.getDate("startingDate").toLocalDate(), resultSet.getDate("endingDate").toLocalDate(), eventType, resultSet.getInt("Id"));

            return event;


        } catch (SQLException | ClassNotFoundException e) {

            throw new DatabaseException("Can't retrieve data from database");

        } finally {

            if (databaseConnection != null) {
                databaseConnection.closeResultSet(resultSet);
                databaseConnection.closeStatement(statement);
            }
        }

    }


    public static int addEvent(String organizer, int id, String name, LocalDate startingDate, LocalDate endingDate, EventType type) throws Exception {

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
            if (databaseConnection != null) {
                databaseConnection.closeStatement(statement);
            }
        }

    }


    public static EventType getEventType(String organizer, int id) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            resultSet = SimpleQueries.getEvent(statement, id, organizer);

            return DaoUtils.databaseIntToEventType(resultSet.getInt("private"));

        } catch (SQLException e) {

            throw new DatabaseException("Can't update Goal in database");

        } finally {
            if (databaseConnection != null) {
                databaseConnection.closeResultSet(resultSet);
                databaseConnection.closeStatement(statement);
            }
        }

    }

}
