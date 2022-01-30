package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.AdviceGoalBean;
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

public class AdviceGoalGraphicalController implements Initializable {


    @FXML
    private VBox eventGoalCellBox;

    AdviceGoalBean item;

    @FXML
    private Label goalCellItemTitle;

    @FXML
    private Label goalCellItemDeadline;

    @FXML
    private Label adviceGoalCellActivist;

    @FXML
    private Label goalCellItemDescription;

    @FXML
    private Label goalCellItemId;

    @FXML
    private Label adviceGoalCellProductType;

    @FXML
    private Label goalCellItemCompletedSteps;

    @FXML
    private Label goalCellItemTotalSteps;

    @FXML
    private Label adviceGoalCellAdvice;

    @FXML
    private Button goalCellItemStepsButton;

    @FXML
    private TextField goalCellItemStepsTextField;

    public AdviceGoalGraphicalController(final AdviceGoalBean goal) {
        item = goal;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final String temp = "";
        FxUtilities.hideVBox(this.eventGoalCellBox);

        this.goalCellItemTitle.setText(this.item.getName());
        this.goalCellItemDeadline.setText("Deadline: " + this.item.getDeadline().toString());
        this.goalCellItemDescription.setText(this.item.getDescription());
        this.goalCellItemId.setText(String.valueOf(this.item.getId()));
        this.goalCellItemCompletedSteps.setText(String.valueOf(this.item.getStepsCompleted()));
        this.goalCellItemTotalSteps.setText(String.valueOf(this.item.getNumberOfSteps()));


        try {
            this.adviceGoalCellAdvice.setText(temp);
            this.adviceGoalCellActivist.setText(this.item.getAdviceActivist());
        } catch(final NullPointerException e) {
            this.adviceGoalCellAdvice.setText("not answered");
            this.adviceGoalCellActivist.setText("");
        }

        this.adviceGoalCellProductType.setText(this.item.getType().toString());

    }

    @FXML
    public void submit() throws Exception {
        final UpdateStepsBean bean = new UpdateStepsBean();

        bean.setUpdateId(this.item.getId());
        bean.setStepsCompleted(Integer.parseInt(this.goalCellItemStepsTextField.getText()));
        bean.setType(GoalType.ADVICEGOAL);
        bean.setUpdateUser(Session.getSession().getUser());


        ManageGoalController.updateSteps(bean);
    }

}
