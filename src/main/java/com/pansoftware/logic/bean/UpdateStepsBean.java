package com.pansoftware.logic.bean;

import com.pansoftware.logic.enumeration.GoalType;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.util.DataValidation;

// @author Danilo D'Amico

public class UpdateStepsBean {

    private GoalType type = GoalType.GOAL;
    private int stepsCompleted;
    private int id = -1;
    private String user = "";

    public GoalType getType() {
        return this.type;
    }

    public void setType(final GoalType type) {
        if (type.equals(GoalType.ADVICEGOAL) | type.equals(GoalType.EVENTGOAL))
            this.type = type;
    }

    public int getStepsCompleted() {
        return this.stepsCompleted;
    }

    public void setStepsCompleted(final int stepsCompleted) throws InvalidDataException {
        if (DataValidation.isNatural(stepsCompleted))
            this.stepsCompleted = stepsCompleted;
        else throw new InvalidDataException("steps completed must be a natural number");
    }

    public int getUpdateId() {
        return this.id;
    }

    public void setUpdateId(final int id) throws InvalidDataException {
        if (DataValidation.isNatural(id))
            this.id = id;
        else throw new InvalidDataException("id must be a natural number");
    }

    public String getUpdateUser() {
        return this.user;
    }

    public void setUpdateUser(final String user) {
        this.user = user;
    }
}
