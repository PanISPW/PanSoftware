package logic.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import logic.ManageGoalController;
import logic.bean.GoalBean;
import logic.enumeration.GoalType;
import logic.enumeration.ProductType;
import logic.enumeration.UserRole;
import logic.ui.FxUtilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class NewGoalGraphicalController implements Initializable {

    UserRole role = null;

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

        List<ProductType> list = new ArrayList<ProductType>();
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
        GoalBean bean = new GoalBean();

        RadioButton selected = (RadioButton) radioGroup.getSelectedToggle();
        String selectedButton = selected.getText();
        GoalType type;

        switch (selectedButton) {
            case "EventGoal":
                type = GoalType.EVENTGOAL;

                bean.setEventId(Integer.valueOf(eventId.getText()));
                bean.setEventOrganizer(eventOrganizer.getText());

                break;
            case "AdviceGoal":
                type = GoalType.ADVICEGOAL;
                bean.setType((ProductType) productType.getValue());
                break;
            default:
                type = GoalType.GOAL;
        }

        bean.setGoalType(type);
        bean.setName(goalName.getText());
        bean.setDescription(goalDescription.getText());

        bean.setNumberOfSteps(Integer.valueOf(numberOfSteps.getText()));
        bean.setStepsCompleted(Integer.valueOf(stepsCompleted.getText()));

        bean.setDeadline(deadline.getValue());
        bean.setReminder(reminder.isSelected());

        new ManageGoalController().createGoal(bean);
    }
}
