package com.pansoftware.logic.dao;

import com.pansoftware.logic.entity.AdviceGoal;
import com.pansoftware.logic.entity.User;
import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.Constants;
import com.pansoftware.logic.util.DaoUtils;
import com.pansoftware.logic.util.DataValidation;
import com.pansoftware.logic.util.DatabaseConnection;

import javax.security.auth.login.LoginException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.pansoftware.logic.util.DaoUtils.getLastIdFromSelectedGoalType;

// @author Danilo D'Amico

public class AdviceGoalDao {

    private AdviceGoalDao() {
    }

    public static List<AdviceGoal> getAdviceGoalList(String user) throws UserNotFoundException, EmptyResultSetException, SQLException, LoginException, DatabaseException {

        List<AdviceGoal> goalList;

        String sql = String.format("SELECT * FROM advicegoal WHERE user = '%s';", user);
        ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

        if (!resultSet.first()) {
            throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(user);
            ProductType productType = DaoUtils.intToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
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

        DatabaseConnection.closeResultSet(resultSet);
        return goalList;

    }

    public static AdviceGoal getAdviceGoal(String user, int id) throws UserNotFoundException, EmptyResultSetException, SQLException, LoginException, DatabaseException {

        AdviceGoal goal;
        User activistEntity;

        String sql = String.format("SELECT * FROM advicegoal WHERE user = '%s' and id=%s;", user, id);
        ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

        if (!resultSet.first()) {
            throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
        }

        ProductType productType = DaoUtils.intToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
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

        DatabaseConnection.closeResultSet(resultSet);
        return goal;
    }

    public static int getLastUserAdviceGoalId(String user) throws DatabaseException {
        return getLastIdFromSelectedGoalType("advicegoal", user);
    }

    public static void addAdviceGoal(AdviceGoal goal) throws DatabaseException {

        java.sql.Date sqlDeadline;

        try {

            int typeInt = DaoUtils.productTypeToInt(goal.getType());
            sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());

            String insertStatement = String.format("INSERT INTO advicegoal (name, description, numberOfSteps, stepsCompleted, deadline, id, user, productType, productBarcode, advice, adviceActivist) "
                    + "VALUES ('%s','%s',%s,%s,'%s',%s,'%s',%s,NULL,NULL,NULL);", goal.getName(), goal.getDescription(), goal.getNumberOfSteps(), goal.getStepsCompleted(), sqlDeadline, goal.getId(), goal.getUser().getUsername(), typeInt);
            DaoUtils.executeUpdate(insertStatement);

        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Goal in database");

        }
    }

    public static void updateStepsAdviceGoal(int stepsCompleted, int id, String user) throws DatabaseException {

        try {

            String updateStatement = String.format("UPDATE advicegoal set stepsCompleted=%s WHERE id = %s AND user = '%s';", stepsCompleted, id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_ADVICE_GOAL_IN_DATABASE);

        }
    }

    public static void updateProductTypeAdviceGoal(ProductType type, int id, String user) throws DatabaseException {

        try {

            int typeInt = DaoUtils.productTypeToInt(type);

            String updateStatement = String.format("UPDATE advicegoal set productType=%s WHERE Id=%s AND user='%s';", typeInt, id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_ADVICE_GOAL_IN_DATABASE);

        }
    }

    public static void answerAdviceGoal(int id, String user, String activist, String advice) throws SQLException, DatabaseException {
        String updateStatement = String.format("UPDATE advicegoal SET advice='%s', adviceActivist='%s' WHERE id=%s AND user='%s';", advice, activist, id, user);
        DaoUtils.executeUpdate(updateStatement);
    }


    // MAKEUP
    public static List<AdviceGoal> getUnansweredMakeupAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        List<AdviceGoal> goalList;

        // 0 = MAKEUP
        ResultSet resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=0;");

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

        DatabaseConnection.closeResultSet(resultSet);
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

        List<AdviceGoal> goalList;

        // 1 = FOOD
        ResultSet resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=1;");

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

        DatabaseConnection.closeResultSet(resultSet);
        return goalList;
    }

    // LIFESTYLE
    public static List<AdviceGoal> getUnansweredLifestyleAdvice() throws UserNotFoundException, EmptyResultSetException, SQLException, LoginException, DatabaseException {

        List<AdviceGoal> goalList;

        // 2 = LIFESTYLE
        ResultSet resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=2;");

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

        DatabaseConnection.closeResultSet(resultSet);
        return goalList;
    }

    // OTHER & NOTSPECIFIED
    public static List<AdviceGoal> getUnansweredOtherAdvice() throws UserNotFoundException, EmptyResultSetException, SQLException, LoginException, DatabaseException {

        List<AdviceGoal> goalList;

        // 3 = OTHER & 4 = NOTSPECIFIED
        ResultSet resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM advicegoal WHERE advice is null AND (productType=3 OR productType=4);");

        if (!resultSet.first()) {
            throw new EmptyResultSetException("No unanswered other advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {
            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = DaoUtils.intToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
            getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);
        }

        DatabaseConnection.closeResultSet(resultSet);
        return goalList;
    }
}
