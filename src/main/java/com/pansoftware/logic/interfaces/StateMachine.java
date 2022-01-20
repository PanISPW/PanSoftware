package com.pansoftware.logic.interfaces;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.joineventstatemachine.JoinEventState;

// @author Danilo D'Amico

public interface StateMachine {

    public void answerRequest(EventRequestState state) throws DatabaseException, NoTransitionException;

    public void changeState(JoinEventState state);

    public EventRequestState getState();

    public Event getEvent();

}