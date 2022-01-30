package com.pansoftware.logic.joineventstatemachine;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.NoTransitionException;

import static com.pansoftware.logic.util.Constants.NO_TRANSITION_OCCURS;

// @author Danilo D'Amico

public class StartingState extends JoinEventState {

    private static ConcreteStateMachine stateMachine;
    private static final boolean ISPRIVATE = false;
    private static Event event;

    public StartingState(final ConcreteStateMachine stateMachine, final Event event) {
    }

    public static JoinEventState nextState() throws DatabaseException {
        if (StartingState.ISPRIVATE) {
            return new PendingState(StartingState.stateMachine, StartingState.event);
        } else {
            return new AcceptedState(StartingState.event);
        }
    }

    @Override
    protected void accept() throws NoTransitionException {
        throw new NoTransitionException(NO_TRANSITION_OCCURS);
    }

    @Override
    protected void reject() throws DatabaseException, NoTransitionException {
        throw new NoTransitionException(NO_TRANSITION_OCCURS);
    }

    // FATTO
    @Override
    protected EventRequestState getState() {
        return EventRequestState.STARTING;
    }
}
