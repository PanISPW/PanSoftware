package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.Goal;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.Constants;
import com.pansoftware.logic.util.DaoUtils;

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

    public static List<Goal> getGoalList(String user) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {

        List<Goal> goalList;

        String sql = String.format("SELECT * FROM goal WHERE user = '%s';", user);
        ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

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

        Goal goal;

        String sql = String.format("SELECT * FROM goal WHERE user='%s' and id=%s;", user, id);
        ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

        if (!resultSet.first()) {
            throw new EmptyResultSetException("Goal not found");
        }

        User userEntity = UserDao.getUser(user);
        goal = new Goal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION), resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED), resultSet.getDate(Constants.DEADLINE).toLocalDate(), userEntity, resultSet.getInt("Id"));

        return goal;
    }

    public static int getLastUserGoalId(String user) throws EmptyResultSetException, DatabaseException {
        return getLastIdFromSelectedGoalType("goal", user);
    }

    public static void addGoal(Goal goal) throws SQLException {

        Date sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());
        String insertStatement = String.format("INSERT INTO goal (name, description, numberOfSteps, stepsCompleted, deadline, id, user) VALUES ('%s','%s',%s,%s,'%s',%s,'%s');", goal.getName(), goal.getDescription(), goal.getNumberOfSteps(), goal.getStepsCompleted(), sqlDeadline, goal.getId(), goal.getUser().getUsername());
        DaoUtils.executeUpdate(insertStatement);
    }

    public static void updateStepsGoal(int stepsCompleted, int id, String user) throws DatabaseException {

        try {

            String updateStatement = String.format("UPDATE  goal set stepsCompleted=%s WHERE id = %s AND user='%s';", stepsCompleted, id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException("Can't update Goal in database");

        }
    }
}
