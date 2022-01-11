package logic;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.ui.FxUtilities;
import logic.util.Session;
import javafx.scene.Scene;

// @author Danilo D'Amico

public class MainDesktop extends Application {
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
