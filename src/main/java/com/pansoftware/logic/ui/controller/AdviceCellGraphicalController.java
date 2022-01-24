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
    private Label adviceGoalTitle;
    @FXML
    private Label adviceGoalId;
    @FXML
    private Label adviceGoalDeadline;
    @FXML
    private Label adviceGoalUser;
    @FXML
    private Label adviceCompletedSteps;
    @FXML
    private Label adviceTotalSteps;
    @FXML
    private Label adviceGoalDescription;
    @FXML
    private Label adviceProductBarcode;
    @FXML
    private TextField adviceTextField;
    @FXML
    private Button adviceSubmitButton;

    // dovrei passarmi il bean non l'entity
    public AdviceCellGraphicalController(AdviceGoal goal) {
        this.item = goal;
    }

    @FXML
    public void submitAdvice() throws Exception {

        AnswerAdviceGoalBean bean = new AnswerAdviceGoalBean();

        bean.setAnswerAdviceId(item.getId());
        bean.setGoalUser(item.getUser().getUsername());
        bean.setAnswer(adviceTextField.getText());

        ManageGoalController.answerAdviceGoal(bean);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        adviceGoalTitle.setText(item.getName());
        adviceGoalId.setText(String.valueOf(item.getId()));
        adviceGoalDeadline.setText(item.getDeadline().toString());
        adviceGoalUser.setText(item.getUser().getUsername());
        adviceCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        adviceTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));
        adviceGoalDescription.setText(item.getDescription());
        adviceProductBarcode.setText(item.getProductBarcode());
    }

}
