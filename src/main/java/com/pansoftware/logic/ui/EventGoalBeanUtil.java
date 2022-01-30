package com.pansoftware.logic.ui;

import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.util.Session;

import java.time.LocalDate;

public class EventGoalBeanUtil {

    private static EventGoalBeanUtil instance = null;

    private String name = "";
    private String description = "";
    private int numberOfSteps = 1;
    private int stepsCompleted = 0;
    private LocalDate deadline;

    private int id = -1;
    private String user = "";

    private int eventId = -1;
    private String eventOrganizer = "";
    private String eventName = "";
    private EventRequestState state = EventRequestState.PENDING;

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    private boolean reminder = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public int getStepsCompleted() {
        return stepsCompleted;
    }

    public void setStepsCompleted(int stepsCompleted) {
        this.stepsCompleted = stepsCompleted;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(String eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventRequestState getState() {
        return state;
    }

    public void setState(EventRequestState state) {
        this.state = state;
    }

    private EventGoalBeanUtil() {}

    public static EventGoalBeanUtil getEventGoalBeanUtil() {
        if (instance == null)
            instance = new EventGoalBeanUtil();

        return instance;
    }

    public static void invalidate(){
        instance = null;
    }
}
