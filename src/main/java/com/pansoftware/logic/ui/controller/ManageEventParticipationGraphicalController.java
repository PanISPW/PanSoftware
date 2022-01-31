package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.ManageGoalController;
import com.pansoftware.logic.bean.EventGoalBean;
import com.pansoftware.logic.enumeration.Pages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import com.pansoftware.logic.ui.FxUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class ManageEventParticipationGraphicalController implements Initializable {

    @FXML
    private VBox eventsParticipationList;

    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {
        eventsParticipationList.getChildren().clear();

        try {
            final List<EventGoalBean> goals = ManageGoalController.getPendingEventGoalBeanList();

            for (final EventGoalBean item : goals) {
                final EventParticipationItemGraphicalController cell = new EventParticipationItemGraphicalController(item);

                final FXMLLoader loader = FxUtilities.loadFxml(Pages.EVENTPARTICIPATIONITEM);
                loader.setController(cell);


                final VBox vbox = loader.load();

                eventsParticipationList.getChildren().add(vbox);
            }
        } catch (final Exception e) {

            e.printStackTrace();
        }
    }
}
