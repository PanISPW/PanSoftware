package logic.joinEventStateMachine;

import logic.entity.Event;
import logic.enumeration.EventRequestState;
import logic.exception.DatabaseException;
import logic.exception.NoTransitionException;

// @author Danilo D'Amico

public class AcceptedState extends JoinEventState {

    public AcceptedState(Event event) throws Exception {
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
        return EventRequestState.ACCEPTED;
    }

}
