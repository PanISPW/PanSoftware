package logic.joineventstatemachine;

import logic.entity.Event;
import logic.enumeration.EventRequestState;
import logic.enumeration.EventType;
import logic.exception.DatabaseException;
import logic.exception.NoTransitionException;

// @author Danilo D'Amico

public class StartingState extends JoinEventState {

    private static ConcreteStateMachine stateMachine;
    private static boolean isPrivate = false;
    private static Event event;

    public StartingState(ConcreteStateMachine stateMachine, Event event) throws Exception {
    }

    public static JoinEventState nextState() throws Exception {
        if (isPrivate) {
            return new PendingState(stateMachine, event);
        } else {
            return new AcceptedState(event);
        }
    }

    @Override
    protected void accept() throws DatabaseException, NoTransitionException {
        throw new NoTransitionException();
    }

    @Override
    protected void reject() throws DatabaseException, NoTransitionException {
        throw new NoTransitionException();
    }

    // FATTO
    @Override
    protected EventRequestState getState() {
        return EventRequestState.STARTING;
    }
}
