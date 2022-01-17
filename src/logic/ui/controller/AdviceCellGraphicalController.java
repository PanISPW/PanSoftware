package logic.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.ManageGoalController;
import logic.bean.AnswerAdviceGoalBean;
import logic.entity.AdviceGoal;
import logic.exception.UserNotFoundException;

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
    public void submitAdvice() throws UserNotFoundException, Exception {

        AnswerAdviceGoalBean bean = new AnswerAdviceGoalBean();

        bean.setId(item.getId());
        bean.setUser(item.getUser().getUsername());
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
