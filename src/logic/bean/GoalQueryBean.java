package logic.bean;

import java.sql.Date;
import java.time.LocalDate;

public class GoalQueryBean {

    private String name = "";
    private String description = "";
    private int numberOfSteps = 1;
    private int stepsCompleted = 0;
    private java.sql.Date deadline;

    private int id = -1;
    private String user = "";

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

    public int getStepsCompleted() {
        return this.stepsCompleted;
    }

    public void setStepsCompleted(final int stepsCompleted) {
        this.stepsCompleted = stepsCompleted;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(final Date deadline) {
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
}
