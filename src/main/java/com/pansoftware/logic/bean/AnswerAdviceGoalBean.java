package com.pansoftware.logic.bean;

import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.util.DataValidation;

// @author Danilo D'Amico

public class AnswerAdviceGoalBean {

    private int id = -1;
    private String user = "";
    private String answer = "";

    public int getAnswerAdviceId() {
        return id;
    }

    public void setAnswerAdviceId(final int id) throws InvalidDataException {
        if (DataValidation.isNatural(id))
            this.id = id;
        else
            throw new InvalidDataException("id must be a natural number");
    }

    public String getGoalUser() {
        return user;
    }

    public void setGoalUser(final String user) {
        this.user = user;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String answer) throws InvalidDataException {
        if(answer.length() <= 45) {
            this.answer = answer;
        } else {
            throw new InvalidDataException("Advices must be under 45 characters");
        }
    }

}
