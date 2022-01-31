package com.pansoftware.logic.entity;

import com.pansoftware.logic.enumeration.ProductType;

import java.time.LocalDate;

// @author Danilo D'Amico

public class AdviceGoal extends Goal {

    private ProductType type;
    private String advice;
    private User adviceActivist;

    public AdviceGoal(final String name, final String description, final int numberOfSteps, final int stepsCompleted, final LocalDate deadline, final int id, final User user, final ProductType type, final String advice, final User adviceActivist) {
        super(name, description, numberOfSteps, stepsCompleted, deadline, user, id);
        this.type = type;
        this.advice = advice;
        this.adviceActivist = adviceActivist;

    }

    public AdviceGoal(final String name, final String description, final int numberOfSteps, final int stepsCompleted, final LocalDate deadline, final User user, final int id) {
        super(name, description, numberOfSteps, stepsCompleted, deadline, user, id);
    }

    public ProductType getType() {
        return this.type;
    }

    public void setType(final ProductType type) {
        this.type = type;
    }

    public String getAdvice() {
        return this.advice;
    }

    public void setAdvice(final String advice) {
        this.advice = advice;
    }

    public User getAdviceActivist() {
        return this.adviceActivist;
    }

    public void setAdviceActivist(final User adviceActivist) {
        this.adviceActivist = adviceActivist;
    }
}
