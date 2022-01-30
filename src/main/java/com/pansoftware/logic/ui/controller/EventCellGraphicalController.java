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

    public EventCellGraphicalController(final EventBean event) {
        item = event;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        this.eventItemTitle.setText(this.item.getName());
        this.eventItemId.setText(String.valueOf(this.item.getId()));
        this.eventItemOrganizer.setText(this.item.getOrganizer());
        this.eventItemDuration.setText(this.item.getStartingDate().toString() + " - " + this.item.getEndingDate().toString());
        this.eventItemType.setText(this.item.getType().toString());
    }

}
