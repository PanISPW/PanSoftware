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

import static com.pansoftware.logic.util.Constants.NO_TRANSITION_OCCURS;

// @author Danilo D'Amico

public class ManageGoalController {

    private ManageGoalController(){}

    private static void addReminder(final String email, final LocalDate day) {
        SendMail.sendOnDay(email, day);
    }

    public static void updateSteps(final UpdateStepsBean bean) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        final String username = bean.getUpdateUser();
        int stepsCompleted = bean.getStepsCompleted();
        final int numberOfSteps;

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

    public static List<AdviceGoalBean> getAdviceGoalBeanList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<AdviceGoal> goalList = AdviceGoalDao.getAdviceGoalList(Session.getSession().getUser());
        final List<AdviceGoalBean> beanList = new ArrayList<>();

        for (final AdviceGoal e : goalList){
            final AdviceGoalBean bean = new AdviceGoalBean();
            populateGoalBean(e, bean);

            String adviceActivist;

            bean.setType(e.getType());
            bean.setAdvice(e.getAdvice());

            try {
                adviceActivist = e.getAdviceActivist().getUsername();
            } catch(NullPointerException exception) {
                adviceActivist = "";
            }

            bean.setAdviceActivist(adviceActivist);

            beanList.add(bean);
        }

        return beanList;
    }

    public static List<AdviceGoalBean> populateUnansweredAdviceBean(final List<AdviceGoal> goalList) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<AdviceGoalBean> beanList = new ArrayList<>();

        for (final AdviceGoal e : goalList){

            final AdviceGoalBean bean = new AdviceGoalBean();

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

    public static List<AdviceGoalBean> getUnansweredMakeupAdviceBean() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<AdviceGoal> goalList = AdviceGoalDao.getUnansweredMakeupAdvice();

        return populateUnansweredAdviceBean(goalList);
    }

    public static List<AdviceGoalBean> getUnansweredFoodAdviceBean() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<AdviceGoal> goalList =  AdviceGoalDao.getUnansweredFoodAdvice();

        return populateUnansweredAdviceBean(goalList);
    }

    public static List<AdviceGoalBean> getUnansweredLifestyleAdviceBean() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<AdviceGoal> goalList =   AdviceGoalDao.getUnansweredLifestyleAdvice();

        return populateUnansweredAdviceBean(goalList);
    }

    public static List<AdviceGoalBean> getUnansweredOtherAdviceBean() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<AdviceGoal> goalList =  AdviceGoalDao.getUnansweredOtherAdvice();

        return populateUnansweredAdviceBean(goalList);
    }

    private static void populateGoalBean(final Goal e, final GoalBean bean) throws InvalidDataException {
        bean.setName(e.getName());
        bean.setDescription(e.getDescription());
        bean.setNumberOfSteps(e.getNumberOfSteps());
        bean.setStepsCompleted(e.getStepsCompleted());
        bean.setDeadline(e.getDeadline());
        bean.setId(e.getId());
        bean.setUser(e.getUser().getUsername());
    }

    public static List<GoalBean> getGoalBeanList() throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<Goal> goalList = GoalDao.getGoalList(Session.getSession().getUser());
        final List<GoalBean> beanList = new ArrayList<>();

        for (final Goal e : goalList){
            final GoalBean bean = new GoalBean();
            populateGoalBean(e, bean);

            beanList.add(bean);
        }

        return beanList;
    }


    public static List<EventGoalBean> getEventGoalBeanList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<EventGoal> goalList = EventGoalDao.getEventGoalList(Session.getSession().getUser());

        final List<EventGoalBean> beanList = new ArrayList<>();

        for (final EventGoal e : goalList){
            final EventGoalBean bean = new EventGoalBean();
            populateGoalBean(e, bean);

            bean.setEventId(e.getEvent().getId());
            bean.setEventOrganizer(e.getEvent().getUser().getUsername());
            bean.setState(e.getState());
            bean.setEventName(e.getEvent().getName());

            beanList.add(bean);
        }

        return beanList;
    }

    public static List<EventBean> getEventBeanList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<Event> eventList = EventDao.getEventList();
        final List<EventBean> beanList = new ArrayList<>();


        for (final Event e : eventList){

            final EventBean bean = new EventBean();

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

    public static List<EventGoalBean> getPendingEventGoalBeanList() throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException, InvalidDataException {
        final List<EventGoal> goalList = JoinEventController.getPendingEventGoalList();

        final List<EventGoalBean> beanList = new ArrayList<>();


        for (final EventGoal e : goalList){

            final EventGoalBean bean = new EventGoalBean();

            bean.setName(e.getName());
            bean.setDescription(e.getDescription());
            bean.setNewDeadline(e.getDeadline());
            bean.setNumberOfSteps(e.getNumberOfSteps());
            bean.setStepsCompleted(e.getStepsCompleted());
            bean.setUser(e.getUser().getUsername());
            bean.setId(e.getId());

            bean.setEventId(e.getEvent().getId());
            bean.setState(EventRequestState.PENDING);
            bean.setEventOrganizer(e.getEvent().getUser().getUsername());

            beanList.add(bean);
        }

        return beanList;
    }

    public static void answerAdviceGoal(final AnswerAdviceGoalBean bean) throws UserNotFoundException, LoginException, DatabaseException, NotEnoughPermissionsException, SQLException, EmptyResultSetException {
        final UserRole role = UserDao.getUser(Session.getSession().getUser()).getRole();

        if (role.equals(UserRole.USER))
            throw new NotEnoughPermissionsException("Your type of user cannot perform this action");

        AdviceGoalDao.answerAdviceGoal(bean.getAnswerAdviceId(), bean.getGoalUser(), Session.getSession().getUser(), bean.getAnswer());
    }

    public static void joinEvent(final EventGoalBean bean, final int id) throws DatabaseException, EmptyResultSetException {
        final User userEntity = UserDao.getUser(Session.getSession().getUser());
        final Event eventEntity = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());

        new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), id, userEntity, eventEntity, EventRequestState.STARTING);
    }

    public static void acceptEventGoal(final EventGoalBean bean) throws UserNotFoundException, NoTransitionException, LoginException, DatabaseException, EmptyResultSetException {


        try{
            JoinEventController.acceptJoinRequest(bean);
            EventGoalDao.acceptEventGoal(bean.getId(), bean.getUser());
        } catch(final NoTransitionException e){
            throw new NoTransitionException(NO_TRANSITION_OCCURS + ": Goal not accepted");
        }
    }

    public static void rejectEventGoal(final EventGoalBean bean) throws UserNotFoundException, NoTransitionException, LoginException, DatabaseException, EmptyResultSetException {

        try{
            JoinEventController.rejectJoinRequest(bean);
            EventGoalDao.rejectEventGoal(bean.getId(), bean.getUser());
        } catch(final NoTransitionException e){
            throw new NoTransitionException(NO_TRANSITION_OCCURS + ": Goal not rejected");
        }
    }

    public static User getCurrentUser() throws DatabaseException, EmptyResultSetException {
        final String user = Session.getSession().getUser();

        return UserDao.getUser(user);
    }

    public static UserBean getCurrentUserBean() throws DatabaseException, EmptyResultSetException {
        final User user = getCurrentUser();

        final UserBean bean = new UserBean();
        bean.setName(user.getName());
        bean.setSurname(user.getSurname());
        bean.setUsername(user.getUsername());

        return bean;
    }

    public static void createGoal(final GoalBean bean) throws UserNotFoundException, LoginException, DatabaseException, SQLException, EmptyResultSetException {
        final User user = UserDao.getUser(Session.getSession().getUser());
        GoalFactory.getGoalFactory().createGoal(bean);

        if (bean.isReminder()) {
            addReminder(user.getEmail(), bean.getDeadline());
        }
    }

    public static Goal getGoal(final String user, final int id) throws EmptyResultSetException, DatabaseException {
        return GoalDao.getGoal(user, id);
    }

}
