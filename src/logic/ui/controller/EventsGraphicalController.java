package logic.ui.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import logic.ManageGoalController;
import logic.entity.Event;
import logic.enumeration.Pages;
import logic.ui.FxUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class EventsGraphicalController implements Initializable {

    ObservableList<Event> observableList = FXCollections.observableArrayList();
    @FXML
    private VBox eventsList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {

            List<Event> events = ManageGoalController.getEventList();

            System.out.println("fuori dal loop");
            for (Event item : events) {
                System.out.println("loop: " + item.getName());
                EventCellGraphicalController cell = new EventCellGraphicalController(item);

                FXMLLoader loader = FxUtilities.loadFxml(Pages.EVENTITEM);
                loader.setController(cell);


                VBox vbox = loader.load();

                eventsList.getChildren().add(vbox);
            }
        } catch (Exception e) {
            System.out.println("errore");
            Platform.exit();
        }

    }

}
