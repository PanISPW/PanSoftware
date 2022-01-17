package logic.ui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import logic.MainDesktop;
import logic.enumeration.Pages;
import logic.enumeration.UserRole;
import logic.ui.controller.NavbarGraphicalController;
import logic.util.Session;

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
        switch (page) {
            case LOGIN:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/Login.fxml"));

            case EVENTS:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/Events.fxml"));

            case NEWGOAL:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/NewGoal.fxml"));

            case EVENTITEM:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/EventItem.fxml"));

            case GOALITEM:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/GoalCell.fxml"));

            case ADVICEPAGE:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/GiveAdvice.fxml"));

            case EVENTPARTICIPATION:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/ManageEventParticipation.fxml"));

            case ADVICEITEM:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/AdviceCell.fxml"));

            case EVENTPARTICIPATIONITEM:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/EventParticipationItem.fxml"));

            default:
                return new FXMLLoader(MainDesktop.class.getResource("fxml/Profile.fxml"));
        }
    }

    private static BorderPane getNavbar() throws IOException {
        if (navbar == null) {
            FXMLLoader loader = new FXMLLoader(MainDesktop.class.getResource("fxml/Navbar.fxml"));

            navbarController = new NavbarGraphicalController();

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
