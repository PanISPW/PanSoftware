package logic.dao;

import logic.bean.GoalQueryBean;
import logic.entity.Goal;
import logic.entity.User;
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
import java.util.ArrayList;
import java.util.List;

// @author Danilo D'Amico

public class GoalDao {

    private GoalDao(){}

    public static List<Goal> getGoalList(String user) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Goal> goalList;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getGoalList(statement, user);

        if (!resultSet.first()) {
            throw new Exception(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
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

    public static Goal getGoal(String user, int id) throws UserNotFoundException, Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Goal goal;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getGoal(statement, user, id);

        if (!resultSet.first()) {
            throw new Exception("Goal not found");
        }

        User userEntity = UserDao.getUser(user);
        goal = new Goal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION), resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED), resultSet.getDate(Constants.DEADLINE).toLocalDate(), userEntity, resultSet.getInt("Id"));

        return goal;

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

    public static int addGoal(Goal goal) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            Date sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());

            GoalQueryBean bean = DaoUtils.getGoalQueryBean(goal, sqlDeadline);

            result = CRUDQueries.addGoal(statement, bean);

            return result;

        } finally {
            databaseConnection.closeStatement(statement);
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
            databaseConnection.closeStatement(statement);
        }

    }

}
