package com.pansoftware.logic.bean;

public class EventGoalQueryBean extends GoalQueryBean{
    String eventOrganizer = "";
    int eventId = -1;
    int requestState = 0;

    public String getEventOrganizer() {
        return this.eventOrganizer;
    }

    public void setEventOrganizer(final String eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    public int getEventId() {
        return this.eventId;
    }

    public void setEventId(final int eventId) {
        this.eventId = eventId;
    }

    public int getRequestState() {
        return this.requestState;
    }

    public void setRequestState(final int requestState) {
        this.requestState = requestState;
    }
}
