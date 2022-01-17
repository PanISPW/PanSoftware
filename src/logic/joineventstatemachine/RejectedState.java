package logic.joineventstatemachine;

import logic.entity.Event;
import logic.enumeration.EventRequestState;
import logic.exception.DatabaseException;
import logic.exception.NoTransitionException;

// @author Danilo D'Amico

public class RejectedState extends JoinEventState {

    public RejectedState(Event event) {
    }

    @Override
    protected void accept() throws DatabaseException, NoTransitionException {
        throw new NoTransitionException();
    }

    @Override
    protected void reject() throws DatabaseException, NoTransitionException {
        throw new NoTransitionException();
    }

    @Override
    protected EventRequestState getState() {
        return EventRequestState.REJECTED;
    }

}
