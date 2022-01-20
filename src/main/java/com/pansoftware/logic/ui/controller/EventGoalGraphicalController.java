package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.UpdateStepsBean;
import com.pansoftware.logic.entity.EventGoal;
import com.pansoftware.logic.enumeration.GoalType;
import com.pansoftware.logic.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.pansoftware.logic.ui.FxUtilities;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class EventGoalGraphicalController implements Initializable {

    EventGoal item;
    @FXML
    private VBox adviceGoalBox;
    @FXML
    private Label goalItemTitle;
    @FXML
    private Label goalItemDeadline;
    @FXML
    private Label goalItemDescription;
    @FXML
    private Label goalItemId;
    @FXML
    private Label goalItemCompletedSteps;
    @FXML
    private Label goalItemTotalSteps;
    @FXML
    private TextField goalItemStepsTextField;
    @FXML
    private Button goalItemStepsButton;
    @FXML
    private Label eventGoalEvent;
    @FXML
    private Label eventGoalEventId;
    @FXML
    private Label eventGoalParticipationStatus;

    // dovrei passarmi il bean non l'entity
    public EventGoalGraphicalController(EventGoal goal) {
        this.item = goal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxUtilities.hideVBox(adviceGoalBox);

        goalItemTitle.setText(item.getName());
        goalItemDeadline.setText("Deadline: " + item.getDeadline().toString());
        goalItemDescription.setText(item.getDescription());
        goalItemId.setText(String.valueOf(item.getId()));
        goalItemCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        goalItemTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));

        try {
            eventGoalEvent.setText(item.getEvent().getName());
            eventGoalEventId.setText(item.getOrganizer().getUsername());
            eventGoalParticipationStatus.setText(item.getState().toString());
        } catch (NullPointerException e) {
            eventGoalEvent.setText("Event not specified");
            eventGoalEventId.setText("");
            eventGoalParticipationStatus.setText("");
        }
    }

    @FXML
    public void submit() throws Exception {
        UpdateStepsBean bean = new UpdateStepsBean();

        bean.setId(item.getId());
        bean.setStepsCompleted(Integer.parseInt(goalItemStepsTextField.getText()));
        bean.setType(GoalType.EVENTGOAL);
        bean.setUser(Session.getSession().getUser());


        ManageGoalController.updateSteps(bean);
    }

}
