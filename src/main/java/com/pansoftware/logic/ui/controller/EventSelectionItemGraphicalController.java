package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.AdviceGoalBean;
import com.pansoftware.logic.bean.EventBean;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.exception.InvalidDataException;
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
    private Label eventItemTitle;
    @FXML
    private Label eventItemDuration;
    @FXML
    private Label eventItemId;
    @FXML
    private Label eventItemOrganizer;
    @FXML
    private Label eventItemType;

    public EventSelectionItemGraphicalController(EventBean event) {
        this.item = event;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eventItemTitle.setText(item.getName());
        eventItemId.setText(String.valueOf(item.getId()));
        eventItemOrganizer.setText(item.getOrganizer());
        eventItemDuration.setText(item.getStartingDate().toString() + " - " + item.getEndingDate().toString());
        eventItemType.setText(item.getType().toString());
    }

    @FXML
    public void submit() throws Exception {

        EventGoalBeanUtil util = EventGoalBeanUtil.getEventGoalBeanUtil();

        EventGoalBean bean = new EventGoalBean();


        bean.setName(util.getName());
        System.out.println("Name: " + bean.getName());
        bean.setDescription(util.getDescription());

        bean.setNumberOfSteps(util.getNumberOfSteps());
        bean.setStepsCompleted(0);

        bean.setNewDeadline(util.getDeadline());
        bean.setReminder(util.isReminder());


        bean.setEventId(item.getId());
        bean.setEventOrganizer(item.getOrganizer());

        ManageGoalController.createGoal(bean);
        util.invalidate();

        NavbarGraphicalController navbar = NavbarGraphicalController.getInstance();
        navbar.changePage(Pages.PROFILE);
    }
}
