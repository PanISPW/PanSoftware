package logic.persistance.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// @author Danilo D'Amico

public class SimpleQueries {

    public static ResultSet checkUserCredentials(Statement stmt, String username, String password) throws SQLException {
        String sql = String.format("SELECT * FROM user WHERE username='%s' AND password ='%s';", username, password);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getUser(Statement stmt, String username) throws SQLException {
        String sql = String.format("SELECT * FROM user WHERE username = '%s';", username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getAdviceGoalList(Statement stmt, String username) throws SQLException {
        String sql = String.format("SELECT * FROM advicegoal WHERE user = '%s';", username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getEventGoalList(Statement stmt, String username) throws SQLException {
        String sql = String.format("SELECT * FROM eventgoal WHERE user = '%s';", username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getGoalList(Statement stmt, String username) throws SQLException {
        System.out.println("user: " + username); //
        String sql = String.format("SELECT * FROM goal WHERE user = '%s';", username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getGoal(Statement stmt, String username, int id) throws SQLException {
        String sql = String.format("SELECT * FROM goal WHERE user = '%s' and id=%s;", username, id);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getAdviceGoal(Statement stmt, String username, int id) throws SQLException {
        String sql = String.format("SELECT * FROM advicegoal WHERE user = '%s' and id=%s;", username, id);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getEventGoal(Statement stmt, String username, int id) throws SQLException {
        String sql = String.format("SELECT * FROM eventgoal WHERE user = '%s' and id=%s;", username, id);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getEventList(Statement stmt) throws SQLException {
        String sql = String.format("SELECT * FROM event;");
        return stmt.executeQuery(sql);
    }

    public static ResultSet getEvent(Statement stmt, int id, String organizer) throws SQLException {
        String sql = String.format("SELECT * FROM event WHERE id = %s AND organizer = '%s';", id, organizer);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getPendingEventApprovalList(Statement stmt, String username) throws SQLException {
        // 0 = PENDING
        String sql = String.format("SELECT * FROM eventgoal WHERE eventOrganizer = '%s' AND requestState = 0;", username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getLastUserGoalId(Statement stmt, String username) throws SQLException {
        String sql = String.format("SELECT MAX(Id) as maxId FROM goal WHERE user='%s';", username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getLastUserAdviceGoalId(Statement stmt, String username) throws SQLException {
        String sql = String.format("SELECT MAX(Id) as maxId FROM advicegoal WHERE user = '%s';", username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getLastUserEventGoalId(Statement stmt, String username) throws SQLException {
        String sql = String.format("SELECT MAX(Id) as maxId FROM eventgoal WHERE user = '%s';", username);
        return stmt.executeQuery(sql);
    }

    public static ResultSet getUnansweredMakeupAdvice(Statement stmt) throws SQLException {
        // 0 = MAKEUP
        String sql = String.format("SELECT * FROM advicegoal WHERE advice is null AND productType=0;");
        return stmt.executeQuery(sql);
    }

    public static ResultSet getUnansweredFoodAdvice(Statement stmt) throws SQLException {
        // 1 = FOOD
        String sql = String.format("SELECT * FROM advicegoal WHERE advice is null AND productType=1;");
        return stmt.executeQuery(sql);
    }

    public static ResultSet getUnansweredLifestyleAdvice(Statement stmt) throws SQLException {
        // 2 = LIFESTYLE
        String sql = String.format("SELECT * FROM advicegoal WHERE advice is null AND productType=2;");
        return stmt.executeQuery(sql);
    }

    public static ResultSet getUnansweredOtherAdvice(Statement stmt) throws SQLException {
        // 3 = OTHER & 4 = NOTSPECIFIED
        String sql = String.format("SELECT * FROM advicegoal WHERE advice is null AND (productType=3 OR productType=4);");
        return stmt.executeQuery(sql);
    }

}
