package logic.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.LoginController;
import logic.bean.LoginBean;
import logic.enumeration.Pages;
import logic.enumeration.UserRole;
import logic.exception.DatabaseException;
import logic.exception.UserNotFoundException;
import logic.ui.FxUtilities;
import logic.util.Session;

import javax.security.auth.login.LoginException;
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
    public void login() throws SQLException, LoginException, IOException {

        try {

            String username = usernameField.getText();
            String password = passwordField.getText();

            LoginController controller = new LoginController();
            LoginBean bean = new LoginBean(username, password);


            role = controller.loginUser(bean);
            if (role != null) {
                Session.getSession().setCurrUser(username);
                Session.getSession().setRole(role);

                Scene newScene = FxUtilities.goToPage(Pages.PROFILE, null);

                Stage stage = (Stage) outerVBox.getScene().getWindow();
                stage.setScene(newScene);
            }


        } catch (UserNotFoundException e) {
            resultLabel.setText(e.getMessage().toUpperCase());
        }
    }

}
