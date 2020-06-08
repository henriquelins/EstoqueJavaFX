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
import model.entities.Usuario;
import model.services.UsuarioService;
import properties.PropertiesFile;

public class SCE1Main extends Application {

	// Tela cheia
	public static Scene mainScene;

	// Tela di�logo
	public static Scene dialogScene;

	private static Socket socket;

	private static ServerSocket serverSocket;

	private static int portSocket = 10050;

	@Override
	public void start(Stage primaryStage) throws SQLException {

		Connection conn = null;
		conn = DB.getConnectionTeste();

		if (conn != null) {

			try {
				// impede que seja criada uma nova inst�ncia do programa
				portSocket = Integer
						.parseInt(PropertiesFile.loadPropertiesSocket().getProperty(Strings.getPropertiessocketPort()));
				setServerSocket(new ServerSocket(portSocket));
				setSocket(new Socket(InetAddress.getLocalHost().getHostAddress(), portSocket));
				try {
					// Define o Style
					// setUserAgentStylesheet(STYLESHEET_CASPIAN);
					setUserAgentStylesheet(STYLESHEET_MODENA);
					new Forms().splashForm(Strings.getSplashView());
				} catch (Exception e) {
					Alerts.showAlert("Controle de Estoque", "Erro ao abrir a tela", e.getLocalizedMessage(),
							AlertType.ERROR);
				}

			} catch (IOException e) {
				Alerts.showAlert("Controle de Estoque", "Erro ao abrir o programa",
						"J� existe uma inst�ncia do programa aberta!", AlertType.ERROR);
			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Erro ao abrir o banco de dados",
					"Voc� deseja configurar as propriedades do banco de dados ?");

			if (result.get() == ButtonType.OK) {
				new Forms().ConfigurarPerpetiesDBForm(Strings.getConfigurarPerpetiesDBView());
			}

		}

	}

	public static Scene getMainScene() {
		return mainScene;
	}

	public static void setMainScene(Scene mainScene) {
		SCE1Main.mainScene = mainScene;
	}

	public static Scene getDialogScene() {
		return dialogScene;
	}

	public static void setDialogScene(Scene dialogScene) {
		SCE1Main.dialogScene = dialogScene;
	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		SCE1Main.socket = socket;
	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {
		SCE1Main.serverSocket = serverSocket;
	}

	public static int getPortSocket() {
		return portSocket;
	}

	public static void setPortSocket(int portSocket) {
		SCE1Main.portSocket = portSocket;
	}

	// inicia o aplicativo

	public static void main(String[] args) {
		iniciar();
		
		launch(args);
	}
	
	private static void iniciar() {

		Usuario usuario = new UsuarioService().find(1);

		if (usuario == null) {

			usuario = new Usuario(null, "ADMINISTRADOR", "adm", "11", 1);			
			new UsuarioService().usuarioNovoOuEditar(usuario);

		}

	}

}
