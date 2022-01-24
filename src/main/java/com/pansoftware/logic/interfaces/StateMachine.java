package com.pansoftware.logic.interfaces;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.joineventstatemachine.JoinEventState;

// @author Danilo D'Amico

public interface StateMachine {

    void answerRequest(EventRequestState state) throws DatabaseException, NoTransitionException;

    void changeState(JoinEventState state);

    EventRequestState getState();

    Event getEvent();

}