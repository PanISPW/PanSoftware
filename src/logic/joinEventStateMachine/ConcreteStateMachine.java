package logic.joinEventStateMachine;

import logic.entity.Event;
import logic.entity.EventGoal;
import logic.enumeration.EventRequestState;
import logic.interfaces.StateMachine;

// @author Danilo D'Amico

public class ConcreteStateMachine implements StateMachine {
	
	private Event event;
	private EventGoal goal;
	
	public EventGoal getGoal() {
		return goal;
	}

	public void setGoal(EventGoal goal) {
		this.goal = goal;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	private JoinEventState currentState; // abstract state
	
	public ConcreteStateMachine(Event event, EventGoal goal, EventRequestState state) throws Exception {
		this.event = event;
		this.goal = goal;

		currentState = JoinEventState.getMachineState(this, state, event);
	}

	@Override
	public void answerRequest(EventRequestState state) throws Exception {
		
		switch(state) {
		case REJECTED:
			currentState.reject();
			break;
		default:
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

	
}
