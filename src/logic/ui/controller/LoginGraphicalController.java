package logic.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import logic.bean.LoginBean;
import logic.enumeration.Pages;
import logic.enumeration.UserRole;
import logic.exception.DatabaseException;
import logic.exception.UserNotFoundException;
import logic.ui.FxUtilities;
import logic.util.Session;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.LoginController;
import javafx.fxml.FXML;

// @author Danilo D'Amico

public class LoginGraphicalController implements Initializable{
	
	UserRole role = null;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Label resultLabel;
	
	@FXML
	private VBox outerVBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML
	public void login() throws Exception {
		
		try {
			
			String username = usernameField.getText();
			String password = passwordField.getText();
			
			LoginController controller = new LoginController();
			LoginBean bean = new LoginBean(username, password);

			
			role = controller.loginUser(bean);
			if(role != null) {
				Session.getSession().setCurrUser(username);
				Session.getSession().setRole(role);
				
				Scene newScene = FxUtilities.goToPage(Pages.PROFILE, null);
				
				Stage stage = (Stage) outerVBox.getScene().getWindow();
				stage.setScene(newScene);
			}

			
		} catch(UserNotFoundException | DatabaseException e) {
			resultLabel.setText(e.getMessage().toUpperCase());
		}
	}

}
