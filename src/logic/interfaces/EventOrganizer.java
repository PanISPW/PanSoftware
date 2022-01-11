package logic.interfaces;

import java.util.List;

import logic.entity.Event;

// @author Danilo D'Amico

public interface EventOrganizer {
	
	public List<Event> getOwnEvents();
	public List<Event> getPendingParticipations();
}
