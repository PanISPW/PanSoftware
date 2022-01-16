package logic.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import logic.ManageGoalController;
import logic.bean.UpdateStepsBean;
import logic.entity.AdviceGoal;
import logic.enumeration.GoalType;
import logic.ui.FxUtilities;
import logic.util.Session;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class AdviceGoalGraphicalController implements Initializable {

    AdviceGoal item;
    @FXML
    private VBox eventGoalBox;
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
    private Label adviceGoalAdvice;
    @FXML
    private Label adviceGoalActivist;
    @FXML
    private Label adviceGoalProductType;
    @FXML
    private Label adviceGoalProductBarcode;

    // dovrei passarmi il bean non l'entity
    public AdviceGoalGraphicalController(AdviceGoal goal) {
        this.item = goal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String temp = "";
        FxUtilities.hideVBox(eventGoalBox);

        goalItemTitle.setText(item.getName());
        goalItemDeadline.setText("Deadline: " + item.getDeadline().toString());
        goalItemDescription.setText(item.getDescription());
        goalItemId.setText(String.valueOf(item.getId()));
        goalItemCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
        goalItemTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));

        temp = item.getAdvice();
        if (!temp.equals("") && item.getAdviceActivist() != null) {
            adviceGoalAdvice.setText(temp);
            adviceGoalActivist.setText(item.getAdviceActivist().getUsername());
        } else {
            adviceGoalAdvice.setText("not answered");
            adviceGoalActivist.setText("");
        }

        adviceGoalProductType.setText(item.getType().toString());

        temp = item.getProductBarcode();
        if (!temp.equals("")) {
            adviceGoalProductBarcode.setText(temp);
        } else {
            adviceGoalProductBarcode.setText("");
        }

        System.out.println("advice initialized");

    }

    @FXML
    public void submit() throws Exception {
        UpdateStepsBean bean = new UpdateStepsBean();

        bean.setId(item.getId());
        bean.setStepsCompleted(Integer.parseInt(goalItemStepsTextField.getText()));
        bean.setType(GoalType.ADVICEGOAL);
        bean.setUser(Session.getSession().getUser());


        ManageGoalController.updateSteps(bean);
    }

}
