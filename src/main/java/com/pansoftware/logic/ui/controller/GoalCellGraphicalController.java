package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.GoalBean;
import com.pansoftware.logic.bean.UpdateStepsBean;
import com.pansoftware.logic.enumeration.GoalType;
import com.pansoftware.logic.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.pansoftware.logic.ui.FxUtilities;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class GoalCellGraphicalController implements Initializable {

    GoalBean item;
    @FXML
    private VBox eventGoalCellBox;
    @FXML
    private VBox adviceGoalCellBox;
    @FXML
    private Separator goalCellSeparator;
    @FXML
    private Label goalCellItemTitle;
    @FXML
    private Label goalCellItemDeadline;
    @FXML
    private Label goalCellItemDescription;
    @FXML
    private Label goalCellItemId;
    @FXML
    private Label goalCellItemCompletedSteps;
    @FXML
    private Label goalCellItemTotalSteps;
    @FXML
    private TextField goalCellItemStepsTextField;
    @FXML
    private Button goalCellItemStepsButton;

    // dovrei passarmi il bean non l'entity
    public GoalCellGraphicalController(GoalBean goal) {
        this.item = goal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxUtilities.hideVBox(eventGoalCellBox);
        FxUtilities.hideVBox(adviceGoalCellBox);

        goalCellSeparator.setVisible(false);
        goalCellSeparator.managedProperty().bind(goalCellSeparator.visibleProperty());

        goalCellItemTitle.setText(item.getName());
        goalCellItemDeadline.setText("Deadline: " + item.getDeadline().toString());
        goalCellItemDescription.setText(item.getDescription());
        goalCellItemId.setText(String.valueOf(item.getId()));
        goalCellItemCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        goalCellItemTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));
    }

    @FXML
    public void submit() throws Exception {
        UpdateStepsBean bean = new UpdateStepsBean();

        bean.setUpdateId(item.getId());
        bean.setStepsCompleted(Integer.parseInt(goalCellItemStepsTextField.getText()));
        bean.setType(GoalType.GOAL);
        bean.setUpdateUser(Session.getSession().getUser());


        ManageGoalController.updateSteps(bean);
    }

}
