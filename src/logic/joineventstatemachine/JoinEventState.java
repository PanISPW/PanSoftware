package logic.joineventstatemachine;

import logic.entity.Event;
import logic.enumeration.EventRequestState;
import logic.exception.DatabaseException;
import logic.exception.NoTransitionException;

// abstract state of the join event state machine

// @author Danilo D'Amico

public abstract class JoinEventState {

    public static JoinEventState getMachineState(ConcreteStateMachine stateMachine, EventRequestState state, Event event) throws Exception {

        switch (state) {
            case PENDING:
                return new PendingState(stateMachine, event);
            case ACCEPTED:
                return new AcceptedState(event);
            case REJECTED:
                return new RejectedState(event);
            default:
                new StartingState(stateMachine, event);
                return StartingState.nextState();
        }

    }

    protected abstract void accept() throws DatabaseException, NoTransitionException, Exception;

    protected abstract void reject() throws DatabaseException, NoTransitionException, Exception;

    protected abstract EventRequestState getState();
}
