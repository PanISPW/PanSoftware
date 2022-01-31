package com.pansoftware.logic.joineventstatemachine;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.NoTransitionException;

import java.sql.SQLException;

// abstract state of the join event state machine

// @author Danilo D'Amico

public abstract class JoinEventState {

    public static JoinEventState getMachineState(final ConcreteStateMachine stateMachine, final EventRequestState state, final Event event) throws SQLException {

        switch (state) {
            case PENDING:
                return new PendingState(stateMachine, event);
            case ACCEPTED:
                return new AcceptedState(event);
            case REJECTED:
                return new RejectedState(event);
            default:
                StartingState newState = new StartingState(stateMachine, event);
                return newState.nextState();
        }

    }

    protected abstract void accept() throws NoTransitionException;

    protected abstract void reject() throws NoTransitionException;

    protected abstract EventRequestState getState();
}
