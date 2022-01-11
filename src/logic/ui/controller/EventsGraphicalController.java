package logic.ui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.layout.VBox;
import logic.ManageGoalController;
import logic.entity.Event;
import logic.enumeration.Pages;

import logic.ui.FxUtilities;


import javafx.fxml.FXMLLoader;

// @author Danilo D'Amico

public class EventsGraphicalController implements Initializable {
	
	@FXML
	private VBox eventsList;
	
	ObservableList<Event> observableList = FXCollections.observableArrayList();

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
