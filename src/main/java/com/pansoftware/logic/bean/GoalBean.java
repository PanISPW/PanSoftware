package com.pansoftware.logic.bean;

import com.pansoftware.logic.enumeration.EventRequestState;
import com.pansoftware.logic.enumeration.GoalType;
import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.util.DataValidation;

import java.time.LocalDate;

// @author Danilo D'Amico


public class GoalBean {

    private boolean reminder = false;

    private String name = "";
    private String surname = "";
    private String description = "";
    private int numberOfSteps = 1;
    private int stepsCompleted = 0;
    private LocalDate deadline;

    private int id = -1;
    private String user = "";

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidDataException {
        if(name.length() >= 45)
            throw new InvalidDataException("Goal Name should be under 45 characters");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidDataException {
        if(description.length() >= 45)
            throw new InvalidDataException("Goal Description should be under 45 characters");
        this.description = description;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) throws InvalidDataException {
        if(!(numberOfSteps >= 1 && numberOfSteps <= 10))
            throw new InvalidDataException("Number of Steps must be between 1 and 10");

        this.numberOfSteps = numberOfSteps;
    }

    public int getStepsCompleted() {
        return stepsCompleted;
    }

    public void setStepsCompleted(int stepsCompleted) throws InvalidDataException {
        if(!(stepsCompleted >= 0 && stepsCompleted <= numberOfSteps))
            throw new InvalidDataException("Steps Completed must be between 0 and Number of Steps");

        this.stepsCompleted = stepsCompleted;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) throws InvalidDataException {
        if(deadline == null)
            throw new InvalidDataException("please insert a deadline");

        if (DataValidation.isNotPastDate(deadline))
            this.deadline = deadline;
        else throw new InvalidDataException("please insert a future date");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws InvalidDataException {
        if (DataValidation.isNatural(id))
            this.id = id;
        else throw new InvalidDataException("It wasn't possible to generate a new Goal Identifier.");
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
