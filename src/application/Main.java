package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import db.DB;
import gui.util.Alerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import properties.PropertiesFile;

public class Main extends Application {

	private static Scene mainScene;

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
				portSocket = Integer.parseInt(PropertiesFile.loadPropertiesSocket().getProperty("socketPort"));
				setServerSocket(new ServerSocket(portSocket));
				setSocket(new Socket(InetAddress.getLocalHost().getHostAddress(), portSocket));

				try {

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginView.fxml"));
					Pane pane = loader.load();

					mainScene = new Scene(pane);

					// Define o Style
					setUserAgentStylesheet(STYLESHEET_CASPIAN);
					// setUserAgentStylesheet(STYLESHEET_MODENA);

					primaryStage.setScene(mainScene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("Controle de Estoque");

					Image applicationIcon = new Image(getClass().getResourceAsStream("/imagens/bozo.jpg"));
					primaryStage.getIcons().add(applicationIcon);

					primaryStage.show();

				} catch (Exception e) {

					Alerts.showAlert("Controle de Estoque", "Erro ao abrir a tela", e.getLocalizedMessage(),
							AlertType.ERROR);

				}

			} catch (IOException e) {

				Alerts.showAlert("Controle de Estoque", "Erro ao abrir o programa",
						"Já existe uma instância do programa aberta!", AlertType.ERROR);

			}

		} else {
			
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ConfigurarPerpetiesDB.fxml"));
				Pane pane = loader.load();

				mainScene = new Scene(pane);

				// Define o Style
				setUserAgentStylesheet(STYLESHEET_CASPIAN);
				// setUserAgentStylesheet(STYLESHEET_MODENA);

				primaryStage.setScene(mainScene);
				primaryStage.setResizable(false);
				primaryStage.setTitle("Configuarar banco de dados");

				Image applicationIcon = new Image(getClass().getResourceAsStream("/imagens/bozo.jpg"));
				primaryStage.getIcons().add(applicationIcon);

				primaryStage.show();

			} catch (Exception e) {

				Alerts.showAlert("Controle de Estoque", "Erro ao abrir a tela", e.getLocalizedMessage(),
						AlertType.ERROR);

			}

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
