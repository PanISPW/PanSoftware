package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.AnswerAdviceGoalBean;
import com.pansoftware.logic.entity.AdviceGoal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class AdviceCellGraphicalController implements Initializable {

    AdviceGoal item;
    @FXML
    private Label adviceCellGoalTitle;
    @FXML
    private Label adviceCellGoalId;
    @FXML
    private Label adviceCellGoalDeadline;
    @FXML
    private Label adviceCellGoalUser;
    @FXML
    private Label adviceCellCompletedSteps;
    @FXML
    private Label adviceCellTotalSteps;
    @FXML
    private Label adviceCellGoalDescription;
    @FXML
    private Label adviceCellProductBarcode;
    @FXML
    private TextField adviceCellTextField;
    @FXML
    private Button adviceCellSubmitButton;

    // dovrei passarmi il bean non l'entity
    public AdviceCellGraphicalController(AdviceGoal goal) {
        this.item = goal;
    }

    @FXML
    public void submitAdvice() throws Exception {

        AnswerAdviceGoalBean bean = new AnswerAdviceGoalBean();

        bean.setAnswerAdviceId(item.getId());
        bean.setGoalUser(item.getUser().getUsername());
        bean.setAnswer(adviceCellTextField.getText());

        ManageGoalController.answerAdviceGoal(bean);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        adviceCellGoalTitle.setText(item.getName());
        adviceCellGoalId.setText(String.valueOf(item.getId()));
        adviceCellGoalDeadline.setText(item.getDeadline().toString());
        adviceCellGoalUser.setText(item.getUser().getUsername());
        adviceCellCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        adviceCellTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));
        adviceCellGoalDescription.setText(item.getDescription());
        adviceCellProductBarcode.setText(item.getProductBarcode());
    }

}
