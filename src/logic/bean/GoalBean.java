package logic.bean;

import logic.enumeration.EventRequestState;
import logic.enumeration.GoalType;
import logic.enumeration.ProductType;
import logic.exception.InvalidDataException;
import logic.util.DataValidation;

import java.time.LocalDate;

// @author Danilo D'Amico


public class GoalBean {

    private GoalType goalType = GoalType.GOAL;

    private boolean reminder = false;

    private String name = "";
    private String description = "";
    private int numberOfSteps = 1;
    private int stepsCompleted = 0;
    private LocalDate deadline;

    private int id = -1;
    private String user = "";

    // adviceGoal
    private ProductType type = ProductType.NOTSPECIFIED;
    private String barcode = "";
    private String advice = "";
    private String adviceActivist = "";

    // eventGoal
    private int eventId = -1;
    private String eventOrganizer = "";
    private EventRequestState state = EventRequestState.PENDING;

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

    public void setNumberOfSteps(int numberOfStpes) throws InvalidDataException {
        if (numberOfSteps >= 1)
            this.numberOfSteps = numberOfStpes;
        else throw new InvalidDataException("there must be at least 1 step");
    }

    public int getStepsCompleted() {
        return stepsCompleted;
    }

    public void setStepsCompleted(int stepsCompleted) throws InvalidDataException {
        if (DataValidation.isNatural(stepsCompleted) | stepsCompleted >= numberOfSteps)
            this.stepsCompleted = stepsCompleted;
        else
            throw new InvalidDataException("you can only complete a positive number of steps lower than the total number");
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) throws InvalidDataException {
        if (DataValidation.isNotPastDate(deadline))
            this.deadline = deadline;
        else throw new InvalidDataException("please insert a future date");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (DataValidation.isNatural(id))
            this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        if (type.equals(ProductType.FOOD) | type.equals(ProductType.LIFESTYLE) | type.equals(ProductType.MAKEUP) | type.equals(ProductType.OTHER))
            this.type = type;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getAdviceActivist() {
        return adviceActivist;
    }

    public void setAdviceActivist(String adviceActivist) {
        this.adviceActivist = adviceActivist;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) throws InvalidDataException {
        if (DataValidation.isNatural(eventId))
            this.eventId = eventId;
        else throw new InvalidDataException("id should be a natural number");
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(String eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    public EventRequestState getState() {
        return state;
    }

    public void setState(EventRequestState state) {
        if (state.equals(EventRequestState.ACCEPTED) | state.equals(EventRequestState.REJECTED))
            this.state = state;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        if (goalType.equals(GoalType.ADVICEGOAL) | goalType.equals(GoalType.EVENTGOAL))
            this.goalType = goalType;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
