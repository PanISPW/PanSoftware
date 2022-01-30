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

    public AdviceGoalGraphicalController(AdviceGoalBean goal) {
        this.item = goal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String temp = "";
        FxUtilities.hideVBox(eventGoalCellBox);

        goalCellItemTitle.setText(item.getName());
        goalCellItemDeadline.setText("Deadline: " + item.getDeadline().toString());
        goalCellItemDescription.setText(item.getDescription());
        goalCellItemId.setText(String.valueOf(item.getId()));
        goalCellItemCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        goalCellItemTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));


        try {
            adviceGoalCellAdvice.setText(temp);
            adviceGoalCellActivist.setText(item.getAdviceActivist());
        } catch(NullPointerException e) {
            adviceGoalCellAdvice.setText("not answered");
            adviceGoalCellActivist.setText("");
        }

        adviceGoalCellProductType.setText(item.getType().toString());

    }

    @FXML
    public void submit() throws Exception {
        UpdateStepsBean bean = new UpdateStepsBean();

        bean.setUpdateId(item.getId());
        bean.setStepsCompleted(Integer.parseInt(goalCellItemStepsTextField.getText()));
        bean.setType(GoalType.ADVICEGOAL);
        bean.setUpdateUser(Session.getSession().getUser());


        ManageGoalController.updateSteps(bean);
    }

}
