package logic.interfaces;

import logic.entity.Event;

import java.util.List;

// @author Danilo D'Amico

public interface EventOrganizer {

    public List<Event> getOwnEvents();

    public List<Event> getPendingParticipations();
}
