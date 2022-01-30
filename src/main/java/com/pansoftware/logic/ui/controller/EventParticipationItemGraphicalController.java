package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.exception.InvalidDataException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class EventParticipationItemGraphicalController implements Initializable {

    EventGoalBean item;
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

    public EventParticipationItemGraphicalController(final EventGoalBean goal) {
        item = goal;
    }

    public EventGoalBean populateGoalBean() throws InvalidDataException {
        final EventGoalBean bean = new EventGoalBean();

        bean.setName(this.item.getName());
        bean.setDescription(this.item.getDescription());

        try {
            bean.setNumberOfSteps(this.item.getNumberOfSteps());
        } catch (final InvalidDataException e) {
            throw new InvalidDataException("invalid number of steps");
        }

        bean.setStepsCompleted(this.item.getStepsCompleted());
        bean.setNewDeadline(this.item.getDeadline());
        bean.setId(this.item.getId());
        bean.setUser(this.item.getUser());

        bean.setEventId(this.item.getEventId());
        bean.setEventOrganizer(this.item.getEventOrganizer());
        bean.setState(this.item.getState());

        return bean;
    }

    @FXML
    public void accept() throws Exception {
        final EventGoalBean bean = this.populateGoalBean();
        ManageGoalController.acceptEventGoal(bean);
    }

    @FXML
    public void reject() throws Exception {
        final EventGoalBean bean = this.populateGoalBean();
        ManageGoalController.rejectEventGoal(bean);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        this.eventParticipationTitle.setText(this.item.getName());
        this.eventParticipationId.setText(String.valueOf(this.item.getId()));
        this.eventParticipationDeadline.setText(this.item.getDeadline().toString());
        this.eventParticipationUser.setText(this.item.getUser());
        this.eventParticipationCompletedSteps.setText(String.valueOf(this.item.getStepsCompleted()));
        this.eventParticipationTotalSteps.setText(String.valueOf(this.item.getNumberOfSteps()));
        this.eventParticipationDescription.setText(this.item.getDescription());
    }

}
