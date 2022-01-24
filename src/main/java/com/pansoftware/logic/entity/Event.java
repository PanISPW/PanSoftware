package com.pansoftware.logic.entity;

import com.pansoftware.logic.enumeration.EventType;
import com.pansoftware.logic.interfaces.UserContent;

import java.time.LocalDate;

// @author Danilo D'Amico

public class Event extends UserContent {

    private LocalDate startingDate;
    private LocalDate endingDate;
    private EventType type; // private or public event

    public Event(User user, String name, LocalDate startingDate, LocalDate endingDate, EventType type, int id) {
        this.user = user;
        this.name = name;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.type = type;
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

}
