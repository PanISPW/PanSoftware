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

    public static List<AdviceGoal> getAdviceGoalList(final String user) throws EmptyResultSetException, DatabaseException {
        try {
            final List<AdviceGoal> goalList;

            final String sql = String.format("SELECT * FROM advicegoal WHERE user = '%s';", user);
            final ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                final User userEntity = UserDao.getUser(user);
                final ProductType productType = DaoUtils.intToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
                LocalDate deadline;

                try {
                    deadline = resultSet.getDate(Constants.DEADLINE).toLocalDate();
                } catch (final Exception e) {
                    deadline = DataValidation.setDefaultDate();
                }

                final AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                        resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                        deadline, resultSet.getInt("Id"), userEntity, productType,
                        resultSet.getString(Constants.ADVICE), null /*activist*/);
                goalList.add(singleGoal);
            }

            DatabaseConnection.closeResultSet(resultSet);
            return goalList;
        } catch(final SQLException e){
            throw new DatabaseException("can't retrieve list of Advice Goals");
        }

    }

    public static AdviceGoal getAdviceGoal(final String user, final int id) throws EmptyResultSetException, DatabaseException {
        try {
            final AdviceGoal goal;
            User activistEntity;

            final String sql = String.format("SELECT * FROM advicegoal WHERE user = '%s' and id=%s;", user, id);
            final ResultSet resultSet = DaoUtils.executeCRUDQuery(sql);

            if (!resultSet.first()) {
                throw new EmptyResultSetException(Constants.NO_GOAL_RELATED_TO_THE_USER_WAS_FOUND);
            }

            final ProductType productType = DaoUtils.intToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
            final User userEntity = UserDao.getUser(user);

            try {
                activistEntity = UserDao.getUser(resultSet.getString("adviceActivist"));
            } catch (final Exception e) {
                activistEntity = null;
            }

            goal = new AdviceGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                    resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                    resultSet.getDate(Constants.DEADLINE).toLocalDate(), resultSet.getInt("Id"), userEntity, productType,
                    resultSet.getString(Constants.ADVICE), activistEntity);

            DatabaseConnection.closeResultSet(resultSet);
            return goal;
        } catch(final SQLException e){
            throw new DatabaseException("Can't retrieve Advice Goal");
        }
    }

    public static int getLastUserAdviceGoalId(final String user) throws DatabaseException {
        return getLastIdFromSelectedGoalType("advicegoal", user);
    }

    public static void addAdviceGoal(final AdviceGoal goal) throws DatabaseException {

        final java.sql.Date sqlDeadline;

        try {

            final int typeInt = DaoUtils.productTypeToInt(goal.getType());
            sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(goal.getDeadline());

            final String insertStatement = String.format("INSERT INTO advicegoal (name, description, numberOfSteps, stepsCompleted, deadline, id, user, productType, productBarcode, advice, adviceActivist) "
                    + "VALUES ('%s','%s',%s,%s,'%s',%s,'%s',%s,NULL,NULL,NULL);", goal.getName(), goal.getDescription(), goal.getNumberOfSteps(), goal.getStepsCompleted(), sqlDeadline, goal.getId(), goal.getUser().getUsername(), typeInt);
            DaoUtils.executeUpdate(insertStatement);

        } catch (final SQLException e) {

            throw new DatabaseException("Can't insert new Goal in database");

        }
    }

    public static void updateStepsAdviceGoal(final int stepsCompleted, final int id, final String user) throws DatabaseException {

        try {

            final String updateStatement = String.format("UPDATE advicegoal set stepsCompleted=%s WHERE id = %s AND user = '%s';", stepsCompleted, id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (final SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_ADVICE_GOAL_IN_DATABASE);

        }
    }

    public static void updateProductTypeAdviceGoal(final ProductType type, final int id, final String user) throws DatabaseException {

        try {

            final int typeInt = DaoUtils.productTypeToInt(type);

            final String updateStatement = String.format("UPDATE advicegoal set productType=%s WHERE Id=%s AND user='%s';", typeInt, id, user);
            DaoUtils.executeUpdate(updateStatement);

        } catch (final SQLException e) {

            throw new DatabaseException(Constants.CAN_T_UPDATE_ADVICE_GOAL_IN_DATABASE);

        }
    }

    public static void answerAdviceGoal(final int id, final String user, final String activist, final String advice) throws DatabaseException {
        try {
            final String updateStatement = String.format("UPDATE advicegoal SET advice='%s', adviceActivist='%s' WHERE id=%s AND user='%s';", advice, activist, id, user);
            DaoUtils.executeUpdate(updateStatement);
        } catch(final SQLException e){
            throw new DatabaseException("can't answer advice");
            }
        }


    // MAKEUP
    public static List<AdviceGoal> getUnansweredMakeupAdvice() throws EmptyResultSetException, DatabaseException {
        try {
            final List<AdviceGoal> goalList;

            // 0 = MAKEUP
            final ResultSet resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=0;");

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No unanswered makeup advice was found");
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                final User userEntity = UserDao.getUser(resultSet.getString("user"));
                final ProductType productType = ProductType.MAKEUP;


                if (!resultSet.first()) {
                    throw new EmptyResultSetException("No unanswered makeup advice was found");
                }

                AdviceGoalDao.getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);

            }

            DatabaseConnection.closeResultSet(resultSet);
            return goalList;
        } catch(final SQLException e){
            throw new DatabaseException("can't retrieve Makeup-related Advice Goals");
        }
    }

    private static void getAdviceGoalFromResultSet(final ResultSet resultSet, final List<AdviceGoal> goalList, final User userEntity, final ProductType productType) throws DatabaseException {
        try {
            LocalDate deadline;

            try {
                deadline = resultSet.getDate(Constants.DEADLINE).toLocalDate();
            } catch (final Exception e) {
                deadline = DataValidation.setDefaultDate();
            }

            final AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString(Constants.DESCRIPTION),
                    resultSet.getInt(Constants.NUMBER_OF_STEPS), resultSet.getInt(Constants.STEPS_COMPLETED),
                    deadline, resultSet.getInt("Id"), userEntity, productType,
                    null, null);
            goalList.add(singleGoal);
        } catch(final SQLException e){
            throw new DatabaseException("can't map Advice Goal to appropriate object");
        }
    }

    // FOOD
    public static List<AdviceGoal> getUnansweredFoodAdvice() throws EmptyResultSetException, DatabaseException {
        try {

            final List<AdviceGoal> goalList;

            // 1 = FOOD
            final ResultSet resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=1;");

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No unanswered food advice was found");
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                final User userEntity = UserDao.getUser(resultSet.getString("user"));
                final ProductType productType = ProductType.FOOD;
                AdviceGoalDao.getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);
            }

            DatabaseConnection.closeResultSet(resultSet);
            return goalList;
        } catch(final SQLException e){
            throw new DatabaseException("can't retrieve Food-related Advice Goals");
        }
    }

    // LIFESTYLE
    public static List<AdviceGoal> getUnansweredLifestyleAdvice() throws EmptyResultSetException, DatabaseException {
        try {

            final List<AdviceGoal> goalList;

            // 2 = LIFESTYLE
            final ResultSet resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM advicegoal WHERE advice is null AND productType=2;");

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No unanswered lifestyle advice was found");
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {

                final User userEntity = UserDao.getUser(resultSet.getString("user"));
                final ProductType productType = ProductType.LIFESTYLE;
                AdviceGoalDao.getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);
            }

            DatabaseConnection.closeResultSet(resultSet);
            return goalList;
        } catch (final SQLException e){
            throw new DatabaseException("can't retrieve Lifestyle-related Advice Goals");
        }
    }

    // OTHER & NOTSPECIFIED
    public static List<AdviceGoal> getUnansweredOtherAdvice() throws EmptyResultSetException, DatabaseException {
        try {

            final List<AdviceGoal> goalList;

            // 3 = OTHER & 4 = NOTSPECIFIED
            final ResultSet resultSet = DaoUtils.executeCRUDQuery("SELECT * FROM advicegoal WHERE advice is null AND (productType=3 OR productType=4);");

            if (!resultSet.first()) {
                throw new EmptyResultSetException("No unanswered other advice was found");
            }

            goalList = new ArrayList<>();

            resultSet.beforeFirst();

            while (resultSet.next()) {
                final User userEntity = UserDao.getUser(resultSet.getString("user"));
                final ProductType productType = DaoUtils.intToProductType(resultSet.getInt(Constants.PRODUCT_TYPE));
                AdviceGoalDao.getAdviceGoalFromResultSet(resultSet, goalList, userEntity, productType);
            }

            DatabaseConnection.closeResultSet(resultSet);
            return goalList;
        }catch (final SQLException e){
            throw new DatabaseException("can't retrieve Other Advice Goals");
        }
    }
}
