package logic.dao;

import logic.entity.Goal;
import logic.entity.User;
import logic.exception.DatabaseException;
import logic.exception.EmptyResultSetException;
import logic.exception.UserNotFoundException;
import logic.persistance.DatabaseConnection;
import logic.persistance.queries.CRUDQueries;
import logic.persistance.queries.SimpleQueries;
import logic.util.DaoUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// @author Danilo D'Amico

public class GoalDao {

    public static List<Goal> getGoalList(String user) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Goal> goalList;

        //try {

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getGoalList(statement, user);

        if (!resultSet.first()) {
            throw new Exception("No Goal related to the User was found");
        }


        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(user);

            Goal singleGoal = new Goal(resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("numberOfSteps"), resultSet.getInt("stepsCompleted"), resultSet.getDate("deadline").toLocalDate(), userEntity, resultSet.getInt("Id"));
            goalList.add(singleGoal);
        }

        return goalList;


        //} catch (SQLException | ClassNotFoundException e) {

        //	throw new DatabaseException("Can't retrieve data from database");

        //} finally {

        //if(databaseConnection!=null) {
//				databaseConnection.closeResultSet(resultSet);
//				databaseConnection.closeStatement(statement);
//			}
        //}

    }

    public static Goal getGoal(String user, int id) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Goal goal;

        //try {

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getGoal(statement, user, id);

        if (!resultSet.first()) {
            throw new Exception("Goal not found");
        }

        //resultSet.beforeFirst();

        User userEntity = UserDao.getUser(user);
        goal = new Goal(resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("numberOfSteps"), resultSet.getInt("stepsCompleted"), resultSet.getDate("deadline").toLocalDate(), userEntity, resultSet.getInt("Id"));

        return goal;


//		} catch (SQLException | ClassNotFoundException e) {
//
//			throw new DatabaseException("Can't retrieve data from database");
//
//		} finally {
//
//			if(databaseConnection!=null) {
//				databaseConnection.closeResultSet(resultSet);
//				databaseConnection.closeStatement(statement);
//			}
//		}

    }

    public static int getLastUserGoalId(String user) throws UserNotFoundException, EmptyResultSetException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int lastId;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            resultSet = SimpleQueries.getLastUserGoalId(statement, user);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No Goal related to the User was found");
            }

            lastId = resultSet.getInt("maxId");

            return lastId;


        } catch (SQLException e) {

            throw new DatabaseException("Can't retrieve data from database");

        } finally {
            if (databaseConnection != null) {
                databaseConnection.closeResultSet(resultSet);
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static int addGoal(String user, String name, String description, int numberOfSteps, int stepsCompleted, java.time.LocalDate deadline, int id) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            Date sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(deadline);
            result = CRUDQueries.addGoal(statement, name, description, numberOfSteps, stepsCompleted, sqlDeadline, id, user);

            return result;


            //} catch (SQLException e) {

            //throw new DatabaseException("Can't insert new Goal in database");

        } finally {
            if (databaseConnection != null) {
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static int updateStepsGoal(int stepsCompleted, int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            result = CRUDQueries.updateStepsGoal(statement, stepsCompleted, id, user);

            return result;


        } catch (SQLException e) {

            throw new DatabaseException("Can't update Goal in database");

        } finally {
            if (databaseConnection != null) {
                databaseConnection.closeStatement(statement);
            }
        }

    }

}
