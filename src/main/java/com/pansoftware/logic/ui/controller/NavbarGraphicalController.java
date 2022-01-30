package com.pansoftware.logic.ui.controller;

import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.pansoftware.logic.ui.FxUtilities;

import java.io.IOException;

// @author Danilo D'Amico

public class NavbarGraphicalController {

    private static NavbarGraphicalController instance;

    private NavbarGraphicalController() {}

    public static NavbarGraphicalController getInstance() {
        if (NavbarGraphicalController.instance == null)
            NavbarGraphicalController.instance = new NavbarGraphicalController();

        return NavbarGraphicalController.instance;
    }

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
        if (Session.getSession().getRole() == UserRole.ACTIVIST) {
            this.eventParticipationButton.setVisible(false);
        } else if(Session.getSession().getRole() == UserRole.BRANDMANAGER){
            this.adviceButton.setVisible(false);
        } else{
            this.adviceButton.setVisible(false);
            this.eventParticipationButton.setVisible(false);
        }
    }

    public void disableButtons() {
        switch (Session.getSession().getPage()) {
            case EVENTS -> {
                this.eventsButton.setDisable(true);
                this.newGoalButton.setDisable(false);
                this.profileButton.setDisable(false);
                this.adviceButton.setDisable(false);
                this.eventParticipationButton.setDisable(false);
            }
            case NEWGOAL -> {
                this.eventsButton.setDisable(false);
                this.newGoalButton.setDisable(true);
                this.profileButton.setDisable(false);
                this.adviceButton.setDisable(false);
                this.eventParticipationButton.setDisable(false);
            }
            case ADVICEPAGE -> {
                this.eventsButton.setDisable(false);
                this.newGoalButton.setDisable(false);
                this.profileButton.setDisable(false);
                this.adviceButton.setDisable(true);
                this.eventParticipationButton.setDisable(false);
            }
            case EVENTPARTICIPATION -> {
                this.eventsButton.setDisable(false);
                this.newGoalButton.setDisable(false);
                this.profileButton.setDisable(false);
                this.adviceButton.setDisable(false);
                this.eventParticipationButton.setDisable(true);
            }
            default -> {
                this.eventsButton.setDisable(false);
                this.newGoalButton.setDisable(false);
                this.profileButton.setDisable(true);
                this.adviceButton.setDisable(false);
                this.eventParticipationButton.setDisable(false);
            }
        }
    }

    @FXML
    public void goToEvents() throws IOException {
        this.changePage(Pages.EVENTS);
    }

    @FXML
    public void goToNewGoal() throws IOException {
        this.changePage(Pages.NEWGOAL);
    }

    @FXML
    public void goToProfile() throws IOException {
        this.changePage(Pages.PROFILE);
    }

    @FXML
    public void goToGiveAdvice() throws IOException {
        this.changePage(Pages.ADVICEPAGE);
    }

    @FXML
    public void goToManageEventParticipation() throws IOException {
        this.changePage(Pages.EVENTPARTICIPATION);
    }

    public void changePage(final Pages page) throws IOException {
        final Stage stage = (Stage) this.main.getScene().getWindow();
        stage.setScene(FxUtilities.goToPage(page, null));
    }

}
