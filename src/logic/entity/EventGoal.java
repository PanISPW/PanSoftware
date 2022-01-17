package logic.entity;

import logic.dao.EventGoalDao;
import logic.enumeration.EventRequestState;
import logic.exception.DatabaseException;
import logic.exception.NoTransitionException;
import logic.exception.UserNotFoundException;
import logic.interfaces.StateMachine;
import logic.joineventstatemachine.ConcreteStateMachine;

import java.time.LocalDate;

// classe Client del pattern Machine State

// @author Danilo D'Amico

public class EventGoal extends Goal {

    private Event event;
    private User organizer;

    private StateMachine stateMachine = null;

    public EventGoal(String name, String description, int numberOfSteps, int stepsCompleted, LocalDate deadline, int id, User user, Event event, EventRequestState state) throws DatabaseException {
        super(name, description, numberOfSteps, stepsCompleted, deadline, user, id);
        this.event = event;

        try {
            this.organizer = event.getUser();
        } catch (NullPointerException e) {
            this.organizer = null;
        }

        this.stateMachine = new ConcreteStateMachine(event, this, state);
    }

    public static EventGoal getEventGoal(String user, int id) throws UserNotFoundException, Exception {
        return EventGoalDao.getEventGoal(user, id);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public EventRequestState getState() {
        return stateMachine.getState();
    }

    public void acceptJoinRequest() throws NoTransitionException, DatabaseException {
        stateMachine.answerRequest(EventRequestState.ACCEPTED);
    }

    public void rejectJoinRequest() throws NoTransitionException, DatabaseException {
        stateMachine.answerRequest(EventRequestState.REJECTED);
    }
}
