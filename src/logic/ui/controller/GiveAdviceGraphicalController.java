package logic.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import logic.ManageGoalController;
import logic.entity.AdviceGoal;
import logic.enumeration.Pages;
import logic.exception.UserNotFoundException;
import logic.ui.FxUtilities;

// @author Danilo D'Amico

public class GiveAdviceGraphicalController implements Initializable{

	@FXML
	private RadioButton makeupButton;
	
	@FXML
	private RadioButton foodButton;
	
	@FXML
	private RadioButton lifestyleButton;
	
	@FXML
	private RadioButton otherButton;
	
	@FXML
	private ToggleGroup radioGroup;
	
	@FXML
	private VBox adviceBox;
	
	public void populateBox(List<AdviceGoal> goals) throws IOException {
		
		
		
        System.out.println("goal loop");//
		for (AdviceGoal item : goals) {
			System.out.println("loop: " + item.getName());//
			
			AdviceCellGraphicalController cell = new AdviceCellGraphicalController(item);
			
			FXMLLoader loader = FxUtilities.loadFxml(Pages.ADVICEITEM);
			loader.setController(cell);
			
			
			VBox vbox = loader.load();
			
			adviceBox.getChildren().add(vbox);
			}
		
	}
	
	@FXML
	public void makeupAdviceRadio() throws UserNotFoundException, Exception {
		adviceBox.getChildren().clear();
		List<AdviceGoal> goals = ManageGoalController.getUnansweredMakeupAdvice();
		populateBox(goals);
	}
	
	@FXML
	public void foodAdviceRadio() throws UserNotFoundException, Exception {
		adviceBox.getChildren().clear();
		List<AdviceGoal> goals = ManageGoalController.getUnansweredFoodAdvice();
		populateBox(goals);
	}
	
	@FXML
	public void lifestyleAdviceRadio() throws UserNotFoundException, Exception {
		adviceBox.getChildren().clear();
		List<AdviceGoal> goals = ManageGoalController.getUnansweredLifestyleAdvice();
		populateBox(goals);
	}
	
	@FXML
	public void otherAdviceRadio() throws UserNotFoundException, Exception {
		adviceBox.getChildren().clear();
		List<AdviceGoal> goals = ManageGoalController.getUnansweredOtherAdvice();
		populateBox(goals);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		radioGroup = new ToggleGroup();
		makeupButton.setToggleGroup(radioGroup);
		foodButton.setToggleGroup(radioGroup);
		lifestyleButton.setToggleGroup(radioGroup);
		otherButton.setToggleGroup(radioGroup);
		
		makeupButton.setSelected(true);
		
		try {
			makeupAdviceRadio();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
