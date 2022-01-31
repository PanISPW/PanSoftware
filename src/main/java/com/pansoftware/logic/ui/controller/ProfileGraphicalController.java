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

    UserBean user;
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
        FxUtilities.showScrollPane(this.goalPane);
        FxUtilities.hideScrollPane(this.adviceGoalPane);
        FxUtilities.hideScrollPane(this.eventGoalPane);

        // lista
        final List<GoalBean> goals = ManageGoalController.getGoalBeanList();
        this.goalList.getChildren().clear();

        for (final GoalBean item : goals) {

            final GoalCellGraphicalController cell = new GoalCellGraphicalController(item);

            final FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
            loader.setController(cell);


            final VBox vbox = loader.load();

            this.goalList.getChildren().add(vbox);
        }
    }

    @FXML
    public void adviceGoalRadio() throws Exception {
        FxUtilities.showScrollPane(this.adviceGoalPane);
        FxUtilities.hideScrollPane(this.goalPane);
        FxUtilities.hideScrollPane(this.eventGoalPane);

        // lista
        final List<AdviceGoalBean> goals = ManageGoalController.getAdviceGoalBeanList();
        this.adviceGoalList.getChildren().clear();

        for (final AdviceGoalBean item : goals) {

            final AdviceGoalGraphicalController cell = new AdviceGoalGraphicalController(item);

            final FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
            loader.setController(cell);

            final VBox vbox = loader.load();

            adviceGoalList.getChildren().add(vbox);
        }
    }

    @FXML
    public void eventGoalRadio() throws Exception {
        FxUtilities.showScrollPane(eventGoalPane);
        FxUtilities.hideScrollPane(goalPane);
        FxUtilities.hideScrollPane(adviceGoalPane);

        final List<EventGoalBean> goals = ManageGoalController.getEventGoalBeanList();
        eventGoalList.getChildren().clear();

        for (final EventGoalBean item : goals) {

            final EventGoalGraphicalController cell = new EventGoalGraphicalController(item);

            final FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
            loader.setController(cell);

            final VBox vbox = loader.load();

            eventGoalList.getChildren().add(vbox);
        }
    }


    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {

        radioGroup = new ToggleGroup();
        goalListButton.setToggleGroup(radioGroup);
        adviceListButton.setToggleGroup(radioGroup);
        eventListButton.setToggleGroup(radioGroup);
        goalListButton.setSelected(true);

        try {
            user = ManageGoalController.getCurrentUserBean();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        nameLabel.setText(this.user.getName() + " " + user.getSurname());
        usernameLabel.setText("@" + user.getUsername());

        try {
            goalRadio();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
