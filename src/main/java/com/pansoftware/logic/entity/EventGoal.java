package com.pansoftware.logic.entity;

import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.interfaces.StateMachine;
import com.pansoftware.logic.joineventstatemachine.ConcreteStateMachine;

import java.sql.SQLException;
import java.time.LocalDate;

// classe Client del pattern Machine State

// @author Danilo D'Amico

public class EventGoal extends Goal {

    private Event event;

    private final StateMachine stateMachine;

    public EventGoal(final String name, final String description, final int numberOfSteps, final int stepsCompleted, final LocalDate deadline, final int id, final User user, final Event event, final EventRequestState state) throws SQLException {
        super(name, description, numberOfSteps, stepsCompleted, deadline, user, id);
        this.event = event;

        stateMachine = new ConcreteStateMachine(event, this, state);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }

    public EventRequestState getState() {
        return stateMachine.getState();
    }

    public void acceptJoinRequest() throws NoTransitionException {
        stateMachine.answerRequest(EventRequestState.ACCEPTED);
    }

    public void rejectJoinRequest() throws NoTransitionException {
        stateMachine.answerRequest(EventRequestState.REJECTED);
    }
}
