package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.EventBean;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.ui.EventGoalBeanUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EventSelectionItemGraphicalController implements Initializable {


    EventBean item;
    @FXML
    private VBox eventItemVBox;
    @FXML
    private Label eventTitle;
    @FXML
    private Label eventDuration;
    @FXML
    private Label eventId;
    @FXML
    private Label eventOrganizer;
    @FXML
    private Label eventType;

    public EventSelectionItemGraphicalController(EventBean event) {
        this.item = event;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eventTitle.setText(item.getName());
        eventId.setText(String.valueOf(item.getId()));
        eventOrganizer.setText(item.getOrganizer());
        eventDuration.setText(item.getStartingDate().toString() + " - " + item.getEndingDate().toString());
        eventType.setText(item.getType().toString());
    }

    @FXML
    public void submit() throws Exception {

        EventGoalBeanUtil util = EventGoalBeanUtil.getEventGoalBeanUtil();

        EventGoalBean bean = new EventGoalBean();


        bean.setName(util.getName());
        bean.setDescription(util.getDescription());

        bean.setNumberOfSteps(util.getNumberOfSteps());
        bean.setStepsCompleted(0);

        bean.setNewDeadline(util.getDeadline());
        bean.setReminder(util.isReminder());


        bean.setEventId(item.getId());
        bean.setEventOrganizer(item.getOrganizer());

        ManageGoalController.createGoal(bean);
        EventGoalBeanUtil.invalidate();

        NavbarGraphicalController navbar = NavbarGraphicalController.getInstance();
        navbar.changePage(Pages.PROFILE);
    }
}
