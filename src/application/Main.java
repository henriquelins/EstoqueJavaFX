package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	private static Scene mainScene;

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginView.fxml"));
			Pane pane = loader.load();

			mainScene = new Scene(pane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Estoque Crach�s");
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

	public static void main(String[] args) {
		launch(args);
	}
}
