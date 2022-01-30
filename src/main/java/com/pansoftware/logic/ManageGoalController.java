package com.pansoftware.logic;

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
import java.util.ArrayList;
import java.util.List;

// @author Danilo D'Amico

public class ManageGoalController {

    private ManageGoalController(){}

    private static void addReminder(String email, LocalDate day) {
        SendMail.sendOnDay(email, day);
    }

    public static void updateSteps(UpdateStepsBean bean) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        String username = bean.getUpdateUser();
        int stepsCompleted = bean.getStepsCompleted();
        int numberOfSteps;

        switch (bean.getType()) {
            case ADVICEGOAL -> {
                numberOfSteps = AdviceGoalDao.getAdviceGoal(username, bean.getUpdateId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }
                AdviceGoalDao.updateStepsAdviceGoal(stepsCompleted, bean.getUpdateId(), username);
            }
            case EVENTGOAL -> {
                numberOfSteps = EventGoalDao.getEventGoal(username, bean.getUpdateId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }
                EventGoalDao.updateStepsEventGoal(stepsCompleted, bean.getUpdateId(), username);
            }
            default -> {
                numberOfSteps = GoalDao.getGoal(bean.getUpdateUser(), bean.getUpdateId()).getNumberOfSteps();
                if (numberOfSteps < stepsCompleted) {
                    stepsCompleted = numberOfSteps;
                }
                GoalDao.updateStepsGoal(stepsCompleted, bean.getUpdateId(), username);
            }
        }

    }

    public static List<AdviceGoal> getAdviceGoalList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getAdviceGoalList(Session.getSession().getUser());
    }

    public static List<AdviceGoalBean> getAdviceGoalBeanList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<AdviceGoal> goalList = AdviceGoalDao.getAdviceGoalList(Session.getSession().getUser());
        List<AdviceGoalBean> beanList = new ArrayList<>();

        for (AdviceGoal e : goalList){
            AdviceGoalBean bean = new AdviceGoalBean();
            populateGoalBean(e, bean);

            String adviceActivist;

            bean.setType(e.getType());
            bean.setAdvice(e.getAdvice());

            if(e.getAdviceActivist() != null)
                adviceActivist = e.getAdviceActivist().getUsername();
            else
                adviceActivist = "NOT SPECIFIED";

            bean.setAdviceActivist(adviceActivist);

            beanList.add(bean);
        }

        return beanList;
    }

    public static List<AdviceGoalBean> populateUnansweredAdviceBean(List<AdviceGoal> goalList) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<AdviceGoalBean> beanList = new ArrayList<>();

        for (AdviceGoal e : goalList){

            AdviceGoalBean bean = new AdviceGoalBean();

            bean.setName(e.getName());
            bean.setId(e.getId());
            bean.setName(e.getName());
            bean.setDescription(e.getDescription());
            bean.setNewDeadline(e.getDeadline());
            bean.setNumberOfSteps(e.getNumberOfSteps());
            bean.setStepsCompleted(e.getStepsCompleted());
            bean.setUser(e.getUser().getUsername());

            bean.setType(e.getType());

            beanList.add(bean);

        }

        return beanList;
    }

    public static List<AdviceGoal> getUnansweredMakeupAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getUnansweredMakeupAdvice();
    }

    public static List<AdviceGoalBean> getUnansweredMakeupAdviceBean() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<AdviceGoal> goalList = AdviceGoalDao.getUnansweredMakeupAdvice();

        return populateUnansweredAdviceBean(goalList);
    }

    public static List<AdviceGoal> getUnansweredFoodAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getUnansweredFoodAdvice();
    }

    public static List<AdviceGoalBean> getUnansweredFoodAdviceBean() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<AdviceGoal> goalList =  AdviceGoalDao.getUnansweredFoodAdvice();

        return populateUnansweredAdviceBean(goalList);
    }

    public static List<AdviceGoal> getUnansweredLifestyleAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getUnansweredLifestyleAdvice();
    }

    public static List<AdviceGoalBean> getUnansweredLifestyleAdviceBean() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<AdviceGoal> goalList =   AdviceGoalDao.getUnansweredLifestyleAdvice();

        return populateUnansweredAdviceBean(goalList);
    }

    public static List<AdviceGoal> getUnansweredOtherAdvice() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return AdviceGoalDao.getUnansweredOtherAdvice();
    }

    public static List<AdviceGoalBean> getUnansweredOtherAdviceBean() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<AdviceGoal> goalList =  AdviceGoalDao.getUnansweredOtherAdvice();

        return populateUnansweredAdviceBean(goalList);
    }

    public static List<Goal> getGoalList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return GoalDao.getGoalList(Session.getSession().getUser());
    }

    private static void populateGoalBean(Goal e, GoalBean bean) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        bean.setName(e.getName());
        bean.setDescription(e.getDescription());
        bean.setNumberOfSteps(e.getNumberOfSteps());
        bean.setStepsCompleted(e.getStepsCompleted());
        bean.setDeadline(e.getDeadline());
        bean.setId(e.getId());
        bean.setUser(e.getUser().getUsername());
    }

    public static List<GoalBean> getGoalBeanList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<Goal> goalList = GoalDao.getGoalList(Session.getSession().getUser());
        List<GoalBean> beanList = new ArrayList<>();

        for (Goal e : goalList){
            GoalBean bean = new GoalBean();
            populateGoalBean(e, bean);

            beanList.add(bean);
        }

        return beanList;
    }


    public static List<EventGoal> getEventGoalList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return EventGoalDao.getEventGoalList(Session.getSession().getUser());
    }

    public static List<EventGoalBean> getEventGoalBeanList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, SQLException, InvalidDataException {
        List<EventGoal> goalList = EventGoalDao.getEventGoalList(Session.getSession().getUser());

        List<EventGoalBean> beanList = new ArrayList<>();

        for (EventGoal e : goalList){
            EventGoalBean bean = new EventGoalBean();
            populateGoalBean(e, bean);

            bean.setEventId(e.getEvent().getId());
            bean.setEventOrganizer(e.getEvent().getUser().getUsername());
            bean.setState(e.getState());
            bean.setEventName(e.getName());

            beanList.add(bean);
        }

        return beanList;
    }

    public static List<Event> getEventList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return EventDao.getEventList();
    }

    public static List<EventBean> getEventBeanList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<Event> eventList = EventDao.getEventList();
        List<EventBean> beanList = new ArrayList<>();


        for (Event e : eventList){

            EventBean bean = new EventBean();

            bean.setName(e.getName());
            bean.setStartingDate(e.getStartingDate());
            bean.setEndingDate(e.getEndingDate());
            bean.setOrganizer(e.getUser().getUsername());
            bean.setId(e.getId());
            bean.setType(e.getType());

            beanList.add(bean);

        }

        return beanList;
    }

    public static List<EventGoal> getPendingEventGoalList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return JoinEventController.getPendingEventGoalList();
    }

    public static List<EventGoalBean> getPendingEventGoalBeanList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        List<EventGoal> goalList = JoinEventController.getPendingEventGoalList();

        List<EventGoalBean> beanList = new ArrayList<>();


        for (EventGoal e : goalList){

            EventGoalBean bean = new EventGoalBean();

            bean.setName(e.getName());
            bean.setDescription(e.getDescription());
            bean.setNewDeadline(e.getDeadline());
            bean.setNumberOfSteps(e.getNumberOfSteps());
            bean.setStepsCompleted(e.getStepsCompleted());
            bean.setUser(e.getUser().getUsername());
            bean.setId(e.getId());

            bean.setEventId(e.getEvent().getId());
            bean.setState(EventRequestState.PENDING);
            bean.setEventOrganizer(e.getOrganizer().getUsername());

            beanList.add(bean);
        }

        return beanList;
    }

    public static void answerAdviceGoal(AnswerAdviceGoalBean bean) throws UserNotFoundException, LoginException, DatabaseException, NotEnoughPermissionsException, SQLException {
        UserRole role = UserDao.getUser(Session.getSession().getUser()).getRole();

        if (role.equals(UserRole.USER))
            throw new NotEnoughPermissionsException("Your type of user cannot perform this action");

        AdviceGoalDao.answerAdviceGoal(bean.getAnswerAdviceId(), bean.getGoalUser(), Session.getSession().getUser(), bean.getAnswer());
    }

    public static void joinEvent(EventGoalBean bean, int id) throws UserNotFoundException, LoginException, DatabaseException, EmptyResultSetException, SQLException {
        User userEntity = UserDao.getUser(Session.getSession().getUser());
        Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());

        new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), id, userEntity, eventEntity, EventRequestState.STARTING);
    }

    public static void acceptEventGoal(EventGoalBean bean) throws UserNotFoundException, NoTransitionException, LoginException, DatabaseException, EmptyResultSetException {


        try{
            JoinEventController.acceptJoinRequest(bean);
            EventGoalDao.acceptEventGoal(bean.getId(), bean.getUser());
        } catch(NoTransitionException e){
            throw new NoTransitionException("Event Goal not accepted");
        }
    }

    public static void rejectEventGoal(EventGoalBean bean) throws UserNotFoundException, NoTransitionException, LoginException, DatabaseException, EmptyResultSetException {

        try{
            JoinEventController.rejectJoinRequest(bean);
            EventGoalDao.rejectEventGoal(bean.getId(), bean.getUser());
        } catch(NoTransitionException e){
            throw new NoTransitionException("Event Goal not rejected");
        }
    }

    public static User getCurrentUser() throws UserNotFoundException, LoginException, DatabaseException, SQLException {
        String user = Session.getSession().getUser();

        return UserDao.getUser(user);
    }

    public static UserBean getCurrentUserBean() throws UserNotFoundException, LoginException, DatabaseException, SQLException {
        User user = ManageGoalController.getCurrentUser();

        UserBean bean = new UserBean();
        bean.setName(user.getName());
        bean.setSurname(user.getSurname());
        bean.setUsername(user.getUsername());

        return bean;
    }

    public static void createGoal(GoalBean bean) throws UserNotFoundException, LoginException, DatabaseException, SQLException, EmptyResultSetException {
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
