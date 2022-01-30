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

    public void populateBox(final List<AdviceGoalBean> goals) throws IOException {

        for (final AdviceGoalBean item : goals) {

            final AdviceCellGraphicalController cell = new AdviceCellGraphicalController(item);

            final FXMLLoader loader = FxUtilities.loadFxml(Pages.ADVICEITEM);
            loader.setController(cell);


            final VBox vbox = loader.load();

            this.adviceBox.getChildren().add(vbox);
        }

    }

    @FXML
    public void makeupAdviceRadio() throws Exception {
        this.adviceBox.getChildren().clear();
        final List<AdviceGoalBean> goals = ManageGoalController.getUnansweredMakeupAdviceBean();
        this.populateBox(goals);
    }

    @FXML
    public void foodAdviceRadio() throws Exception {
        this.adviceBox.getChildren().clear();
        final List<AdviceGoalBean> goals = ManageGoalController.getUnansweredFoodAdviceBean();
        this.populateBox(goals);
    }

    @FXML
    public void lifestyleAdviceRadio() throws Exception {
        this.adviceBox.getChildren().clear();
        final List<AdviceGoalBean> goals = ManageGoalController.getUnansweredLifestyleAdviceBean();
        this.populateBox(goals);
    }

    @FXML
    public void otherAdviceRadio() throws Exception {
        this.adviceBox.getChildren().clear();
        final List<AdviceGoalBean> goals = ManageGoalController.getUnansweredOtherAdviceBean();
        this.populateBox(goals);
    }

    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {

        this.radioGroup = new ToggleGroup();
        this.makeupButton.setToggleGroup(this.radioGroup);
        this.foodButton.setToggleGroup(this.radioGroup);
        this.lifestyleButton.setToggleGroup(this.radioGroup);
        this.otherButton.setToggleGroup(this.radioGroup);

        this.makeupButton.setSelected(true);

        try {
            this.makeupAdviceRadio();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
