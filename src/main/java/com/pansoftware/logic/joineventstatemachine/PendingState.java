package com.pansoftware.logic.joineventstatemachine;

import com.pansoftware.logic.dao.EventGoalDao;
import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.enumeration.EventRequestState;

import java.sql.SQLException;

// @author Danilo D'Amico

public class PendingState extends JoinEventState {

    ConcreteStateMachine stateMachine;
    Event event;

    public PendingState(final ConcreteStateMachine stateMachine, final Event event) throws SQLException {
        this.stateMachine = stateMachine;
        this.event = event;
        EventGoalDao.pendingEventGoal(event.getId(), event.getUser().getUsername());
    }

    @Override
    protected void accept() {
        stateMachine.changeState(new AcceptedState(this.event));
    }

    @Override
    protected void reject() {
        stateMachine.changeState(new RejectedState(this.event));
    }

    @Override
    protected EventRequestState getState() {
        return EventRequestState.PENDING;
    }

}
