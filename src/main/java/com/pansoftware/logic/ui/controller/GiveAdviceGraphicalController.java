package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.entity.AdviceGoal;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.exception.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import com.pansoftware.logic.ui.FxUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class GiveAdviceGraphicalController implements Initializable {

    @FXML
    private RadioButton makeupButton;

    @FXML
    private RadioButton foodButton;

    @FXML
    private RadioButton lifestyleButton;

    @FXML
    private RadioButton otherButton;

    @FXML
    private ToggleGroup radioGroup;

    @FXML
    private VBox adviceBox;

    public void populateBox(List<AdviceGoal> goals) throws IOException {

        for (AdviceGoal item : goals) {

            AdviceCellGraphicalController cell = new AdviceCellGraphicalController(item);

            FXMLLoader loader = FxUtilities.loadFxml(Pages.ADVICEITEM);
            loader.setController(cell);


            VBox vbox = loader.load();

            adviceBox.getChildren().add(vbox);
        }

    }

    @FXML
    public void makeupAdviceRadio() throws UserNotFoundException, Exception {
        adviceBox.getChildren().clear();
        List<AdviceGoal> goals = ManageGoalController.getUnansweredMakeupAdvice();
        populateBox(goals);
    }

    @FXML
    public void foodAdviceRadio() throws UserNotFoundException, Exception {
        adviceBox.getChildren().clear();
        List<AdviceGoal> goals = ManageGoalController.getUnansweredFoodAdvice();
        populateBox(goals);
    }

    @FXML
    public void lifestyleAdviceRadio() throws UserNotFoundException, Exception {
        adviceBox.getChildren().clear();
        List<AdviceGoal> goals = ManageGoalController.getUnansweredLifestyleAdvice();
        populateBox(goals);
    }

    @FXML
    public void otherAdviceRadio() throws UserNotFoundException, Exception {
        adviceBox.getChildren().clear();
        List<AdviceGoal> goals = ManageGoalController.getUnansweredOtherAdvice();
        populateBox(goals);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        radioGroup = new ToggleGroup();
        makeupButton.setToggleGroup(radioGroup);
        foodButton.setToggleGroup(radioGroup);
        lifestyleButton.setToggleGroup(radioGroup);
        otherButton.setToggleGroup(radioGroup);

        makeupButton.setSelected(true);

        try {
            makeupAdviceRadio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
