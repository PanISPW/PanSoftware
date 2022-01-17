package logic.entity;

import logic.enumeration.EventType;
import logic.interfaces.UserContent;

import java.time.LocalDate;

// @author Danilo D'Amico

public class Event implements UserContent {

    private int id;
    private User user; // utente in grado di organizzare eventi: Activist o Brand Manager quindi EventOrganizer
    private String name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
