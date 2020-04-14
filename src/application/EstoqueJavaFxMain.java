package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import db.DB;
import gui.forms.Forms;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import properties.PropertiesFile;

public class EstoqueJavaFxMain extends Application {

	// Tela cheia
	public static Scene mainScene;

	// Tela diálogo
	public static Scene dialogScene;

	private static Socket socket;

	private static ServerSocket serverSocket;

	private static int portSocket = 10050;
	
	//public static String style = "/application/darktheme.css";

	@Override
	public void start(Stage primaryStage) throws SQLException {

		Connection conn = null;
		conn = DB.getConnectionTeste();

		if (conn != null) {

			try {

				// impede que seja criada uma nova instância do programa
				portSocket = Integer
						.parseInt(PropertiesFile.loadPropertiesSocket().getProperty(Strings.getPropertiessocketPort()));
				setServerSocket(new ServerSocket(portSocket));
				setSocket(new Socket(InetAddress.getLocalHost().getHostAddress(), portSocket));

				try {

					// Define o Style
					// setUserAgentStylesheet(STYLESHEET_CASPIAN);
					setUserAgentStylesheet(STYLESHEET_MODENA);

					//Application.setUserAgentStylesheet(getClass().getResource(style).toExternalForm());

					new Forms().splashForm(Strings.getSplashView());

				} catch (Exception e) {

					Alerts.showAlert("Controle de Estoque", "Erro ao abrir a tela", e.getLocalizedMessage(),
							AlertType.ERROR);

				}

			} catch (IOException e) {

				Alerts.showAlert("Controle de Estoque", "Erro ao abrir o programa",
						"Já existe uma instância do programa aberta!", AlertType.ERROR);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Erro ao abrir o banco de dados",
					"Você deseja configurar as propriedades do banco de dados ?");

			if (result.get() == ButtonType.OK) {

				new Forms().ConfigurarPerpetiesDBForm(Strings.getConfigurarPerpetiesDBView());

			}

		}

	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		EstoqueJavaFxMain.mainScene = mainScene;
	}

	public static Scene getDialogScene() {
		return dialogScene;
	}

	public static void setDialogScene(Scene dialogScene) {
		EstoqueJavaFxMain.dialogScene = dialogScene;
	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {

		EstoqueJavaFxMain.socket = socket;

	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {

		EstoqueJavaFxMain.serverSocket = serverSocket;

	}

	public static int getPortSocket() {
		return portSocket;
	}

	public static void setPortSocket(int portSocket) {
		EstoqueJavaFxMain.portSocket = portSocket;
	}
	
	// inicia o aplicativo

	public static void main(String[] args) {

		launch(args);

	}

}
