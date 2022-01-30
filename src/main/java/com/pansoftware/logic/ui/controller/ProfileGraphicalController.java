package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.AdviceGoalBean;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.bean.GoalBean;
import com.pansoftware.logic.bean.UserBean;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.ui.FxUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class ProfileGraphicalController implements Initializable {

    UserBean user = null;
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private VBox goalList;
    @FXML
    private VBox adviceGoalList;
    @FXML
    private VBox eventGoalList;
    @FXML
    private ScrollPane goalPane;
    @FXML
    private ScrollPane adviceGoalPane;
    @FXML
    private ScrollPane eventGoalPane;
    @FXML
    private RadioButton goalListButton;
    @FXML
    private RadioButton adviceListButton;
    @FXML
    private RadioButton eventListButton;
    @FXML
    private ToggleGroup radioGroup;

    @FXML
    public void goalRadio() throws Exception {
        FxUtilities.showScrollPane(goalPane);
        FxUtilities.hideScrollPane(adviceGoalPane);
        FxUtilities.hideScrollPane(eventGoalPane);

        // lista
        List<GoalBean> goals = ManageGoalController.getGoalBeanList();
        goalList.getChildren().clear();

        for (GoalBean item : goals) {

            GoalCellGraphicalController cell = new GoalCellGraphicalController(item);

            FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
            loader.setController(cell);


            VBox vbox = loader.load();

            goalList.getChildren().add(vbox);
        }
    }

    @FXML
    public void adviceGoalRadio() throws Exception {
        FxUtilities.showScrollPane(adviceGoalPane);
        FxUtilities.hideScrollPane(goalPane);
        FxUtilities.hideScrollPane(eventGoalPane);

        // lista
        List<AdviceGoalBean> goals = ManageGoalController.getAdviceGoalBeanList();
        adviceGoalList.getChildren().clear();

        for (AdviceGoalBean item : goals) {

            AdviceGoalGraphicalController cell = new AdviceGoalGraphicalController(item);

            FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
            loader.setController(cell);

            VBox vbox = loader.load();

            adviceGoalList.getChildren().add(vbox);
        }
    }

    @FXML
    public void eventGoalRadio() throws Exception {
        FxUtilities.showScrollPane(eventGoalPane);
        FxUtilities.hideScrollPane(goalPane);
        FxUtilities.hideScrollPane(adviceGoalPane);

        //lista
        List<EventGoalBean> goals = ManageGoalController.getEventGoalBeanList();
        eventGoalList.getChildren().clear();

        for (EventGoalBean item : goals) {

            EventGoalGraphicalController cell = new EventGoalGraphicalController(item);

            FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
            loader.setController(cell);

            VBox vbox = loader.load();

            eventGoalList.getChildren().add(vbox);
        }
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        radioGroup = new ToggleGroup();
        goalListButton.setToggleGroup(radioGroup);
        adviceListButton.setToggleGroup(radioGroup);
        eventListButton.setToggleGroup(radioGroup);
        goalListButton.setSelected(true);

        try {
            user = ManageGoalController.getCurrentUserBean();
        } catch (Exception e) {
            e.printStackTrace();
        }

        nameLabel.setText(user.getName() + " " + user.getSurname());
        usernameLabel.setText("@" + user.getUsername());

        try {
            goalRadio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
