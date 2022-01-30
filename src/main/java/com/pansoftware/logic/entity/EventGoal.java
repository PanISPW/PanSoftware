package com.pansoftware.logic.entity;

import com.pansoftware.logic.dao.EventGoalDao;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.interfaces.StateMachine;
import com.pansoftware.logic.joineventstatemachine.ConcreteStateMachine;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;

// classe Client del pattern Machine State

// @author Danilo D'Amico

public class EventGoal extends Goal {

    private Event event;
    private User organizer;

    private final StateMachine stateMachine;

    public EventGoal(final String name, final String description, final int numberOfSteps, final int stepsCompleted, final LocalDate deadline, final int id, final User user, final Event event, final EventRequestState state) throws DatabaseException {
        super(name, description, numberOfSteps, stepsCompleted, deadline, user, id);
        this.event = event;

        try {
            organizer = event.getUser();
        } catch (final NullPointerException e) {
            organizer = null;
        }

        stateMachine = new ConcreteStateMachine(event, this, state);
    }

    public static EventGoal getEventGoal(final String user, final int id) throws UserNotFoundException, EmptyResultSetException, LoginException, DatabaseException {
        return EventGoalDao.getEventGoal(user, id);
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }

    public User getOrganizer() {
        return this.organizer;
    }

    public void setOrganizer(final User organizer) {
        this.organizer = organizer;
    }

    public EventRequestState getState() {
        return this.stateMachine.getState();
    }

    public void acceptJoinRequest() throws NoTransitionException, DatabaseException {
        this.stateMachine.answerRequest(EventRequestState.ACCEPTED);
    }

    public void rejectJoinRequest() throws NoTransitionException, DatabaseException {
        this.stateMachine.answerRequest(EventRequestState.REJECTED);
    }
}
