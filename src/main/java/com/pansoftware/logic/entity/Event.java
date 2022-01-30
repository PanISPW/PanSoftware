package com.pansoftware.logic.entity;

import com.pansoftware.logic.enumeration.EventType;
import com.pansoftware.logic.interfaces.UserContent;

import java.time.LocalDate;

// @author Danilo D'Amico

public class Event extends UserContent {

    private LocalDate startingDate;
    private LocalDate endingDate;
    private EventType type; // private or public event

    public Event(final User user, final String name, final LocalDate startingDate, final LocalDate endingDate, final EventType type, final int id) {
        this.user = user;
        this.name = name;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.type = type;
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return this.startingDate;
    }

    public void setStartingDate(final LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return this.endingDate;
    }

    public void setEndingDate(final LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(final EventType type) {
        this.type = type;
    }

}
