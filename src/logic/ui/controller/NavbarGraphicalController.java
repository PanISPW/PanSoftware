package logic.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.enumeration.UserRole;
import logic.ui.FxUtilities;
import logic.util.Session;

// @author Danilo D'Amico

public class NavbarGraphicalController {
	
	@FXML
	private BorderPane main;

	@FXML
	private Button profileButton;
	
	@FXML
	private Button eventsButton;
	
	@FXML
	private Button newGoalButton;
	
	@FXML
	private Button adviceButton;
	
	@FXML
	private Button eventParticipationButton;
	
	public void setUserNavbar() {
		if(Session.getSession().getRole() == UserRole.USER) {
				adviceButton.setVisible(false);
				eventParticipationButton.setVisible(false);
		}
	}
	
	public void disableButtons() {
		switch(Session.getSession().getPage()) {
		case EVENTS:
			eventsButton.setDisable(true);
			newGoalButton.setDisable(false);
			profileButton.setDisable(false);
			adviceButton.setDisable(false);
			eventParticipationButton.setDisable(false);
			break;
		case NEWGOAL:
			eventsButton.setDisable(false);
			newGoalButton.setDisable(true);
			profileButton.setDisable(false);
			adviceButton.setDisable(false);
			eventParticipationButton.setDisable(false);
			break;
		case ADVICEPAGE:
			eventsButton.setDisable(false);
			newGoalButton.setDisable(false);
			profileButton.setDisable(false);
			adviceButton.setDisable(true);
			eventParticipationButton.setDisable(false);
			break;
		case EVENTPARTICIPATION:
			eventsButton.setDisable(false);
			newGoalButton.setDisable(false);
			profileButton.setDisable(false);
			adviceButton.setDisable(false);
			eventParticipationButton.setDisable(true);
			break;
		default:
			eventsButton.setDisable(false);
			newGoalButton.setDisable(false);
			profileButton.setDisable(true);
			adviceButton.setDisable(false);
			eventParticipationButton.setDisable(false);
			break;
		}
	}
	
	@FXML
	public void goToEvents() throws Exception {		
		changePage(Pages.EVENTS);
	}
	
	@FXML
	public void goToNewGoal() throws Exception {
		changePage(Pages.NEWGOAL);
	}
	
	@FXML
	public void goToProfile() throws Exception {
		changePage(Pages.PROFILE);
	}
	
	@FXML
	public void goToGiveAdvice() throws Exception {
		changePage(Pages.ADVICEPAGE);
	}
	
	@FXML
	public void goToManageEventParticipation() throws Exception {
		changePage(Pages.EVENTPARTICIPATION);
	}
	
	private void changePage(Pages page) throws Exception {
		Stage stage = (Stage) main.getScene().getWindow();
		stage.setScene(FxUtilities.goToPage(page, null));
	}

}
