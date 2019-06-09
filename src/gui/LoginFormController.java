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

	private Usuario logado;

	private UsuarioService service;

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

		if (usuario.getName() != null && usuario.getSenha() != null) {

			logado = service.login(usuario);

			if (logado.getName() != null && logado.getSenha() != null) {

				Utils.currentStage(event).close();

				Stage parentStage = Utils.currentStage(event);
				createPrincipalForm("/gui/PrincipalView.fxml", parentStage);

			} else {

				Alerts.showAlert("Login", null, "Login não confirmado", AlertType.ERROR);

			}

		} else {

		}

	}

	public void setUsuarioServices(UsuarioService service) {
		this.service = service;
	}

	public void setUsuario(Usuario usuario) {
		this.logado = usuario;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		logado = new Usuario();
		service = new UsuarioService();

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

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela principal", e.getMessage(), AlertType.ERROR);

		}

	}

	private Usuario getFormData() {

		Usuario usuario = new Usuario();

		if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {

			Alerts.showAlert("Login", null, "Digite seu login", AlertType.INFORMATION);

			txtLogin.requestFocus();

		} else if (String.valueOf(pswSenha.getText()) == null || pswSenha.getText().trim().equals("")) {

			Alerts.showAlert("Login", null, "Digite sua senha", AlertType.INFORMATION);

			pswSenha.requestFocus();

		} else {

			usuario.setName(txtLogin.getText());
			usuario.setSenha(pswSenha.getText());

		}

		return usuario;

	}

}
