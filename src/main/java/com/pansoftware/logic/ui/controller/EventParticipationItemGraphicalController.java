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

    @FXML
    private Label resultLabel;

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

        bean.setName(item.getName());
        bean.setDescription(item.getDescription());

        try {
            bean.setNumberOfSteps(item.getNumberOfSteps());
        } catch (final InvalidDataException e) {
            throw new InvalidDataException("invalid number of steps");
        }

        bean.setStepsCompleted(item.getStepsCompleted());
        bean.setNewDeadline(item.getDeadline());
        bean.setId(item.getId());
        bean.setUser(item.getUser());

        bean.setEventId(item.getEventId());
        bean.setEventOrganizer(item.getEventOrganizer());
        bean.setState(item.getState());

        return bean;
    }

    @FXML
    public void accept() {
        try {
            final EventGoalBean bean = populateGoalBean();
            ManageGoalController.acceptEventGoal(bean);
            resultLabel.setText("Goal Successfully added");
        } catch(Exception e){
            resultLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void reject() throws Exception {
        try{
            final EventGoalBean bean = populateGoalBean();
            ManageGoalController.rejectEventGoal(bean);
            resultLabel.setText("Goal Successfully added");
        } catch(Exception e){
            resultLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        eventParticipationTitle.setText(item.getName());
        eventParticipationId.setText(String.valueOf(item.getId()));
        eventParticipationDeadline.setText(item.getDeadline().toString());
        eventParticipationUser.setText(item.getUser());
        eventParticipationCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        eventParticipationTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));
        eventParticipationDescription.setText(item.getDescription());
    }

}
