package com.pansoftware.logic.joineventstatemachine;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.enumeration.EventType;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.NoTransitionException;

import static com.pansoftware.logic.util.Constants.NO_TRANSITION_OCCURS;

// @author Danilo D'Amico

public class StartingState extends JoinEventState {

    private static ConcreteStateMachine stateMachine;
    private static boolean ISPRIVATE = false;
    private static Event event;

    public StartingState(final ConcreteStateMachine stateMachine, final Event event) {
        this.stateMachine = stateMachine;
        this.event = event;
        if(event.getType().equals(EventType.PRIVATE)){
            ISPRIVATE = true;
        }

    }

    public static JoinEventState nextState() throws DatabaseException {
        if (ISPRIVATE) {
            return new PendingState(stateMachine, event);
        } else {
            return new AcceptedState(event);
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

    @Override
    protected EventRequestState getState() {
        return EventRequestState.STARTING;
    }
}
