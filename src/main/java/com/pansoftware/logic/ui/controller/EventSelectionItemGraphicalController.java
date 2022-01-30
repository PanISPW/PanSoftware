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

    public EventSelectionItemGraphicalController(final EventBean event) {
        item = event;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        this.eventTitle.setText(this.item.getName());
        this.eventId.setText(String.valueOf(this.item.getId()));
        this.eventOrganizer.setText(this.item.getOrganizer());
        this.eventDuration.setText(this.item.getStartingDate().toString() + " - " + this.item.getEndingDate().toString());
        this.eventType.setText(this.item.getType().toString());
    }

    @FXML
    public void submit() throws Exception {

        final EventGoalBeanUtil util = EventGoalBeanUtil.getEventGoalBeanUtil();

        final EventGoalBean bean = new EventGoalBean();


        bean.setName(util.getName());
        bean.setDescription(util.getDescription());

        bean.setNumberOfSteps(util.getNumberOfSteps());
        bean.setStepsCompleted(0);

        bean.setNewDeadline(util.getDeadline());
        bean.setReminder(util.isReminder());


        bean.setEventId(this.item.getId());
        bean.setEventOrganizer(this.item.getOrganizer());

        ManageGoalController.createGoal(bean);
        EventGoalBeanUtil.invalidate();

        final NavbarGraphicalController navbar = NavbarGraphicalController.getInstance();
        navbar.changePage(Pages.PROFILE);
    }
}
