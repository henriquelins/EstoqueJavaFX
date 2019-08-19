package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import db.DB;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import properties.PropertiesFile;

public class Main extends Application {

	// Tela cheia
	private static Scene mainScene;

	// Tela diálogo
	private static Scene dialogScene;

	private static Socket socket;

	private static ServerSocket serverSocket;

	private static int portSocket = 10050;

	@Override
	public void start(Stage primaryStage) throws SQLException {

		Connection conn = null;
		conn = DB.getConnection();

		if (conn != null) {

			try {

				// impede que seja criada uma nova instância do programa
				portSocket = Integer
						.parseInt(PropertiesFile.loadPropertiesSocket().getProperty(Strings.getPropertiessocketPort()));
				setServerSocket(new ServerSocket(portSocket));
				setSocket(new Socket(InetAddress.getLocalHost().getHostAddress(), portSocket));

				try {

					splash(primaryStage);

				} catch (Exception e) {

					Alerts.showAlert("Controle de Estoque", "Erro ao abrir a tela", e.getLocalizedMessage(),
							AlertType.ERROR);

				}

			} catch (IOException e) {

				Alerts.showAlert("Controle de Estoque", "Erro ao abrir o programa",
						"Já existe uma instância do programa aberta!", AlertType.ERROR);

			}

		} else {

			primaryStage.close();
			carregarTela(primaryStage, Strings.getLoginView());

		}

	}

	private void splash(Stage primaryStage) throws IOException {

		try {

			StackPane pane = FXMLLoader.load(getClass().getResource((Strings.getSplashView())));

			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.TRANSPARENT);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.show();

			// Carrega a tela splash com fade in effect
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);

			// Termina a tela splash com fade out effect
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			fadeIn.play();

			// Depois de fade in, inicia o fade out
			fadeIn.setOnFinished((e) -> {

				fadeOut.play();

			});

			// Depois do fade out, carrega a tela inicial - login
			fadeOut.setOnFinished((e) -> {

				primaryStage.close();
				carregarTela(primaryStage, Strings.getLoginView());

			});

		} catch (IOException ex) {

			Alerts.showAlert("Controle de Estoque", "Erro ao abrir o splash", ex.getLocalizedMessage(),
					AlertType.ERROR);

		}

	}

	private void carregarTela(Stage primaryStage, String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			AnchorPane pane = loader.load();

			mainScene = new Scene(pane);

			// Define o Style
			setUserAgentStylesheet(STYLESHEET_CASPIAN);
			// setUserAgentStylesheet(STYLESHEET_MODENA);

			Stage principalStage = new Stage();
			principalStage.setTitle(Strings.getTitle());

			principalStage.setScene(mainScene);
			principalStage.setResizable(false);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			principalStage.getIcons().add(applicationIcon);

			principalStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

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

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {

		Main.socket = socket;

	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {

		Main.serverSocket = serverSocket;

	}

	public static void main(String[] args) {

		launch(args);

	}

}
