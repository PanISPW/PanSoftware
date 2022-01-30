package com.pansoftware.logic.bean;

import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.util.DataValidation;

public class EventGoalBean extends GoalBean{
    private int eventId = -1;
    private String eventOrganizer = "";
    private String eventName = "";
    private EventRequestState state = EventRequestState.PENDING;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) throws InvalidDataException {
        if (DataValidation.isNatural(eventId))
            this.eventId = eventId;
        else throw new InvalidDataException("id should be a natural number");
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(String eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    public EventRequestState getState() {
        return state;
    }

    public void setState(EventRequestState state) throws InvalidDataException {
        if (state.equals(EventRequestState.ACCEPTED) | state.equals(EventRequestState.REJECTED))
            this.state = state;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
