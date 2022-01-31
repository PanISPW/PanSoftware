package com.pansoftware.logic.joineventstatemachine;

import com.pansoftware.logic.entity.Event;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.exception.NoTransitionException;
import com.pansoftware.logic.interfaces.StateMachine;

import java.sql.SQLException;

// @author Danilo D'Amico

public class ConcreteStateMachine implements StateMachine {

    private Event event;
    private EventGoal goal;
    private JoinEventState currentState; // abstract state

    public ConcreteStateMachine(final Event event, final EventGoal goal, final EventRequestState state) throws SQLException {
        this.event = event;
        this.goal = goal;

        currentState = JoinEventState.getMachineState(this, state, event);
    }

    public EventGoal getGoal() {
        return this.goal;
    }

    public void setGoal(final EventGoal goal) {
        this.goal = goal;
    }

    @Override
    public void answerRequest(final EventRequestState state) throws NoTransitionException {

        if(state == EventRequestState.REJECTED){
            this.currentState.reject();
        } else {
            this.currentState.accept();
        }
    }

    @Override
    public void changeState(final JoinEventState state) {
        this.currentState = state;
    }

    @Override
    public EventRequestState getState() {
        return currentState.getState();
    }

    @Override
    public Event getEvent() {
        return this.event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }


}
