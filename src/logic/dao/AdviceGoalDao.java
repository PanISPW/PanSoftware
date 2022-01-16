package logic.dao;

import logic.entity.AdviceGoal;
import logic.entity.User;
import logic.enumeration.ProductType;
import logic.exception.DatabaseException;
import logic.exception.EmptyResultSetException;
import logic.exception.UserNotFoundException;
import logic.persistance.DatabaseConnection;
import logic.persistance.queries.CRUDQueries;
import logic.persistance.queries.SimpleQueries;
import logic.util.DaoUtils;
import logic.util.DataValidation;

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

    public static List<AdviceGoal> getAdviceGoalList(String user) throws UserNotFoundException, Exception {

        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = null;
        List<AdviceGoal> goalList;

        //try {

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getAdviceGoalList(statement, user);

        if (!resultSet.first()) {
            throw new Exception("No Goal related to the User was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(user);
            ProductType productType = DaoUtils.databaseIntToProductType(resultSet.getInt("productType"));
            LocalDate deadline;

            // activist null
            //User activistEntity = UserDao.getUser(resultSet.getString("adviceActivist")); //?
            try {
                deadline = resultSet.getDate("deadline").toLocalDate();
            } catch (Exception e) { // generalizzare ogni volta che chiamo una data
                deadline = DataValidation.setDefaultDate();
            }

            AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString("description"),
                    resultSet.getInt("numberOfSteps"), resultSet.getInt("stepsCompleted"),
                    deadline, resultSet.getInt("Id"), userEntity, productType,
                    resultSet.getString("advice"), null /*activist*/);
            goalList.add(singleGoal);
        }

        return goalList;

        //} catch (SQLException | ClassNotFoundException e) {

        //	throw new DatabaseException("Can't retrieve data from database");

        //} finally {
//			if(databaseConnection!=null) {
//				databaseConnection.closeResultSet(resultSet);
//				databaseConnection.closeStatement(statement);
//			}
//
//		}

    }

    public static AdviceGoal getAdviceGoal(String user, int id) throws UserNotFoundException, Exception {

        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = null;
        AdviceGoal goal;
        User activistEntity;

        //try {

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getAdviceGoal(statement, user, id);

        if (!resultSet.first()) {
            throw new Exception("No Goal related to the User was found");
        }

        ProductType productType = DaoUtils.databaseIntToProductType(resultSet.getInt("productType"));
        User userEntity = UserDao.getUser(user);

        try {
            activistEntity = UserDao.getUser(resultSet.getString("adviceActivist"));
        } catch (Exception e) {
            activistEntity = null;
        }

        goal = new AdviceGoal(resultSet.getString("name"), resultSet.getString("description"),
                resultSet.getInt("numberOfSteps"), resultSet.getInt("stepsCompleted"),
                resultSet.getDate("deadline").toLocalDate(), resultSet.getInt("Id"), userEntity, productType,
                resultSet.getString("advice"), activistEntity);

        return goal;

//		}catch(SQLException|
//
//	ClassNotFoundException e)
//	{
//
//		throw new DatabaseException("Can't retrieve data from database");
//
//	}finally
//	{
//
//		if(databaseConnection!=null) {
//			databaseConnection.closeResultSet(resultSet);
//			databaseConnection.closeStatement(statement);
//		}
//	}

    }

    public static int getLastUserAdviceGoalId(String user) throws UserNotFoundException, Exception {

        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = null;
        int lastId;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();
            resultSet = SimpleQueries.getLastUserAdviceGoalId(statement, user);

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

    public static int addAdviceGoal(String user, String name, String description, int numberOfSteps, int stepsCompleted,
                                    java.time.LocalDate deadline, int id, ProductType type) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;
        java.sql.Date sqlDeadline;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            int typeInt = DaoUtils.productTypeToDatabaseInt(type);

            sqlDeadline = DaoUtils.localDateToSqlDateOrDefault(deadline);

            result = CRUDQueries.addAdviceGoal(statement, name, description, numberOfSteps, stepsCompleted, sqlDeadline, id, user, typeInt);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException("Can't insert new Goal in database");

        } finally {
            if (databaseConnection != null) {
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static int updateStepsAdviceGoal(int stepsCompleted, int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;

        try {

            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            result = CRUDQueries.updateStepsAdviceGoal(statement, stepsCompleted, id, user);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException("Can't update Advice Goal in database");

        } finally {
            if (databaseConnection != null) {
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static int insertBarcode(String barcode, int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;

        try {
            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            result = CRUDQueries.insertBarcodeAdviceGoal(statement, barcode, id, user);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException("Can't update Advice Goal in database");

        } finally {
            if (databaseConnection != null) {
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static int updateProductTypeAdviceGoal(ProductType type, int id, String user) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;

        try {
            databaseConnection = new DatabaseConnection();
            statement = databaseConnection.createStatement();

            int typeInt = DaoUtils.productTypeToDatabaseInt(type);

            result = CRUDQueries.updateProductTypeAdviceGoal(statement, typeInt, id, user);

            return result;

        } catch (SQLException e) {

            throw new DatabaseException("Can't update Advice Goal in database");

        } finally {
            if (databaseConnection != null) {
                databaseConnection.closeStatement(statement);
            }
        }

    }

    public static int answerAdviceGoal(int id, String user, String activist, String advice) throws Exception {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        int result;

        //try {
        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        result = CRUDQueries.answerAdviceGoal(statement, advice, activist, id, user);

        return result;

        //} catch (SQLException e) {

        //throw new DatabaseException("Can't update Advice Goal in database");

//		} finally {
//			if(databaseConnection!=null) {
//				databaseConnection.closeStatement(statement);
//			}
//		}

    }


    // MAKEUP
    public static List<AdviceGoal> getUnansweredMakeupAdvice() throws UserNotFoundException, Exception {

        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = null;
        List<AdviceGoal> goalList;

        //try {

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getUnansweredMakeupAdvice(statement);

        if (!resultSet.first()) {
            throw new Exception("No unanswered makeup advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = ProductType.MAKEUP;
            LocalDate deadline;

            try {
                deadline = resultSet.getDate("deadline").toLocalDate();
            } catch (Exception e) { // generalizzare ogni volta che chiamo una data
                deadline = DataValidation.setDefaultDate();
            }

            AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString("description"),
                    resultSet.getInt("numberOfSteps"), resultSet.getInt("stepsCompleted"),
                    deadline, resultSet.getInt("Id"), userEntity, productType,
                    null, null);
            goalList.add(singleGoal);
        }

        return goalList;

        //} catch (SQLException | ClassNotFoundException e) {

        //	throw new DatabaseException("Can't retrieve data from database");

        //} finally {
//			if(databaseConnection!=null) {
//				databaseConnection.closeResultSet(resultSet);
//				databaseConnection.closeStatement(statement);
//			}
//
//		}

    }

    // FOOD
    public static List<AdviceGoal> getUnansweredFoodAdvice() throws UserNotFoundException, Exception {

        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = null;
        List<AdviceGoal> goalList;

        //try {

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getUnansweredFoodAdvice(statement);

        if (!resultSet.first()) {
            throw new Exception("No unanswered food advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = ProductType.FOOD;
            LocalDate deadline;

            try {
                deadline = resultSet.getDate("deadline").toLocalDate();
            } catch (Exception e) { // generalizzare ogni volta che chiamo una data
                deadline = DataValidation.setDefaultDate();
            }

            AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString("description"),
                    resultSet.getInt("numberOfSteps"), resultSet.getInt("stepsCompleted"),
                    deadline, resultSet.getInt("Id"), userEntity, productType,
                    null, null);
            goalList.add(singleGoal);
        }

        return goalList;

        //} catch (SQLException | ClassNotFoundException e) {

        //	throw new DatabaseException("Can't retrieve data from database");

        //} finally {
//			if(databaseConnection!=null) {
//				databaseConnection.closeResultSet(resultSet);
//				databaseConnection.closeStatement(statement);
//			}
//
//		}

    }

    // LIFESTYLE
    public static List<AdviceGoal> getUnansweredLifestyleAdvice() throws UserNotFoundException, Exception {

        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = null;
        List<AdviceGoal> goalList;

        //try {

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getUnansweredLifestyleAdvice(statement);

        if (!resultSet.first()) {
            throw new Exception("No unanswered lifestyle advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = ProductType.LIFESTYLE;
            LocalDate deadline;

            try {
                deadline = resultSet.getDate("deadline").toLocalDate();
            } catch (Exception e) { // generalizzare ogni volta che chiamo una data
                deadline = DataValidation.setDefaultDate();
            }

            AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString("description"),
                    resultSet.getInt("numberOfSteps"), resultSet.getInt("stepsCompleted"),
                    deadline, resultSet.getInt("Id"), userEntity, productType,
                    null, null);
            goalList.add(singleGoal);
        }

        return goalList;

        //} catch (SQLException | ClassNotFoundException e) {

        //	throw new DatabaseException("Can't retrieve data from database");

        //} finally {
//			if(databaseConnection!=null) {
//				databaseConnection.closeResultSet(resultSet);
//				databaseConnection.closeStatement(statement);
//			}
//
//		}

    }

    // OTHER & NOTSPECIFIED
    public static List<AdviceGoal> getUnansweredOtherAdvice() throws UserNotFoundException, Exception {

        Statement statement = null;
        ResultSet resultSet = null;
        DatabaseConnection databaseConnection = null;
        List<AdviceGoal> goalList;

        //try {

        databaseConnection = new DatabaseConnection();
        statement = databaseConnection.createStatement();
        resultSet = SimpleQueries.getUnansweredOtherAdvice(statement);

        if (!resultSet.first()) {
            throw new Exception("No unanswered other advice was found");
        }

        goalList = new ArrayList<>();

        resultSet.beforeFirst();

        while (resultSet.next()) {

            User userEntity = UserDao.getUser(resultSet.getString("user"));
            ProductType productType = DaoUtils.databaseIntToProductType(resultSet.getInt("productType"));
            LocalDate deadline;

            // activist null
            //User activistEntity = UserDao.getUser(resultSet.getString("adviceActivist")); //?
            try {
                deadline = resultSet.getDate("deadline").toLocalDate();
            } catch (Exception e) { // generalizzare ogni volta che chiamo una data
                deadline = DataValidation.setDefaultDate();
            }

            AdviceGoal singleGoal = new AdviceGoal(resultSet.getString("name"), resultSet.getString("description"),
                    resultSet.getInt("numberOfSteps"), resultSet.getInt("stepsCompleted"),
                    deadline, resultSet.getInt("Id"), userEntity, productType,
                    resultSet.getString("advice"), null /*activist*/);
            goalList.add(singleGoal);
        }

        return goalList;

        //} catch (SQLException | ClassNotFoundException e) {

        //	throw new DatabaseException("Can't retrieve data from database");

        //} finally {
//			if(databaseConnection!=null) {
//				databaseConnection.closeResultSet(resultSet);
//				databaseConnection.closeStatement(statement);
//			}
//
//		}

    }


}
