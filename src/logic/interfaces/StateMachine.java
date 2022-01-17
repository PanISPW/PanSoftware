package logic.interfaces;

import logic.entity.Event;
import logic.enumeration.EventRequestState;
import logic.exception.DatabaseException;
import logic.exception.NoTransitionException;
import logic.joineventstatemachine.JoinEventState;

// @author Danilo D'Amico

public interface StateMachine {

    public void answerRequest(EventRequestState state) throws DatabaseException, NoTransitionException;

    public void changeState(JoinEventState state);

    public EventRequestState getState();

    public Event getEvent();

}