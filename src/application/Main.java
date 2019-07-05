package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	private static Scene mainScene;

	private static Scene dialogScene;

	// public static String style = "/application/app2.css" ;
	// public static String style = "" ;

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginView.fxml"));
			Pane pane = loader.load();

			mainScene = new Scene(pane);

			// mainScene.getStylesheets().add(style);

			setUserAgentStylesheet(STYLESHEET_CASPIAN);
			// setUserAgentStylesheet(STYLESHEET_MODENA);
			// setUserAgentStylesheet(style);

			primaryStage.setScene(mainScene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Estoque Crachás");
			primaryStage.show();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		Main.mainScene = mainScene;
	}

	public static Scene getDialogScene() {
		return dialogScene;
	}

	public static void setDialogScene(Scene dialogScene) {
		Main.dialogScene = dialogScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
