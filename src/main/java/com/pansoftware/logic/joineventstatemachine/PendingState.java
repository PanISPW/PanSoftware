package com.pansoftware.logic.joineventstatemachine;

import com.pansoftware.logic.dao.EventGoalDao;
import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;

// @author Danilo D'Amico

public class PendingState extends JoinEventState {

    ConcreteStateMachine stateMachine;
    Event event;

    public PendingState(ConcreteStateMachine stateMachine, Event event) throws DatabaseException {
        this.stateMachine = stateMachine;
        this.event = event;
        EventGoalDao.pendingEventGoal(event.getId(), event.getUser().getUsername());
    }

    @Override
    protected void accept() {
        this.stateMachine.changeState(new AcceptedState(event));
    }

    @Override
    protected void reject() {
        this.stateMachine.changeState(new RejectedState(event));
    }

    @Override
    protected EventRequestState getState() {
        return EventRequestState.PENDING;
    }

}
