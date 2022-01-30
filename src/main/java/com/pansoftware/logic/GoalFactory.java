package com.pansoftware.logic;

import com.pansoftware.logic.bean.AdviceGoalBean;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.bean.GoalBean;
import com.pansoftware.logic.dao.*;
import com.pansoftware.logic.entity.AdviceGoal;
import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.entity.Goal;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.util.Constants;
import com.pansoftware.logic.util.Session;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

// singleton
// creates new goal and adds it to the persistance layer

// @author Danilo D'Amico

public class GoalFactory {

    private static GoalFactory instance = null;

    private GoalFactory() {
    }

    public static GoalFactory getGoalFactory() {
        if (instance == null)
            instance = new GoalFactory();

        return instance;
    }

    public void createGoal(GoalBean bean) throws SQLException, DatabaseException, UserNotFoundException, EmptyResultSetException, LoginException {

        if(bean instanceof AdviceGoalBean){
            makeAdviceGoal((AdviceGoalBean) bean);
        } else if(bean instanceof EventGoalBean){
            makeEventGoal((EventGoalBean) bean);
        } else{
            makeGoal(bean);
        }
    }

    private void makeGoal(GoalBean bean) throws DatabaseException, SQLException, UserNotFoundException, LoginException {

        int goalId;
        try {
            goalId = GoalDao.getLastUserGoalId(Session.getSession().getUser());

        } catch (Exception e) {
            throw new DatabaseException(Constants.COULD_NOT_RETRIEVE_NEW_ID);
        }

        goalId = goalId + 1;
        Goal goal = new Goal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), UserDao.getUser(Session.getSession().getUser()), goalId);
        GoalDao.addGoal(goal);
    }

    private void makeAdviceGoal(AdviceGoalBean bean) throws DatabaseException, UserNotFoundException, LoginException, SQLException {

        int adviceGoalId;
        try {
            adviceGoalId = AdviceGoalDao.getLastUserAdviceGoalId(Session.getSession().getUser());
        } catch (Exception e) {
            throw new DatabaseException(Constants.COULD_NOT_RETRIEVE_NEW_ID);
        }

        adviceGoalId = adviceGoalId + 1;

        AdviceGoal goal = new AdviceGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), adviceGoalId, UserDao.getUser(Session.getSession().getUser()), bean.getType(), "", null);

        AdviceGoalDao.addAdviceGoal(goal);

    }

    private void makeEventGoal(EventGoalBean bean) throws DatabaseException, UserNotFoundException, EmptyResultSetException, LoginException, SQLException {

        int eventGoalId;
        try {
            eventGoalId = EventGoalDao.getLastUserEventGoalId(Session.getSession().getUser());
        } catch (Exception e) {
            throw new DatabaseException(Constants.COULD_NOT_RETRIEVE_NEW_ID);
        }

        eventGoalId = eventGoalId + 1;
        Event event = EventDao.getEvent(bean.getEventId(), bean.getEventOrganizer());

        EventGoal goal = new EventGoal(bean.getName(), bean.getDescription(), bean.getNumberOfSteps(), bean.getStepsCompleted(), bean.getDeadline(), eventGoalId, UserDao.getUser(Session.getSession().getUser()), event, EventRequestState.STARTING);

        EventGoalDao.addEventGoal(goal);

    }

}
