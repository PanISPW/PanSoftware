package com.pansoftware.logic;

import com.pansoftware.logic.barcode.BarcodeScanner;
import com.pansoftware.logic.bean.*;
import com.pansoftware.logic.dao.*;
import com.pansoftware.logic.entity.*;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.*;
import com.pansoftware.logic.mail.SendMail;
import com.pansoftware.logic.util.Session;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

// @author Danilo D'Amico

public class ManageGoalController {

    private static void addReminder(String email, LocalDate day) {
        SendMail.sendOnDay(email, day);
    }

    public static void updateSteps(UpdateStepsBean bean) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        String username = bean.getUser();
        int stepsCompleted = bean.getStepsCompleted();
        int numberOfSteps;

        switch (bean.getType()) {
            case ADVICEGOAL -> {
                numberOfSteps = AdviceGoalDao.getAdviceGoal(username, bean.getId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }
                AdviceGoalDao.updateStepsAdviceGoal(stepsCompleted, bean.getId(), username);
            }
            case EVENTGOAL -> {
                numberOfSteps = EventGoalDao.getEventGoal(username, bean.getId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }
                EventGoalDao.updateStepsEventGoal(stepsCompleted, bean.getId(), username);
            }
            default -> {
                numberOfSteps = GoalDao.getGoal(bean.getUser(), bean.getId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }
                GoalDao.updateStepsGoal(stepsCompleted, bean.getId(), username);
            }
        }

    }

    public static List<AdviceGoal> getAdviceGoalList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getAdviceGoalList(Session.getSession().getUser());
    }

    public static List<AdviceGoal> getUnansweredMakeupAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getUnansweredMakeupAdvice();
    }

    public static List<AdviceGoal> getUnansweredFoodAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getUnansweredFoodAdvice();
    }

    public static List<AdviceGoal> getUnansweredLifestyleAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getUnansweredLifestyleAdvice();
    }

    public static List<AdviceGoal> getUnansweredOtherAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getUnansweredOtherAdvice();
    }

    public static List<Goal> getGoalList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return GoalDao.getGoalList(Session.getSession().getUser());
    }

    public static List<EventGoal> getEventGoalList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return EventGoalDao.getEventGoalList(Session.getSession().getUser());
    }

    public static List<Event> getEventList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return EventDao.getEventList();
    }

    public static List<EventGoal> getPendingEventGoalList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return JoinEventController.getPendingEventGoalList();
    }

    public static BarcodeBean getBarcode() {
        return BarcodeScanner.getBarcode();
    }

    public static void updateProductType(EventGoalBean bean, int id) throws DatabaseException {
        AdviceGoalDao.updateProductTypeAdviceGoal(bean.getType(), id, Session.getSession().getUser());
    }

    public static void answerAdviceGoal(AnswerAdviceGoalBean bean) throws UserNotFoundException, LoginException, DatabaseException, NotEnoughPermissionsException, SQLException {
        UserRole role = UserDao.getUser(Session.getSession().getUser()).getRole();

        if (role.equals(UserRole.USER))
            throw new NotEnoughPermissionsException("Your type of user cannot perform this action");

        AdviceGoalDao.answerAdviceGoal(bean.getId(), bean.getUser(), Session.getSession().getUser(), bean.getAnswer());
    }

    public static void insertBarcode(BarcodeBean bean) throws DatabaseException {
        AdviceGoalDao.insertBarcode(bean.getBarcode(), bean.getId(), Session.getSession().getUser());
    }

    public static void joinEvent(EventGoalBean bean, int id) throws UserNotFoundException, LoginException, DatabaseException, EmptyResultSetException {
        User userEntity = UserDao.getUser(Session.getSession().getUser());
        Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());

        new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), id, userEntity, eventEntity, EventRequestState.STARTING);
    }

    public static void acceptEventGoal(EventGoalBean bean) throws UserNotFoundException, NoTransitionException, NotEnoughPermissionsException, LoginException, DatabaseException, EmptyResultSetException {


        try{
            JoinEventController.acceptJoinRequest(bean);
            EventGoalDao.acceptEventGoal(bean.getId(), bean.getUser());
        } catch(NoTransitionException e){
            throw new NoTransitionException("Event Goal not accepted");
        }
    }

    public static void rejectEventGoal(EventGoalBean bean) throws UserNotFoundException, NoTransitionException, NotEnoughPermissionsException, LoginException, DatabaseException, EmptyResultSetException {

        try{
            JoinEventController.rejectJoinRequest(bean);
            EventGoalDao.rejectEventGoal(bean.getId(), bean.getUser());
        } catch(NoTransitionException e){
            throw new NoTransitionException("Event Goal not rejected");
        }
    }

    public static User getCurrentUser() throws UserNotFoundException, LoginException, DatabaseException {
        String user = Session.getSession().getUser();

        return UserDao.getUser(user);
    }

    public void createGoal(GoalBean bean) throws UserNotFoundException, LoginException, DatabaseException, SQLException, EmptyResultSetException {
        User user = UserDao.getUser(Session.getSession().getUser());
        GoalFactory.getGoalFactory().createGoal(bean);

        if (bean.isReminder()) {
            addReminder(user.getEmail(), bean.getDeadline());
        }
    }

    public static Goal getGoal(String user, int id) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return GoalDao.getGoal(user, id);
    }

}
