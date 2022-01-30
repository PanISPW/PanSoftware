package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.Goal;
import com.pansoftware.logic.entity.User;
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

import static com.pansoftware.logic.util.DaoUtils.getLastIdFromSelectedGoalType;

// @author Danilo D'Amico

public class GoalDao {

    private GoalDao(){}

    public static List<Goal> getGoalList(final String user) throws EmptyResultSetException, DatabaseException {
        try {
            final List<Goal> goalList;

            final String sql = String.format("SELECT * FROM goal WHERE user = '%s';", user);
            final ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                final User userEntity = UserDao.getUser(user);

                final Goal singleGoal = new Goal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION), resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED), resultSet.getDate(Constants.DEADLINE).toLocalDate(), userEntity, resultSet.getInt("Id"));
                goalList.add(singleGoal);
            }

            DatabaseConnection.closeResultSet(resultSet);
            return goalList;
        } catch(final SQLException e){
            throw new DatabaseException("SQL error");
        }
    }

    public static Goal getGoal(final String user, final int id) throws EmptyResultSetException, DatabaseException {

        try {
            final Goal goal;

            final String sql = String.format("SELECT * FROM goal WHERE user='%s' and id=%s;", user, id);
            final ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException("Goal not found");
            }

            final User userEntity = UserDao.getUser(user);
            goal = new Goal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION), resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED), resultSet.getDate(Constants.DEADLINE).toLocalDate(), userEntity, resultSet.getInt("Id"));

            DatabaseConnection.closeResultSet(resultSet);
            return goal;
        } catch(final SQLException e){
            throw new DatabaseException("SQL error");
        }
    }

    public static int getLastUserGoalId(final String user) throws DatabaseException {
        return getLastIdFromSelectedGoalType("goal", user);
    }

    public static void addGoal(final Goal goal) throws DatabaseException {

        try {
            final Date sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());
            final String insertStatement = String.format("INSERT INTO goal (name, description, numberOfSteps, stepsCompleted, deadline, id, user) VALUES ('%s','%s',%s,%s,'%s',%s,'%s');", goal.getName(), goal.getDescription(), goal.getNumberOfSteps(), goal.getStepsCompleted(), sqlDeadline, goal.getId(), goal.getUser().getUsername());
            DaoUtils.executeUpdate(insertStatement);
        } catch(final SQLException e){
            throw new DatabaseException("Can't add Goal to Database");
        }
    }

    public static void updateStepsGoal(final int stepsCompleted, final int id, final String user) throws DatabaseException {

        try {

            final String updateStatement = String.format("UPDATE  goal set stepsCompleted=%s WHERE id = %s AND user='%s';", stepsCompleted, id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (final SQLException e) {
            throw new DatabaseException("Can't update Goal in database");
        }
    }
}
