package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.bean.GoalBean;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.enumeration.GoalType;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.exception.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class EventParticipationItemGraphicalController implements Initializable {

    EventGoal item;
    @FXML
    private Label eventParticipationTitle;
    @FXML
    private Label eventParticipationId;
    @FXML
    private Label eventParticipationDeadline;
    @FXML
    private Label eventParticipationUser;
    @FXML
    private Label eventParticipationCompletedSteps;
    @FXML
    private Label eventParticipationTotalSteps;
    @FXML
    private Label eventParticipationDescription;
    @FXML
    private Button eventParticipationAccept;
    @FXML
    private Button eventParticipationReject;

    public EventParticipationItemGraphicalController(EventGoal goal) {
        this.item = goal;
    }

    public EventGoalBean populateGoalBean() throws InvalidDataException {
        EventGoalBean bean = new EventGoalBean();

        bean.setName(item.getName());
        bean.setDescription(item.getDescription());

        try {
            bean.setNumberOfSteps(item.getNumberOfSteps());
        } catch (InvalidDataException e) {
            throw new InvalidDataException("invalid number of steps");
        }

        bean.setStepsCompleted(item.getStepsCompleted());
        bean.setDeadline(item.getDeadline());
        bean.setId(item.getId());
        bean.setUser(item.getUser().getUsername());

        bean.setEventId(item.getEvent().getId());
        bean.setEventOrganizer(item.getEvent().getUser().getUsername());
        bean.setState(item.getState());

        return bean;
    }

    @FXML
    public void accept() throws UserNotFoundException, Exception {
        EventGoalBean bean = populateGoalBean();
        ManageGoalController.acceptEventGoal(bean);
    }

    @FXML
    public void reject() throws UserNotFoundException, Exception {
        EventGoalBean bean = populateGoalBean();
        ManageGoalController.rejectEventGoal(bean);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eventParticipationTitle.setText(item.getName());
        eventParticipationId.setText(String.valueOf(item.getId()));
        eventParticipationDeadline.setText(item.getDeadline().toString());
        eventParticipationUser.setText(item.getUser().getUsername());
        eventParticipationCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        eventParticipationTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));
        eventParticipationDescription.setText(item.getDescription());
    }

}
