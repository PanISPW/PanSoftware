package logic;

import logic.bean.GoalBean;
import logic.dao.*;
import logic.entity.Event;
import logic.entity.EventGoal;
import logic.enumeration.EventRequestState;
import logic.enumeration.GoalType;
import logic.enumeration.ProductType;
import logic.exception.DatabaseException;
import logic.exception.EmptyResultSetException;
import logic.util.Constants;
import logic.util.Session;

import java.time.LocalDate;

// singleton
// creates new goal and adds it to the persistance layer

// @author Danilo D'Amico

public class GoalFactory {

    private static GoalFactory instance = null;

    private String name;
    private String description;
    private int numberOfSteps;
    private int stepsCompleted;
    private LocalDate deadline;

    private int goalId;
    private int eventGoalId;
    private int adviceGoalId;

    private GoalFactory() {
    }

    public static GoalFactory getGoalFactory() {
        if (instance == null)
            instance = new GoalFactory();

        return instance;
    }

    public int createGoal(GoalBean bean) throws Exception {

        this.name = bean.getName();
        this.description = bean.getDescription();
        this.numberOfSteps = bean.getNumberOfSteps();
        this.stepsCompleted = bean.getStepsCompleted();
        this.deadline = bean.getDeadline();


        if (bean.getGoalType().equals(GoalType.GOAL)) {
            return makeGoal();
        } else if (bean.getGoalType().equals(GoalType.ADVICEGOAL)) {
            return makeAdviceGoal(bean.getType());
        } else if (bean.getGoalType().equals(GoalType.EVENTGOAL)) {
            return makeEventGoal(bean.getEventOrganizer(), bean.getEventId());
        } else return -1;
    }

    private int makeGoal() throws Exception {

        try {
            goalId = GoalDao.getLastUserGoalId(Session.getSession().getUser());

        } catch (Exception e) {
            if (e instanceof EmptyResultSetException)
                goalId = 0;
            else
                throw new DatabaseException(Constants.COULD_NOT_RETRIEVE_NEW_ID);
        }

        goalId = goalId + 1;

        GoalDao.addGoal(Session.getSession().getUser(), name, description, numberOfSteps, stepsCompleted, deadline, goalId);

        return goalId;
    }

    private int makeAdviceGoal(ProductType type) throws Exception {

        try {
            adviceGoalId = AdviceGoalDao.getLastUserAdviceGoalId(Session.getSession().getUser());
        } catch (Exception e) {
            if (e instanceof EmptyResultSetException)
                goalId = 0;
            else
                throw new DatabaseException(Constants.COULD_NOT_RETRIEVE_NEW_ID);
        }

        adviceGoalId = adviceGoalId + 1;

        AdviceGoalDao.addAdviceGoal(Session.getSession().getUser(), name, description, numberOfSteps, stepsCompleted, deadline, adviceGoalId, type);

        return adviceGoalId;
    }

    private int makeEventGoal(String eventOrganizer, int eventId) throws Exception {

        try {
            eventGoalId = EventGoalDao.getLastUserEventGoalId(Session.getSession().getUser());
        } catch (Exception e) {
            if (e instanceof EmptyResultSetException)
                goalId = 0;
            else
                throw new DatabaseException(Constants.COULD_NOT_RETRIEVE_NEW_ID);
        }

        eventGoalId = eventGoalId + 1;
        Event event = EventDao.getEvent(eventId, eventOrganizer);

        EventGoal goal = new EventGoal(name, description, numberOfSteps, stepsCompleted, deadline, goalId, UserDao.getUser(Session.getSession().getUser()), event, EventRequestState.STARTING);

        EventGoalDao.addEventGoal(Session.getSession().getUser(), name, description, numberOfSteps, stepsCompleted, deadline, eventGoalId, eventOrganizer, eventId, goal.getState());

        return eventGoalId;
    }

}
