package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.AdviceGoalBean;
import com.pansoftware.logic.bean.AnswerAdviceGoalBean;
import com.pansoftware.logic.exception.InvalidDataException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class AdviceCellGraphicalController implements Initializable {

    @FXML
    private Label resultLabel;

    AdviceGoalBean item;
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
    private TextField adviceCellTextField;
    @FXML
    private Button adviceCellSubmitButton;

    public AdviceCellGraphicalController(final AdviceGoalBean goal) {
        item = goal;
    }

    @FXML
    public void submitAdvice() throws InvalidDataException {

        final AnswerAdviceGoalBean bean = new AnswerAdviceGoalBean();

        try {
            bean.setAnswerAdviceId(this.item.getId());
            bean.setGoalUser(this.item.getUser());
            bean.setAnswer(this.adviceCellTextField.getText());

            ManageGoalController.answerAdviceGoal(bean);
            resultLabel.setText("Advice Successfully added");
        } catch (Exception e){
            resultLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        this.adviceCellGoalTitle.setText(this.item.getName());
        this.adviceCellGoalId.setText(String.valueOf(this.item.getId()));
        this.adviceCellGoalDeadline.setText(this.item.getDeadline().toString());
        this.adviceCellGoalUser.setText(this.item.getUser());
        this.adviceCellCompletedSteps.setText(String.valueOf(this.item.getStepsCompleted()));
        this.adviceCellTotalSteps.setText(String.valueOf(this.item.getNumberOfSteps()));
        this.adviceCellGoalDescription.setText(this.item.getDescription());
    }

}
