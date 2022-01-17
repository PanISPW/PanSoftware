package logic.joineventstatemachine;

import logic.entity.Event;
import logic.entity.EventGoal;
import logic.enumeration.EventRequestState;
import logic.exception.DatabaseException;
import logic.exception.NoTransitionException;
import logic.interfaces.StateMachine;

import static logic.enumeration.EventRequestState.REJECTED;

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

        if(state == REJECTED){
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
