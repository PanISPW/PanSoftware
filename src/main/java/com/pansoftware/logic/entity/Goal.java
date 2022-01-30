package com.pansoftware.logic.entity;

import com.pansoftware.logic.dao.GoalDao;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.interfaces.UserContent;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
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

    public static Goal getGoal(final String user, final int id) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return GoalDao.getGoal(user, id);
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