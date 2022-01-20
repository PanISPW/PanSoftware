package com.pansoftware.logic.interfaces;

import com.pansoftware.logic.entity.Event;

import java.util.List;

// @author Danilo D'Amico

public interface EventOrganizer {

    public List<Event> getOwnEvents();

    public List<Event> getPendingParticipations();
}
