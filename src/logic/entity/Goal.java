package logic.entity;

import logic.dao.GoalDao;
import logic.exception.DatabaseException;
import logic.exception.EmptyResultSetException;
import logic.exception.UserNotFoundException;
import logic.interfaces.UserContent;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.time.LocalDate;

// @author Danilo D'Amico

public class Goal implements UserContent {

    private String name;
    private String description;
    private int numberOfSteps;
    private int stepsCompleted;
    private LocalDate deadline;
    private int id;
    private User user;

    public Goal(String name, String description, int numberOfSteps, int stepsCompleted, LocalDate deadline, User user, int id) {
        this.name = name;
        this.description = description;
        this.numberOfSteps = numberOfSteps;
        this.stepsCompleted = stepsCompleted;
        this.deadline = deadline;
        this.user = user;
        this.id = id;
    }

    public static Goal getGoal(String user, int id) throws UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {
        return GoalDao.getGoal(user, id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

}