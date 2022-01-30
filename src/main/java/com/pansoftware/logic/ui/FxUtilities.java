package com.pansoftware.logic.ui;

import com.pansoftware.logic.MainDesktop;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.enumeration.UserRole;
import com.pansoftware.logic.ui.controller.NavbarGraphicalController;
import com.pansoftware.logic.util.Session;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

// @author Danilo D'Amico

public class FxUtilities {

    private FxUtilities(){}

    static BorderPane navbar = null;
    static NavbarGraphicalController navbarController = null;

    public static Scene goToPage(Pages page, Initializable controller) throws IOException {

        Session.getSession().setPage(page);

        if (page.equals(Pages.LOGIN)) {
            Session.getSession().setCurrUser(null);
            Session.getSession().setRole(UserRole.USER);

            return new Scene(loadFxml(page).load());
        } else {
            FXMLLoader loader = loadFxml(page);

            if (controller != null) {
                loader.setController(controller);
            }

            BorderPane pane = loader.load();
            pane.setTop(getNavbar());
            navbarController.disableButtons();

            navbarController.setUserNavbar();

            return new Scene(pane);


        }
    }

    public static FXMLLoader loadFxml(Pages page) {
        return switch (page) {
            case LOGIN -> new FXMLLoader(MainDesktop.class.getResource("Login.fxml"));
            case EVENTS -> new FXMLLoader(MainDesktop.class.getResource("Events.fxml"));
            case NEWGOAL -> new FXMLLoader(MainDesktop.class.getResource("NewGoal.fxml"));
            case EVENTITEM -> new FXMLLoader(MainDesktop.class.getResource("EventItem.fxml"));
            case GOALITEM -> new FXMLLoader(MainDesktop.class.getResource("GoalCell.fxml"));
            case ADVICEPAGE -> new FXMLLoader(MainDesktop.class.getResource("GiveAdvice.fxml"));
            case EVENTPARTICIPATION -> new FXMLLoader(MainDesktop.class.getResource("ManageEventParticipation.fxml"));
            case ADVICEITEM -> new FXMLLoader(MainDesktop.class.getResource("AdviceCell.fxml"));
            case EVENTPARTICIPATIONITEM -> new FXMLLoader(MainDesktop.class.getResource("EventParticipationItem.fxml"));
            case EVENTSELECTIONITEM -> new FXMLLoader(MainDesktop.class.getResource("EventSelectionItem.fxml"));
            case EVENTSELECTION -> new FXMLLoader(MainDesktop.class.getResource("EventSelection.fxml"));
            default -> new FXMLLoader(MainDesktop.class.getResource("Profile.fxml"));
        };
    }

    private static BorderPane getNavbar() throws IOException {
        if (navbar == null) {
            FXMLLoader loader = new FXMLLoader(MainDesktop.class.getResource("Navbar.fxml"));

            navbarController = NavbarGraphicalController.getInstance();

            loader.setController(navbarController);
            navbar = loader.load();
        }

        return navbar;
    }

    public static void hideVBox(VBox box) {
        box.setVisible(false);
        box.managedProperty().bind(box.visibleProperty());
    }

    public static void showVBox(VBox box) {
        box.setVisible(true);
        box.managedProperty().bind(box.visibleProperty());
    }

    public static void hideScrollPane(ScrollPane pane) {
        pane.setVisible(false);
        pane.managedProperty().bind(pane.visibleProperty());
    }

    public static void showScrollPane(ScrollPane pane) {
        pane.setVisible(true);
        pane.managedProperty().bind(pane.visibleProperty());
    }

}
