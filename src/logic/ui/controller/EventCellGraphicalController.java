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

//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        
//        updateSelected(false);
//        
//        // set ListCell graphic
//        setGraphic(eventItemVBox);
//    }
//
//    public static EventCell newInstance() {
//        FXMLLoader loader = new FXMLLoader(EventCell.class.getResource("EventItem.fxml"));
//        try {
//            loader.load();
//            return loader.getController();
//        } catch (IOException ex) {
//            LOG.log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
//
//    @Override
//    protected void updateItem(Event item, boolean empty) {
//        super.updateItem(item, empty); // <-- Important
//        // make empty cell items invisible
//        eventItemVBox.getChildrenUnmodifiable().forEach(c -> c.setVisible(!empty));
//        // update valid cells with model data
//        if (!empty && item != null && !item.equals(this.model)) {
//        	eventItemTitle.setText(item.getName());
//        	eventItemId.setText(String.valueOf(item.getId()));
//        	eventItemOrganizer.setText(item.getUser().getUsername());
//        	eventItemDuration.setText(item.getStartingDate().toString() + " - " + item.getEndingDate().toString());
//        	eventItemType.setText(item.getType().toString());
//        }
//        // keep a reference to the model item in the ListCell
//        this.model = item;
//    }
//	
//	
//	
//	
//	// hic sunt leones
//	
//    @Override
//    public void updateItem(Event item, boolean empty) {
//    	
//        super.updateItem(item, empty);
//        
//        if (item != null && !empty) {
//        
//        	eventItemTitle.setText(item.getName());
//        	eventItemId.setText(String.valueOf(item.getId()));
//        	eventItemOrganizer.setText(item.getUser().getUsername());
//        	eventItemDuration.setText(item.getStartingDate().toString() + " - " + item.getEndingDate().toString());
//        	eventItemType.setText(item.getType().toString());
//                            
//            setGraphic(eventItemVBox);
//        } else {
//            setGraphic(null);
//        }
//    }

}
