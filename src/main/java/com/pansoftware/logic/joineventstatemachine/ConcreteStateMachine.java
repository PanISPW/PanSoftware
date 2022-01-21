package com.pansoftware.logic.joineventstatemachine;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.interfaces.StateMachine;

// @author Danilo D'Amico

public class ConcreteStateMachine implements StateMachine {

    private Event event;
    private EventGoal goal;
    private JoinEventState currentState; // abstract state

    public ConcreteStateMachine(Event event, EventGoal goal, EventRequestState state) throws DatabaseException {
        this.event = event;
        this.goal = goal;

        currentState = JoinEventState.getMachineState(this, state, event);
    }

    public EventGoal getGoal() {
        return goal;
    }

    public void setGoal(EventGoal goal) {
        this.goal = goal;
    }

    @Override
    public void answerRequest(EventRequestState state) throws DatabaseException, NoTransitionException {

        if(state == EventRequestState.REJECTED){
            currentState.reject();
        } else {
            currentState.accept();
        }
    }

    @Override
    public void changeState(JoinEventState state) {
        currentState = state;
    }

    @Override
    public EventRequestState getState() {
        return this.currentState.getState();
    }

    @Override
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


}
