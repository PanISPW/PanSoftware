package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.bean.EventBean;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class EventCellGraphicalController implements Initializable {

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

    public EventCellGraphicalController(EventBean event) {
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

}
