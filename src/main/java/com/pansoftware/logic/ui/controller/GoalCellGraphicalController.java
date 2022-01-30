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
    public GoalCellGraphicalController(final GoalBean goal) {
        item = goal;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        FxUtilities.hideVBox(this.eventGoalCellBox);
        FxUtilities.hideVBox(this.adviceGoalCellBox);

        this.goalCellSeparator.setVisible(false);
        this.goalCellSeparator.managedProperty().bind(this.goalCellSeparator.visibleProperty());

        this.goalCellItemTitle.setText(this.item.getName());
        this.goalCellItemDeadline.setText("Deadline: " + this.item.getDeadline().toString());
        this.goalCellItemDescription.setText(this.item.getDescription());
        this.goalCellItemId.setText(String.valueOf(this.item.getId()));
        this.goalCellItemCompletedSteps.setText(String.valueOf(this.item.getStepsCompleted()));
        this.goalCellItemTotalSteps.setText(String.valueOf(this.item.getNumberOfSteps()));
    }

    @FXML
    public void submit() throws Exception {
        final UpdateStepsBean bean = new UpdateStepsBean();

        bean.setUpdateId(this.item.getId());
        bean.setStepsCompleted(Integer.parseInt(this.goalCellItemStepsTextField.getText()));
        bean.setType(GoalType.GOAL);
        bean.setUpdateUser(Session.getSession().getUser());


        ManageGoalController.updateSteps(bean);
    }

}
