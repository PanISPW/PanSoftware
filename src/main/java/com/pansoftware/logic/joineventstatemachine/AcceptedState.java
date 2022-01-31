package com.pansoftware.logic.joineventstatemachine;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.NoTransitionException;

import static com.pansoftware.logic.util.Constants.NO_TRANSITION_OCCURS;

// @author Danilo D'Amico

public class AcceptedState extends JoinEventState {

    public AcceptedState(final Event event) {
    }

    @Override
    protected void accept() throws NoTransitionException {
        throw new NoTransitionException(NO_TRANSITION_OCCURS);
    }

    @Override
    protected void reject() throws NoTransitionException {
        throw new NoTransitionException(NO_TRANSITION_OCCURS);
    }

    @Override
    protected EventRequestState getState() {
        return EventRequestState.ACCEPTED;
    }

}
