package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.LoginController;
import com.pansoftware.logic.bean.LoginBean;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.exception.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.pansoftware.logic.ui.FxUtilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

// @author Danilo D'Amico

public class LoginGraphicalController implements Initializable {

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
        // empty
    }

    @FXML
    public void login() throws SQLException, IOException {

        try {

            String username = usernameField.getText();
            String password = passwordField.getText();

            LoginBean bean = new LoginBean(username, password);
            LoginController.loginUser(bean);

            Scene newScene = FxUtilities.goToPage(Pages.PROFILE, null);
            Stage stage = (Stage) outerVBox.getScene().getWindow();
            stage.setScene(newScene);

        } catch (UserNotFoundException e) {
            resultLabel.setText(e.getMessage());
        }
    }

}
