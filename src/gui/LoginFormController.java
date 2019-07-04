package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Usuario;
import model.services.UsuarioService;

public class LoginFormController implements Initializable {

	private static Usuario logado;

	private UsuarioService usuarioService;

	@FXML
	private TextField txtLogin;

	@FXML
	private PasswordField pswSenha;

	@FXML
	private Button btOK;

	@FXML
	public void onBtOKAction(ActionEvent event) {

		Usuario usuario = new Usuario();
		usuario = getFormData();

		if (usuario.getLogin() != null && usuario.getSenha() != null) {

			setLogado(usuarioService.login(usuario));

			try {

				if (logado != null) {

					Utils.currentStage(event).close();

					Stage parentStage = Utils.currentStage(event);
					createPrincipalForm("/gui/PrincipalView.fxml", parentStage);

				} else {

					Alerts.showAlert("Login", null, "Login não confirmado", AlertType.ERROR);

					txtLogin.setText("");
					pswSenha.setText("");
					txtLogin.requestFocus();

				}

			} catch (NullPointerException e) {

				Alerts.showAlert("Login", null, e.getLocalizedMessage(), AlertType.ERROR);
			}

		}

	}

	public static void setLogado(Usuario logado) {
		LoginFormController.logado = logado;
	}

	public static Usuario getLogado() {
		return logado;
	}

	public static String usuarioLogado() {
		return logado.usuarioLogado();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {
		logado = new Usuario();
		usuarioService = new UsuarioService();
	}

	private void createPrincipalForm(String absoluteName, Stage parenteStage) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			ScrollPane scrollPane = loader.load();

			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			Stage principalStage = new Stage();
			principalStage.setTitle("Controle de Estoque");

			Main.setMainScene(new Scene(scrollPane));

			principalStage.setScene(Main.getMainScene());
			principalStage.setResizable(true);
			principalStage.initOwner(parenteStage);
			principalStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela principal", e.getCause().toString(),
					AlertType.ERROR);

		}

	}

	private Usuario getFormData() {

		Usuario usuario = new Usuario();

		if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {

			Alerts.showAlert("Login", null, "Digite seu login", AlertType.INFORMATION);

			txtLogin.requestFocus();

			usuario.setLogin(null);
			usuario.setSenha(null);

		} else if (String.valueOf(pswSenha.getText()) == null || pswSenha.getText().trim().equals("")) {

			Alerts.showAlert("Login", null, "Digite sua senha", AlertType.INFORMATION);

			pswSenha.requestFocus();

			usuario.setLogin(null);
			usuario.setSenha(null);

		} else {

			usuario.setLogin(txtLogin.getText());
			usuario.setSenha(pswSenha.getText());

		}

		return usuario;

	}

}
