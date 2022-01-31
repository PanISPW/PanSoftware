package com.pansoftware.logic.entity;

import com.pansoftware.logic.interfaces.UserContent;

import java.time.LocalDate;

// @author Danilo D'Amico

public class Goal extends UserContent {

    private String description;
    private int numberOfSteps;
    private int stepsCompleted;
    private LocalDate deadline;


    public Goal(final String name, final String description, final int numberOfSteps, final int stepsCompleted, final LocalDate deadline, final User user, final int id) {
        this.name = name;
        this.description = description;
        this.numberOfSteps = numberOfSteps;
        this.stepsCompleted = stepsCompleted;
        this.deadline = deadline;
        this.user = user;
        this.id = id;
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

    public int getStepsCompleted() {
        return this.stepsCompleted;
    }

    public void setStepsCompleted(final int stepsCompleted) {
        this.stepsCompleted = stepsCompleted;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public void setDeadline(final LocalDate deadline) {
        this.deadline = deadline;
    }

}