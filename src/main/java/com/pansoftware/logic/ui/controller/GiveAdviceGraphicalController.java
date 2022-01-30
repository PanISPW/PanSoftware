package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.AdviceGoalBean;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.ui.FxUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

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

    public void populateBox(List<AdviceGoalBean> goals) throws IOException {

        for (AdviceGoalBean item : goals) {

            AdviceCellGraphicalController cell = new AdviceCellGraphicalController(item);

            FXMLLoader loader = FxUtilities.loadFxml(Pages.ADVICEITEM);
            loader.setController(cell);


            VBox vbox = loader.load();

            adviceBox.getChildren().add(vbox);
        }

    }

    @FXML
    public void makeupAdviceRadio() throws Exception {
        adviceBox.getChildren().clear();
        List<AdviceGoalBean> goals = ManageGoalController.getUnansweredMakeupAdviceBean();
        populateBox(goals);
    }

    @FXML
    public void foodAdviceRadio() throws Exception {
        adviceBox.getChildren().clear();
        List<AdviceGoalBean> goals = ManageGoalController.getUnansweredFoodAdviceBean();
        populateBox(goals);
    }

    @FXML
    public void lifestyleAdviceRadio() throws Exception {
        adviceBox.getChildren().clear();
        List<AdviceGoalBean> goals = ManageGoalController.getUnansweredLifestyleAdviceBean();
        populateBox(goals);
    }

    @FXML
    public void otherAdviceRadio() throws Exception {
        adviceBox.getChildren().clear();
        List<AdviceGoalBean> goals = ManageGoalController.getUnansweredOtherAdviceBean();
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
