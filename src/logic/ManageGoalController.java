package logic;

import logic.barcode.BarcodeScanner;
import logic.bean.AnswerAdviceGoalBean;
import logic.bean.BarcodeBean;
import logic.bean.GoalBean;
import logic.bean.UpdateStepsBean;
import logic.dao.*;
import logic.entity.*;
import logic.enumeration.EventRequestState;
import logic.enumeration.UserRole;
import logic.exception.*;
import logic.mail.SendMail;
import logic.util.Session;

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
        String username = Session.getSession().getUser();
        int stepsCompleted = bean.getStepsCompleted();
        int numberOfSteps;

        switch (bean.getType()) {
            case ADVICEGOAL:

                numberOfSteps = AdviceGoalDao.getAdviceGoal(username, bean.getId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }

                AdviceGoalDao.updateStepsAdviceGoal(stepsCompleted, bean.getId(), username);
                break;
            case EVENTGOAL:

                numberOfSteps = EventGoalDao.getEventGoal(username, bean.getId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }

                EventGoalDao.updateStepsEventGoal(stepsCompleted, bean.getId(), username);
                break;
            default:

                numberOfSteps = GoalDao.getGoal(username, bean.getId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }

                GoalDao.updateStepsGoal(stepsCompleted, bean.getId(), username);
        }

    }

    public static List<AdviceGoal> getAdviceGoalList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getAdviceGoalList(Session.getSession().getUser());
    }

    //
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
        return EventGoalDao.getPendingEventGoalList(Session.getSession().getUser());
    }

    public static BarcodeBean getBarcode() {
        return BarcodeScanner.getBarcode();
    }

    public static void updateProductType(GoalBean bean, int id) throws DatabaseException {
        AdviceGoalDao.updateProductTypeAdviceGoal(bean.getType(), id, Session.getSession().getUser());
    }

    public static void answerAdviceGoal(AnswerAdviceGoalBean bean) throws UserNotFoundException, LoginException, DatabaseException, NotEnoughPermissionsException, SQLException {
        UserRole role = UserDao.getUser(Session.getSession().getUser()).getRole();

        if (role.equals(UserRole.USER))
            throw new NotEnoughPermissionsException();

        AdviceGoalDao.answerAdviceGoal(bean.getId(), bean.getUser(), Session.getSession().getUser(), bean.getAnswer());
    }

    public static void insertBarcode(BarcodeBean bean) throws DatabaseException {
        AdviceGoalDao.insertBarcode(bean.getBarcode(), bean.getId(), Session.getSession().getUser());
    }

    public static void joinEvent(GoalBean bean, int id) throws UserNotFoundException, LoginException, DatabaseException, EmptyResultSetException {
        User userEntity = UserDao.getUser(Session.getSession().getUser());
        Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());

        new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), id, userEntity, eventEntity, EventRequestState.STARTING);
    }

    public static void acceptEventGoal(GoalBean bean) throws UserNotFoundException, NoTransitionException, NotEnoughPermissionsException, LoginException, DatabaseException, EmptyResultSetException {

        if (!bean.getState().equals(EventRequestState.PENDING)) {
            throw new NoTransitionException();
        }

        if (!Session.getSession().getUser().equals(bean.getEventOrganizer())) {
            throw new NotEnoughPermissionsException();
        }

        User userEntity = UserDao.getUser(bean.getUser());
        Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());

        EventGoal goal = new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), bean.getId(), userEntity, eventEntity, bean.getState());
        goal.acceptJoinRequest();

        if (goal.getState().equals(EventRequestState.ACCEPTED))
            EventGoalDao.acceptEventGoal(goal.getId(), goal.getUser().getUsername());


    }

    public static void rejectEventGoal(GoalBean bean) throws UserNotFoundException, NoTransitionException, NotEnoughPermissionsException, LoginException, DatabaseException, EmptyResultSetException {

        if (!bean.getState().equals(EventRequestState.PENDING)) {
            throw new NoTransitionException();
        }

        if (!Session.getSession().getUser().equals(bean.getEventOrganizer())) {
            throw new NotEnoughPermissionsException();
        }

        User userEntity = UserDao.getUser(bean.getUser());
        Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());

        EventGoal goal = new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), bean.getId(), userEntity, eventEntity, bean.getState());

        goal.rejectJoinRequest();

        if (goal.getState().equals(EventRequestState.REJECTED))
            EventGoalDao.rejectEventGoal(goal.getId(), goal.getUser().getUsername());
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


}
