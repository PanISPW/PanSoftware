package logic.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import logic.ManageGoalController;
import logic.entity.EventGoal;
import logic.enumeration.Pages;
import logic.ui.FxUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class ManageEventParticipationGraphicalController implements Initializable {

    @FXML
    private VBox eventsParticipationList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        eventsParticipationList.getChildren().clear();

        System.out.println("Event Participation aperto");//

        try {
            List<EventGoal> goals = ManageGoalController.getPendingEventGoalList();

            for (EventGoal item : goals) {
                System.out.println("loop: " + item.getName());
                EventParticipationItemGraphicalController cell = new EventParticipationItemGraphicalController(item);

                FXMLLoader loader = FxUtilities.loadFxml(Pages.EVENTPARTICIPATIONITEM);
                loader.setController(cell);


                VBox vbox = loader.load();

                eventsParticipationList.getChildren().add(vbox);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
