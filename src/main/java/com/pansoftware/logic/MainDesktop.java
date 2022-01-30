package com.pansoftware.logic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.pansoftware.logic.enumeration.Pages;
import com.pansoftware.logic.ui.FxUtilities;
import com.pansoftware.logic.util.Session;

import java.io.IOException;

// @author Danilo D'Amico

public class MainDesktop extends Application {
    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {

        final Scene scene = FxUtilities.goToPage(Pages.LOGIN, null);

        try {

            primaryStage.setTitle(Session.getSession().getPage().toString().toLowerCase());
            primaryStage.setWidth(600);
            primaryStage.setHeight(600);
            primaryStage.centerOnScreen();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
