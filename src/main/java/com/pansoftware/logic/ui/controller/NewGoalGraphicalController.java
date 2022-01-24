package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.AdviceGoalBean;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.bean.GoalBean;
import com.pansoftware.logic.enumeration.GoalType;
import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.DatabaseException;
import com.pansoftware.logic.exception.EmptyResultSetException;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.exception.UserNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import com.pansoftware.logic.ui.FxUtilities;

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
    private TextField stepsCompleted;

    @FXML
    private DatePicker deadline;

    @FXML
    private CheckBox reminder;

    @FXML
    private ComboBox<ProductType> productType;

    @FXML
    private TextField eventId;

    @FXML
    private TextField eventOrganizer;

    @FXML
    private Button submitButton;

    @FXML
    private VBox adviceGoal;

    @FXML
    private VBox eventGoal;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        List<ProductType> list = new ArrayList<>();
        list.add(ProductType.NOTSPECIFIED);
        list.add(ProductType.FOOD);
        list.add(ProductType.LIFESTYLE);
        list.add(ProductType.MAKEUP);
        list.add(ProductType.OTHER);

        ObservableList<ProductType> observable = FXCollections.observableList(list);

        productType.getItems().clear();
        productType.setItems(observable);
        productType.getSelectionModel().selectFirst();

        FxUtilities.hideVBox(adviceGoal);
        FxUtilities.hideVBox(eventGoal);

        radioGroup = new ToggleGroup();
        goalButton.setToggleGroup(radioGroup);
        adviceButton.setToggleGroup(radioGroup);
        eventButton.setToggleGroup(radioGroup);
        goalButton.setSelected(true);
    }

    @FXML
    public void goalRadio() {
        FxUtilities.hideVBox(adviceGoal);
        FxUtilities.hideVBox(eventGoal);
    }

    @FXML
    public void adviceGoalRadio() {
        FxUtilities.showVBox(adviceGoal);
        FxUtilities.hideVBox(eventGoal);
    }

    @FXML
    public void eventGoalRadio() {
        FxUtilities.hideVBox(adviceGoal);
        FxUtilities.showVBox(eventGoal);
    }

    @FXML
    public void submit() throws Exception {


        RadioButton selected = (RadioButton) radioGroup.getSelectedToggle();
        String selectedButton = selected.getText();

        switch (selectedButton) {
            case "EventGoal" -> {
                EventGoalBean eventGoalBean = new EventGoalBean();
                eventGoalBean.setEventId(Integer.parseInt(eventId.getText()));
                eventGoalBean.setEventOrganizer(eventOrganizer.getText());
                fillBeanAndCreateGoal(eventGoalBean);
            }
            case "AdviceGoal" -> {
                AdviceGoalBean adviceGoalBean = new AdviceGoalBean();
                adviceGoalBean.setType(productType.getValue());
                fillBeanAndCreateGoal(adviceGoalBean);
            }
            default -> {
                GoalBean goalBean = new GoalBean();
                fillBeanAndCreateGoal(goalBean);
            }
        }
    }

    public void fillBeanAndCreateGoal(GoalBean bean) throws InvalidDataException, UserNotFoundException, SQLException, EmptyResultSetException, LoginException, DatabaseException {

        try {
            bean.setName(goalName.getText());
            bean.setDescription(goalDescription.getText());

            bean.setNumberOfSteps(Integer.parseInt(numberOfSteps.getText()));
            bean.setStepsCompleted(Integer.parseInt(stepsCompleted.getText()));

            bean.setDeadline(deadline.getValue());
            bean.setReminder(reminder.isSelected());

            new ManageGoalController().createGoal(bean);
            resultLabel.setText("Goal Successfully added");
        } catch (InvalidDataException e) {
            resultLabel.setText(e.getMessage());
        }

    }
}
