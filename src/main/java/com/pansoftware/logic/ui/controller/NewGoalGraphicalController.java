package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.AdviceGoalBean;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.bean.GoalBean;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.exception.UserNotFoundException;
import com.pansoftware.logic.ui.EventGoalBeanUtil;
import com.pansoftware.logic.ui.FxUtilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class NewGoalGraphicalController implements Initializable {

    @FXML
    private Label resultLabel;

    @FXML
    private RadioButton goalButton;

    @FXML
    private RadioButton adviceButton;

    @FXML
    private RadioButton eventButton;

    @FXML
    private ToggleGroup radioGroup;

    @FXML
    private TextField goalName;

    @FXML
    private TextField goalDescription;

    @FXML
    private TextField numberOfSteps;

    @FXML
    private DatePicker deadline;

    @FXML
    private CheckBox reminder;

    @FXML
    private ComboBox<ProductType> productType;

    @FXML
    private VBox adviceGoal;

    @FXML
    private Button submitButton;

    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {

        final List<ProductType> list = new ArrayList<>();
        list.add(ProductType.NOTSPECIFIED);
        list.add(ProductType.FOOD);
        list.add(ProductType.LIFESTYLE);
        list.add(ProductType.MAKEUP);
        list.add(ProductType.OTHER);

        final ObservableList<ProductType> observable = FXCollections.observableList(list);

        this.productType.getItems().clear();
        this.productType.setItems(observable);
        this.productType.getSelectionModel().selectFirst();

        FxUtilities.hideVBox(this.adviceGoal);

        this.radioGroup = new ToggleGroup();
        this.goalButton.setToggleGroup(this.radioGroup);
        this.adviceButton.setToggleGroup(this.radioGroup);
        this.eventButton.setToggleGroup(this.radioGroup);
        this.goalButton.setSelected(true);
    }

    @FXML
    public void goalRadio() {
        FxUtilities.hideVBox(this.adviceGoal);
    }

    @FXML
    public void adviceGoalRadio() {
        FxUtilities.showVBox(this.adviceGoal);
    }

    @FXML
    public void eventGoalRadio() {
        FxUtilities.hideVBox(this.adviceGoal);
    }

    @FXML
    public void submit() throws Exception {


        final RadioButton selected = (RadioButton) this.radioGroup.getSelectedToggle();
        final String selectedButton = selected.getText();

        switch (selectedButton) {
            case "EventGoal" -> {
                final EventGoalBeanUtil util = EventGoalBeanUtil.getEventGoalBeanUtil();

                util.setName(goalName.getText());
                util.setDescription(goalDescription.getText());

                util.setNumberOfSteps(Integer.parseInt(numberOfSteps.getText()));

                util.setDeadline(deadline.getValue());
                util.setReminder(reminder.isSelected());

                final NavbarGraphicalController navbar = NavbarGraphicalController.getInstance();
                navbar.changePage(Pages.EVENTSELECTION);
            }
            case "AdviceGoal" -> {
                final AdviceGoalBean adviceGoalBean = new AdviceGoalBean();
                adviceGoalBean.setType(productType.getValue());
                fillBean(adviceGoalBean);

                ManageGoalController.createGoal(adviceGoalBean);
                resultLabel.setText("Goal Successfully added");
            }
            default -> {
                final GoalBean goalBean = new GoalBean();
                fillBean(goalBean);

                ManageGoalController.createGoal(goalBean);
                resultLabel.setText("Goal Successfully added");
            }
        }
    }

    private void fillBean(final GoalBean bean) {

        try {
            bean.setName(goalName.getText());
            bean.setDescription(goalDescription.getText());

            bean.setNumberOfSteps(Integer.parseInt(numberOfSteps.getText()));
            bean.setStepsCompleted(0);

            bean.setNewDeadline(deadline.getValue());
            bean.setReminder(reminder.isSelected());

        } catch (final InvalidDataException e) {
            resultLabel.setText(e.getMessage());
        }

    }
}
