package logic.interfaces;

import logic.entity.Event;
import logic.enumeration.EventRequestState;
import logic.exception.DatabaseException;
import logic.exception.NoTransitionException;
import logic.joinEventStateMachine.JoinEventState;

// @author Danilo D'Amico

public interface StateMachine {

	public void answerRequest(EventRequestState state) throws DatabaseException, NoTransitionException, Exception;
	public void changeState(JoinEventState state);
	public EventRequestState getState();
	public Event getEvent();

}