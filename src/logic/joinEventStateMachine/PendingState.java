package logic.joinEventStateMachine;

import logic.dao.EventGoalDao;
import logic.entity.Event;
import logic.enumeration.EventRequestState;

// @author Danilo D'Amico

public class PendingState extends JoinEventState{
	
	ConcreteStateMachine stateMachine;
	Event event;
	
	public PendingState(ConcreteStateMachine stateMachine, Event event) throws Exception {
		this.stateMachine = stateMachine;
		this.event = event;
		EventGoalDao.pendingEventGoal(event.getId(), event.getUser().getUsername());
	}

	@Override
	protected void accept() throws Exception {
		this.stateMachine.changeState(new AcceptedState(event));
	}

	@Override
	protected void reject() throws Exception {
		this.stateMachine.changeState(new RejectedState(event));
	}
	
	@Override
	protected EventRequestState getState() {
		return EventRequestState.PENDING;	
	}

}
