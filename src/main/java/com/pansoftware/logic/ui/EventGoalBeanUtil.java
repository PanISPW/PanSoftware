package com.pansoftware.logic.ui;

import com.pansoftware.logic.enumeration.EventRequestState;

import java.time.LocalDate;

public class EventGoalBeanUtil {

    private static EventGoalBeanUtil instance;

    private String name = "";
    private String description = "";
    private int numberOfSteps = 1;
    private LocalDate deadline;

    private int id = -1;
    private String user = "";

    private EventRequestState state = EventRequestState.PENDING;

    public boolean isReminder() {
        return this.reminder;
    }

    public void setReminder(final boolean reminder) {
        this.reminder = reminder;
    }

    private boolean reminder;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getNumberOfSteps() {
        return this.numberOfSteps;
    }

    public void setNumberOfSteps(final int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public void setDeadline(final LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public EventRequestState getState() {
        return this.state;
    }

    public void setState(final EventRequestState state) {
        this.state = state;
    }

    private EventGoalBeanUtil() {}

    public static EventGoalBeanUtil getEventGoalBeanUtil() {
        if (EventGoalBeanUtil.instance == null)
            EventGoalBeanUtil.instance = new EventGoalBeanUtil();

        return EventGoalBeanUtil.instance;
    }

    public static void invalidate(){
        EventGoalBeanUtil.instance = null;
    }
}
