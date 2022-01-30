package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.EventBean;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.ui.FxUtilities;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class EventsGraphicalController implements Initializable {

    ObservableList<EventBean> observableList = FXCollections.observableArrayList();
    @FXML
    private VBox eventsList;

    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {

        try {

            final List<EventBean> events = ManageGoalController.getEventBeanList();

            for (final EventBean item : events) {
                final EventCellGraphicalController cell = new EventCellGraphicalController(item);

                final FXMLLoader loader = FxUtilities.loadFxml(Pages.EVENTITEM);
                loader.setController(cell);

                final VBox vbox = loader.load();

                this.eventsList.getChildren().add(vbox);
            }
        } catch (final Exception e) {
            Platform.exit();
        }

    }

}
