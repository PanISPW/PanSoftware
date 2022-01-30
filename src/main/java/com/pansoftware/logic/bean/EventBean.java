package com.pansoftware.logic.bean;

import com.pansoftware.logic.enumeration.EventType;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.util.DataValidation;

import java.time.LocalDate;

// @author Danilo D'Amico

public class EventBean {

    private int id = -1;
    private String organizer = "";
    private String name = "";
    private LocalDate startingDate;
    private LocalDate endingDate;
    private EventType type = EventType.PUBLIC;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) throws InvalidDataException {
        if (DataValidation.isNatural(id))
            this.id = id;
        else
            throw new InvalidDataException("id must be a natural number");
    }

    public String getOrganizer() {
        return this.organizer;
    }

    public void setOrganizer(final String organizer) {
        this.organizer = organizer;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public LocalDate getStartingDate() {
        return this.startingDate;
    }

    public void setStartingDate(final LocalDate startingDate) throws InvalidDataException {
        this.startingDate = startingDate;
    }

    public void setNewStartingDate(final LocalDate startingDate) throws InvalidDataException {
        if (DataValidation.isNotPastDate(startingDate))
            this.startingDate = startingDate;
        else throw new InvalidDataException("The starting date must not be a past date");
    }

    public LocalDate getEndingDate() {
        return this.endingDate;
    }

    public void setEndingDate(final LocalDate endingDate) throws InvalidDataException {
        if (endingDate.isAfter(this.startingDate))
            this.endingDate = endingDate;
        else throw new InvalidDataException("The ending date must not be a valid date after the starting one");
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(final EventType type) {
        if (type.equals(EventType.PRIVATE))
            this.type = type;
    }
}
