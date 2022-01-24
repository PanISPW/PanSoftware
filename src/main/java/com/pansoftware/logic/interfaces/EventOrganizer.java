package com.pansoftware.logic.interfaces;

import com.pansoftware.logic.entity.Event;

import java.util.List;

// @author Danilo D'Amico

public interface EventOrganizer {

    List<Event> getOwnEvents();

    List<Event> getPendingParticipations();
}
