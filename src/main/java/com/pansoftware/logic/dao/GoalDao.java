package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.Goal;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
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

// @author Danilo D'Amico

public class GoalDao {

    private GoalDao(){}

    public static List<Goal> getGoalList(String user) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {

        DatabaseConnection databaseConnection;
        Statement statement;
        ResultSet resultSet;
        List<Goal> goalList;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        String sql = String.format("SELECT * FROM goal WHERE user = '%s';", user);
        resultSet = statement.executeQuery(sql);

        if (!resultSet.first()) {
            throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
        }


        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(user);

            Goal singleGoal = new Goal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION), resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED), resultSet.getDate(Constants.DEADLINE).toLocalDate(), userEntity, resultSet.getInt("Id"));
            goalList.add(singleGoal);
        }

        return goalList;

    }

    public static Goal getGoal(String user, int id) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {

        DatabaseConnection databaseConnection;
        Statement statement;
        ResultSet resultSet;
        Goal goal;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        String sql = String.format("SELECT * FROM goal WHERE user='%s' and id=%s;", user, id);
        resultSet = statement.executeQuery(sql);

        if (!resultSet.first()) {
            throw new EmptyResultSetException("Goal not found");
        }

        User userEntity = UserDao.getUser(user);
        goal = new Goal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION), resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED), resultSet.getDate(Constants.DEADLINE).toLocalDate(), userEntity, resultSet.getInt("Id"));

        return goal;
    }

    public static int getLastUserGoalId(String user) throws EmptyResultSetException, DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int lastId;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            String sql = String.format("SELECT MAX(Id) as maxId FROM goal WHERE user='%s';", user);
            resultSet = statement.executeQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
            }

            lastId = resultSet.getInt("maxId");

            return lastId;


        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_RETRIEVE_DATA_FROM_DATABASE);

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeResultSet(resultSet);
            databaseConnection.closeStatement(statement);
        }

    }

    public static void addGoal(Goal goal) throws SQLException, DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            Date sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());

            String insertStatement = String.format("INSERT INTO goal (name, description, numberOfSteps, stepsCompleted, deadline, id, user) VALUES ('%s','%s',%s,%s,'%s',%s,'%s');", goal.getName(), goal.getDescription(), goal.getNumberOfSteps(), goal.getStepsCompleted(), sqlDeadline, goal.getId(), goal.getUser().getUsername());
            statement.executeUpdate(insertStatement);

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeStatement(statement);
        }

    }

    public static void updateStepsGoal(int stepsCompleted, int id, String user) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            String updateStatement = String.format("UPDATE  goal set stepsCompleted=%s WHERE id = %s AND user='%s';", stepsCompleted, id, user);
            statement.executeUpdate(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException("Can't update Goal in database");

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeStatement(statement);
        }

    }

}
