package logic.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.entity.Event;

import java.net.URL;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class EventCellGraphicalController implements Initializable {

    Event item;
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

    // dovrei passarmi il bean non l'entity
    public EventCellGraphicalController(Event event) {
        this.item = event;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        eventItemTitle.setText(item.getName());
        eventItemId.setText(String.valueOf(item.getId()));
        eventItemOrganizer.setText(item.getUser().getUsername());
        eventItemDuration.setText(item.getStartingDate().toString() + " - " + item.getEndingDate().toString());
        eventItemType.setText(item.getType().toString());
    }

}
