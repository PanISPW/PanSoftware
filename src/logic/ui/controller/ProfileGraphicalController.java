package logic.ui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import logic.ManageGoalController;
import logic.entity.AdviceGoal;
import logic.entity.EventGoal;
import logic.entity.Goal;
import logic.entity.User;
import logic.enumeration.Pages;
import logic.exception.UserNotFoundException;
import logic.ui.FxUtilities;

// @author Danilo D'Amico

public class ProfileGraphicalController implements Initializable{
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private VBox goalList;
	
	@FXML
	private VBox adviceGoalList;
	
	@FXML
	private VBox eventGoalList;
	
	@FXML
	private ScrollPane goalPane;
	
	@FXML
	private ScrollPane adviceGoalPane;
	
	@FXML
	private ScrollPane eventGoalPane;
	
	@FXML
	private RadioButton goalListButton;
	
	@FXML
	private RadioButton adviceListButton;
	
	@FXML
	private RadioButton eventListButton;
	
	@FXML
	private ToggleGroup radioGroup;
	
	User user = null;
	
	@FXML
	public void goalRadio() throws UserNotFoundException, Exception {
		FxUtilities.showScrollPane(goalPane);
		FxUtilities.hideScrollPane(adviceGoalPane);
		FxUtilities.hideScrollPane(eventGoalPane);
		
		// lista
		List<Goal> goals = ManageGoalController.getGoalList();
		goalList.getChildren().clear();
		
        System.out.println("goal loop");//
		for (Goal item : goals) {
			System.out.println("loop: " + item.getName());//
			
			GoalCellGraphicalController cell = new GoalCellGraphicalController(item);
			
			FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
			loader.setController(cell);
			
			
			VBox vbox = loader.load();
			
			goalList.getChildren().add(vbox);
			}
	}
	
	@FXML
	public void adviceGoalRadio() throws UserNotFoundException, Exception {
		FxUtilities.showScrollPane(adviceGoalPane);
		FxUtilities.hideScrollPane(goalPane);
		FxUtilities.hideScrollPane(eventGoalPane);
		
		// lista
		List<AdviceGoal> goals = ManageGoalController.getAdviceGoalList();
		adviceGoalList.getChildren().clear();
		
        System.out.println("advice goal loop");//
		for (AdviceGoal item : goals) {
			System.out.println("loop: " + item.getName());//
			
			AdviceGoalGraphicalController cell = new AdviceGoalGraphicalController(item);
			
			FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
			loader.setController(cell);
			
			VBox vbox = loader.load();
			
			adviceGoalList.getChildren().add(vbox);
			}
	}
	
	@FXML
	public void eventGoalRadio() throws UserNotFoundException, Exception {
		FxUtilities.showScrollPane(eventGoalPane);
		FxUtilities.hideScrollPane(goalPane);
		FxUtilities.hideScrollPane(adviceGoalPane);
		
		//lista
		List<EventGoal> goals = ManageGoalController.getEventGoalList();
		eventGoalList.getChildren().clear();
		
        System.out.println("advice goal loop");//
		for (EventGoal item : goals) {
			System.out.println("loop: " + item.getName());//
			
			EventGoalGraphicalController cell = new EventGoalGraphicalController(item);
			
			FXMLLoader loader = FxUtilities.loadFxml(Pages.GOALITEM);
			loader.setController(cell);
			
			VBox vbox = loader.load();
			
			eventGoalList.getChildren().add(vbox);
			}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		radioGroup = new ToggleGroup();
		goalListButton.setToggleGroup(radioGroup);
		adviceListButton.setToggleGroup(radioGroup);
		eventListButton.setToggleGroup(radioGroup);
		goalListButton.setSelected(true);
		
		try {
			user = ManageGoalController.getCurrentUser();
		} catch (UserNotFoundException e) {
			System.out.println("could not find user");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("could not retrieve user");
		}
		
		nameLabel.setText(user.getName() + " " + user.getSurname());
		usernameLabel.setText("@"+user.getUsername());
		
		try {
			goalRadio();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@FXML
//	public void getGoalList() throws Exception {
//		List<Goal> goalList = ManageGoalController.getGoalList();
//		// bind su goalList
//	}
//	
//	@FXML
//	public void getAdviceList() throws Exception {
//		List<AdviceGoal> adviceList = ManageGoalController.getAdviceGoalList();
//		// bind su adviceGoalList
//	}
//	
//	@FXML
//	public void getEventGoalList() throws Exception {
//		List<EventGoal> eventList = ManageGoalController.getEventGoalList();
//		// bind su eventGoalList
//	}

}
