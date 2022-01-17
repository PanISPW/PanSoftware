package logic.persistance.queries;

import logic.bean.AdviceGoalQueryBean;
import logic.bean.EventGoalQueryBean;
import logic.bean.GoalQueryBean;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

// @author Danilo D'Amico

public class CRUDQueries {

    private CRUDQueries(){}

    public static int addGoal(Statement stmt, GoalQueryBean bean) throws SQLException {
        String insertStatement = String.format("INSERT INTO goal (name, description, numberOfSteps, stepsCompleted, deadline, id, user) VALUES ('%s','%s',%s,%s,'%s',%s,'%s');", bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), bean.getId(), bean.getUser());
        return stmt.executeUpdate(insertStatement);
    }

    public static int addUser(Statement stmt, String username, String password, String email, String name, String surname, int role) throws SQLException {
        String insertStatement = String.format("INSERT INTO user (username, password, email, name, surname, role) VALUES ('%s','%s','%s','%s','%s',%s);", username, password, email, name, surname, role);
        return stmt.executeUpdate(insertStatement);
    }

    public static int updateStepsGoal(Statement stmt, int stepsCompleted, int id, String user) throws SQLException {
        String updateStatement = String.format("UPDATE  goal set stepsCompleted=%s WHERE id = %s AND user = '%s';", stepsCompleted, id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int addAdviceGoal(Statement stmt, AdviceGoalQueryBean bean) throws SQLException {
        String insertStatement = String.format("INSERT INTO advicegoal (name, description, numberOfSteps, stepsCompleted, deadline, id, user, productType, productBarcode, advice, adviceActivist) "
                + "VALUES ('%s','%s',%s,%s,'%s',%s,'%s',%s,NULL,NULL,NULL);", bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), bean.getId(), bean.getUser(), bean.getProductType());
        return stmt.executeUpdate(insertStatement);
    }

    public static int updateStepsAdviceGoal(Statement stmt, int stepsCompleted, int id, String user) throws SQLException {
        String updateStatement = String.format("UPDATE advicegoal set stepsCompleted=%s WHERE id = %s AND user = '%s';", stepsCompleted, id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int insertBarcodeAdviceGoal(Statement stmt, String barcode, int id, String user) throws SQLException {
        String updateStatement = String.format("UPDATE advicegoal set barcode='%s' WHERE id = %s AND user = '%s';", barcode, id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int updateProductTypeAdviceGoal(Statement stmt, int type, int id, String user) throws SQLException {

        String updateStatement = String.format("UPDATE advicegoal set productType=%s WHERE Id=%s AND user='%s';", type, id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int addEventGoal(Statement stmt, EventGoalQueryBean bean) throws SQLException {

        String insertStatement = String.format("INSERT INTO eventgoal (name, description, numberOfSteps, stepsCompleted, deadline, id, user, eventOrganizer, eventId, requestState) VALUES ('%s','%s',%s,%s,'%s',%s,'%s','%s',%s,%s);", bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), bean.getId(), bean.getUser(), bean.getEventOrganizer(), bean.getEventId(), bean.getRequestState());
        return stmt.executeUpdate(insertStatement);
    }

    public static int updateStepsEventGoal(Statement stmt, int stepsCompleted, int id, String user) throws SQLException {
        String updateStatement = String.format("UPDATE  eventgoal set stepsCompleted=%s WHERE id = %s AND user = '%s';", stepsCompleted, id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int addEvent(Statement stmt, int id, String organizer, String name, Date startingDate, Date endingDate, int eventType) throws SQLException {
        String insertStatement = String.format("INSERT INTO event (id, organizer, name, startingDate, endingDate, private) VALUES (%s,'%s','%s','%s','%s',%s);", id, organizer, name, startingDate, endingDate, eventType);
        return stmt.executeUpdate(insertStatement);
    }

    public static int answerAdviceGoal(Statement stmt, String advice, String activist, int id, String user) throws SQLException {

        String updateStatement = String.format("UPDATE advicegoal SET advice='%s', adviceActivist='%s' WHERE id=%s AND user='%s';", advice, activist, id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int acceptEventParticipation(Statement stmt, int id, String user) throws SQLException {
        String updateStatement = String.format("UPDATE eventgoal SET requestState=1 WHERE id=%s AND user='%s';", id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int rejectEventParticipation(Statement stmt, int id, String user) throws SQLException {
        String updateStatement = String.format("UPDATE eventgoal SET requestState=2 WHERE id=%s AND user = '%s';", id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int pendingEventParticipation(Statement stmt, int id, String user) throws SQLException {
        String updateStatement = String.format("UPDATE  eventgoal set requestState=0 WHERE id = %s AND user = '%s';", id, user);
        return stmt.executeUpdate(updateStatement);
    }

    public static int joinEvent(Statement stmt, int eventId, String eventOrganizer, int state, int goalId, String user) throws SQLException {
        String updateStatement = String.format("UPDATE eventgoal set eventId = %s and eventOrganizer = '%s' and requestState = %s WHERE id = %s AND user = '%s';", eventId, eventOrganizer, state, goalId, user);
        return stmt.executeUpdate(updateStatement);
    }

}
