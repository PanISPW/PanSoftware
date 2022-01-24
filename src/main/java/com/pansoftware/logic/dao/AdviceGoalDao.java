package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.AdviceGoal;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.DatabaseConnection;
import com.pansoftware.logic.util.Constants;
import com.pansoftware.logic.util.DaoUtils;
import com.pansoftware.logic.util.DataValidation;

import javax.security.auth.login.LoginException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// @author Danilo D'Amico

public class AdviceGoalDao {

    private AdviceGoalDao() {
    }

    public static List<AdviceGoal> getAdviceGoalList(String user) throws UserNotFoundException, EmptyResultSetException, SQLException, LoginException, DatabaseException {

        Statement statement;
        ResultSet resultSet;
        DatabaseConnection databaseConnection;
        List<AdviceGoal> goalList;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        String sql = String.format("SELECT * FROM advicegoal WHERE user = '%s';", user);
        resultSet =  statement.executeQuery(sql);

        if (!resultSet.first()) {
            throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(user);
            ProductType productType = DaoUtils.databaseIntToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
            LocalDate deadline;

            try {
                deadline = resultSet.getDate(Constants.DEADLINE).toLocalDate();
            } catch (Exception e) { // generalizzare ogni volta che chiamo una data
                deadline = DataValidation.setDefaultDate();
            }

            AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                    resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                    deadline, resultSet.getInt("Id"), userEntity, productType,
                    resultSet.getString(Constants.ADVICE), null /*activist*/);
            goalList.add(singleGoal);
        }

        return goalList;

    }

    public static AdviceGoal getAdviceGoal(String user, int id) throws UserNotFoundException, EmptyResultSetException, SQLException, LoginException, DatabaseException {

        Statement statement;
        ResultSet resultSet;
        DatabaseConnection databaseConnection;
        AdviceGoal goal;
        User activistEntity;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        String sql = String.format("SELECT * FROM advicegoal WHERE user = '%s' and id=%s;", user, id);
        resultSet = statement.executeQuery(sql);

        if (!resultSet.first()) {
            throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
        }

        ProductType productType = DaoUtils.databaseIntToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
        User userEntity = UserDao.getUser(user);

        try {
            activistEntity = UserDao.getUser(resultSet.getString("adviceActivist"));
        } catch (Exception e) {
            activistEntity = null;
        }

        goal = new AdviceGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("Id"), userEntity, productType,
                resultSet.getString(Constants.ADVICE), activistEntity);

        return goal;

    }

    public static int getLastUserAdviceGoalId(String user) throws EmptyResultSetException, DatabaseException {

        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = null;
        int lastId;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            String sql = String.format("SELECT MAX(Id) as maxId FROM advicegoal WHERE user = '%s';", user);
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

    public static void addAdviceGoal(AdviceGoal goal) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        java.sql.Date sqlDeadline;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            int typeInt = DaoUtils.productTypeToDatabaseInt(goal.getType());
            sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());

            String insertStatement = String.format("INSERT INTO advicegoal (name, description, numberOfSteps, stepsCompleted, deadline, id, user, productType, productBarcode, advice, adviceActivist) "
                    + "VALUES ('%s','%s',%s,%s,'%s',%s,'%s',%s,NULL,NULL,NULL);", goal.getName(), goal.getDescription(), goal.getNumberOfSteps(), goal.getStepsCompleted(), sqlDeadline, goal.getId(), goal.getUser().getUsername(), typeInt);
            statement.executeUpdate(insertStatement);

        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Goal in database");

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeStatement(statement);
        }

    }

    public static void updateStepsAdviceGoal(int stepsCompleted, int id, String user) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            String updateStatement = String.format("UPDATE advicegoal set stepsCompleted=%s WHERE id = %s AND user = '%s';", stepsCompleted, id, user);
            statement.executeUpdate(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_ADVICE_GOAL_IN_DATABASE);

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeStatement(statement);
        }

    }

    public static void insertBarcode(String barcode, int id, String user) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;

        try {
            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            String updateStatement = String.format("UPDATE advicegoal set barcode='%s' WHERE id = %s AND user = '%s';", barcode, id, user);
            statement.executeUpdate(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_ADVICE_GOAL_IN_DATABASE);

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeStatement(statement);
        }

    }

    public static void updateProductTypeAdviceGoal(ProductType type, int id, String user) throws DatabaseException {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;

        try {
            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            int typeInt = DaoUtils.productTypeToDatabaseInt(type);

            String updateStatement = String.format("UPDATE advicegoal set productType=%s WHERE Id=%s AND user='%s';", typeInt, id, user);
            statement.executeUpdate(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_ADVICE_GOAL_IN_DATABASE);

        } finally {
            assert databaseConnection != null;
            databaseConnection.closeStatement(statement);
        }

    }

    public static void answerAdviceGoal(int id, String user, String activist, String advice) throws SQLException {

        DatabaseConnection databaseConnection;
        Statement statement;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        String updateStatement = String.format("UPDATE advicegoal SET advice='%s', adviceActivist='%s' WHERE id=%s AND user='%s';", advice, activist, id, user);
        statement.executeUpdate(updateStatement);
    }


    // MAKEUP
    public static List<AdviceGoal> getUnansweredMakeupAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {

        Statement statement;
        ResultSet resultSet;
        DatabaseConnection databaseConnection;
        List<AdviceGoal> goalList;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        // 0 = MAKEUP
        resultSet = statement.executeQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=0;");

        if (!resultSet.first()) {
            throw new EmptyResultSetException("No unanswered makeup advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = ProductType.MAKEUP;


            if (!resultSet.first()) {
                throw new EmptyResultSetException("No unanswered makeup advice was found");
            }

            getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);

        }

        return goalList;
    }

    private static void getAdviceGoalFromResultSet(ResultSet resultSet, List<AdviceGoal> goalList, User userEntity, ProductType productType) throws SQLException {
        LocalDate deadline;

        try {
            deadline = resultSet.getDate(Constants.DEADLINE).toLocalDate();
        } catch (Exception e) {
            deadline = DataValidation.setDefaultDate();
        }

        AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                deadline, resultSet.getInt("Id"), userEntity, productType,
                null, null);
        goalList.add(singleGoal);
    }

    // FOOD
    public static List<AdviceGoal> getUnansweredFoodAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {

        Statement statement;
        ResultSet resultSet;
        DatabaseConnection databaseConnection;
        List<AdviceGoal> goalList;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        // 1 = FOOD
        resultSet = statement.executeQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=1;");

        if (!resultSet.first()) {
            throw new EmptyResultSetException("No unanswered food advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = ProductType.FOOD;
            getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);
        }

        return goalList;
    }

    // LIFESTYLE
    public static List<AdviceGoal> getUnansweredLifestyleAdvice() throws UserNotFoundException, EmptyResultSetException, SQLException, LoginException, DatabaseException {

        Statement statement;
        ResultSet resultSet;
        DatabaseConnection databaseConnection;
        List<AdviceGoal> goalList;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        // 2 = LIFESTYLE
        resultSet = statement.executeQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=2;");

        if (!resultSet.first()) {
            throw new EmptyResultSetException("No unanswered lifestyle advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = ProductType.LIFESTYLE;
            getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);
        }

        return goalList;

    }

    // OTHER & NOTSPECIFIED
    public static List<AdviceGoal> getUnansweredOtherAdvice() throws UserNotFoundException, EmptyResultSetException, SQLException, LoginException, DatabaseException {

        Statement statement;
        ResultSet resultSet;
        DatabaseConnection databaseConnection;
        List<AdviceGoal> goalList;

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();

        // 3 = OTHER & 4 = NOTSPECIFIED
        resultSet = statement.executeQuery("SELECT * FROM advicegoal WHERE advice is null AND (productType=3 OR productType=4);");

        if (!resultSet.first()) {
            throw new EmptyResultSetException("No unanswered other advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {
            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = DaoUtils.databaseIntToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
            getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);
        }

        return goalList;
    }


}
