package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.bean.UpdateStepsBean;
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

    EventGoalBean item;

    @FXML
    private Label goalCellItemTotalSteps;

    @FXML
    private Label goalCellItemCompletedSteps;

    @FXML
    private Label goalCellItemId;

    @FXML
    private Label eventGoalCellEventOrganizer;

    @FXML
    private Label goalCellItemDescription;

    @FXML
    private Label goalCellItemDeadline;

    @FXML
    private Label eventGoalCellEvent;

    @FXML
    private Label goalCellItemTitle;

    @FXML
    private VBox adviceGoalCellBox;

    @FXML
    private TextField goalCellItemStepsTextField;

    @FXML
    private Button goalCellItemStepsButton;

    @FXML
    private Label eventGoalCellParticipationStatus;

    public EventGoalGraphicalController(final EventGoalBean goal) {
        item = goal;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        FxUtilities.hideVBox(adviceGoalCellBox);

        goalCellItemTitle.setText(item.getName());
        goalCellItemDeadline.setText("Deadline: " + item.getDeadline().toString());
        goalCellItemDescription.setText(item.getDescription());
        goalCellItemId.setText(String.valueOf(item.getId()));
        goalCellItemCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        goalCellItemTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));

        try {
            eventGoalCellEvent.setText(item.getEventName());
            eventGoalCellEventOrganizer.setText(item.getEventOrganizer());
            eventGoalCellParticipationStatus.setText(item.getState().toString());
        } catch (final NullPointerException e) {
            eventGoalCellEvent.setText("Event not specified");
            eventGoalCellEventOrganizer.setText("");
            eventGoalCellParticipationStatus.setText("");
        }
    }

    @FXML
    public void submit() throws Exception {
        final UpdateStepsBean bean = new UpdateStepsBean();

        bean.setUpdateId(item.getId());
        bean.setStepsCompleted(Integer.parseInt(goalCellItemStepsTextField.getText()));
        bean.setType(GoalType.EVENTGOAL);
        bean.setUpdateUser(Session.getSession().getUser());


        ManageGoalController.updateSteps(bean);
    }

}
