package logic.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import logic.ManageGoalController;
import logic.bean.UpdateStepsBean;
import logic.entity.EventGoal;
import logic.enumeration.GoalType;
import logic.ui.FxUtilities;
import logic.util.Session;

// @author Danilo D'Amico

public class EventGoalGraphicalController implements Initializable{
	
	@FXML
	private VBox adviceGoalBox;
	
	@FXML
	private Label goalItemTitle;
	
	@FXML
	private Label goalItemDeadline;
	
	@FXML
	private Label goalItemDescription;
	
	@FXML
	private Label goalItemId;
	
	@FXML
	private Label goalItemCompletedSteps;
	
	@FXML
	private Label goalItemTotalSteps;
	
	@FXML
	private TextField goalItemStepsTextField;
	
	@FXML
	private Button goalItemStepsButton;
	
	@FXML
	private Label eventGoalEvent;
	
	@FXML
	private Label eventGoalEventId;
	
	@FXML
	private Label eventGoalParticipationStatus;
	
	EventGoal item;
	
	// dovrei passarmi il bean non l'entity
	public EventGoalGraphicalController(EventGoal goal) {
		this.item = goal;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FxUtilities.hideVBox(adviceGoalBox);
		
		goalItemTitle.setText(item.getName());
		goalItemDeadline.setText("Deadline: " + item.getDeadline().toString());
		goalItemDescription.setText(item.getDescription());
		goalItemId.setText(String.valueOf(item.getId()));
		goalItemCompletedSteps.setText(String.valueOf(item.getStepsCompleted()));
		goalItemTotalSteps.setText(String.valueOf(item.getNumberOfSteps()));
		
		try {
			eventGoalEvent.setText(item.getEvent().getName());
			eventGoalEventId.setText(item.getOrganizer().getUsername());
			eventGoalParticipationStatus.setText(item.getState().toString());
		} catch(NullPointerException e) {
			eventGoalEvent.setText("Event not specified");
			eventGoalEventId.setText("");
			eventGoalParticipationStatus.setText("");
		}
	}
	
	@FXML
	public void submit() throws Exception {
		UpdateStepsBean bean = new UpdateStepsBean();
		
		bean.setId(item.getId());
		bean.setStepsCompleted(Integer.parseInt(goalItemStepsTextField.getText()));
		bean.setType(GoalType.EVENTGOAL);
		bean.setUser(Session.getSession().getUser());

		
		ManageGoalController.updateSteps(bean);
	}

}
