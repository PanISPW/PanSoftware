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

    private static NavbarGraphicalController instance = null;

    private NavbarGraphicalController() {}

    public static NavbarGraphicalController getInstance() {
        if (instance == null)
            instance = new NavbarGraphicalController();

        return instance;
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
            eventParticipationButton.setVisible(false);
        } else if(Session.getSession().getRole() == UserRole.BRANDMANAGER){
            adviceButton.setVisible(false);
        } else{
            adviceButton.setVisible(false);
            eventParticipationButton.setVisible(false);
        }
    }

    public void disableButtons() {
        switch (Session.getSession().getPage()) {
            case EVENTS -> {
                eventsButton.setDisable(true);
                newGoalButton.setDisable(false);
                profileButton.setDisable(false);
                adviceButton.setDisable(false);
                eventParticipationButton.setDisable(false);
            }
            case NEWGOAL -> {
                eventsButton.setDisable(false);
                newGoalButton.setDisable(true);
                profileButton.setDisable(false);
                adviceButton.setDisable(false);
                eventParticipationButton.setDisable(false);
            }
            case ADVICEPAGE -> {
                eventsButton.setDisable(false);
                newGoalButton.setDisable(false);
                profileButton.setDisable(false);
                adviceButton.setDisable(true);
                eventParticipationButton.setDisable(false);
            }
            case EVENTPARTICIPATION -> {
                eventsButton.setDisable(false);
                newGoalButton.setDisable(false);
                profileButton.setDisable(false);
                adviceButton.setDisable(false);
                eventParticipationButton.setDisable(true);
            }
            default -> {
                eventsButton.setDisable(false);
                newGoalButton.setDisable(false);
                profileButton.setDisable(true);
                adviceButton.setDisable(false);
                eventParticipationButton.setDisable(false);
            }
        }
    }

    @FXML
    public void goToEvents() throws IOException {
        changePage(Pages.EVENTS);
    }

    @FXML
    public void goToNewGoal() throws IOException {
        changePage(Pages.NEWGOAL);
    }

    @FXML
    public void goToProfile() throws IOException {
        changePage(Pages.PROFILE);
    }

    @FXML
    public void goToGiveAdvice() throws IOException {
        changePage(Pages.ADVICEPAGE);
    }

    @FXML
    public void goToManageEventParticipation() throws IOException {
        changePage(Pages.EVENTPARTICIPATION);
    }

    public void changePage(Pages page) throws IOException {
        Stage stage = (Stage) main.getScene().getWindow();
        stage.setScene(FxUtilities.goToPage(page, null));
    }

}
