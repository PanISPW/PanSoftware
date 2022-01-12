package logic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.ui.FxUtilities;
import logic.util.Session;

// @author Danilo D'Amico

public class MainDesktop extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = FxUtilities.goToPage(Pages.LOGIN, null);

        try {

            primaryStage.setTitle(Session.getSession().getPage().toString().toLowerCase());
            primaryStage.setWidth(600);
            primaryStage.setHeight(600);
            primaryStage.centerOnScreen();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
