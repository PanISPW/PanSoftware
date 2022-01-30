package com.pansoftware.logic.ui;

import com.pansoftware.logic.enumeration.EventRequestState;

import java.time.LocalDate;

public class EventGoalBeanUtil {

    private static EventGoalBeanUtil instance = null;

    private String name = "";
    private String description = "";
    private int numberOfSteps = 1;
    private LocalDate deadline;

    private int id = -1;
    private String user = "";

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
