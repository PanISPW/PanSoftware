package com.pansoftware.logic.bean;

import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.util.DataValidation;

import java.time.LocalDate;

// @author Danilo D'Amico


public class GoalBean {

    private boolean reminder;

    private String name = "";
    private String description = "";
    private int numberOfSteps = 1;
    private int stepsCompleted;
    private LocalDate deadline;

    private int id = -1;
    private String user = "";

    public String getName() {
        return this.name;
    }

    public void setName(final String name) throws InvalidDataException {
        if(name.length() >= 45)
            throw new InvalidDataException("Goal Name should be under 45 characters");
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) throws InvalidDataException {
        if(description.length() >= 45)
            throw new InvalidDataException("Goal Description should be under 45 characters");
        this.description = description;
    }

    public int getNumberOfSteps() {
        return this.numberOfSteps;
    }

    public void setNumberOfSteps(final int numberOfSteps) throws InvalidDataException {
        if(!(numberOfSteps >= 1 && numberOfSteps <= 10))
            throw new InvalidDataException("Number of Steps must be between 1 and 10");

        this.numberOfSteps = numberOfSteps;
    }

    public int getStepsCompleted() {
        return this.stepsCompleted;
    }

    public void setStepsCompleted(final int stepsCompleted) throws InvalidDataException {
        if(!(stepsCompleted >= 0 && stepsCompleted <= this.numberOfSteps))
            throw new InvalidDataException("Steps Completed must be between 0 and Number of Steps");

        this.stepsCompleted = stepsCompleted;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public void setNewDeadline(final LocalDate deadline) throws InvalidDataException {
        if(deadline == null)
            throw new InvalidDataException("please insert a deadline");

        if (DataValidation.isNotPastDate(deadline))
            this.deadline = deadline;
        else throw new InvalidDataException("please insert a future date");
    }

    public void setDeadline(final LocalDate deadline) throws InvalidDataException {
        this.deadline = deadline;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) throws InvalidDataException {
        if (DataValidation.isNatural(id))
            this.id = id;
        else throw new InvalidDataException("It wasn't possible to generate a new Goal Identifier.");
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public boolean isReminder() {
        return this.reminder;
    }

    public void setReminder(final boolean reminder) {
        this.reminder = reminder;
    }

}
