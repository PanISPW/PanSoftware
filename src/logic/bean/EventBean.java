package logic.bean;

import logic.enumeration.EventType;
import logic.exception.InvalidDataException;
import logic.util.DataValidation;

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
        return id;
    }

    public void setId(int id) throws InvalidDataException {
        if (DataValidation.isNatural(id))
            this.id = id;
        else
            throw new InvalidDataException("id must be a natural number");
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) throws InvalidDataException {
        if (DataValidation.isNotPastDate(startingDate))
            this.startingDate = startingDate;
        else throw new InvalidDataException("The starting date must not be a past date");
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) throws InvalidDataException {
        if (endingDate.isAfter(startingDate))
            this.endingDate = endingDate;
        else throw new InvalidDataException("The ending date must not be a valid date after the starting one");
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        if (type.equals(EventType.PRIVATE))
            this.type = type;
    }
}
