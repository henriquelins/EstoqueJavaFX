package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Usuario;
import model.services.UsuarioService;

public class UsuarioFormController implements Initializable {

	private UsuarioService usuarioService;

	private Usuario user;

	@FXML
	private TextField txtIdUsuario;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtLogin;

	@FXML
	private PasswordField pswSenha;

	@FXML
	private PasswordField pswRepetirSenha;

	@FXML
	private Button btSalvarUsuario;

	@FXML
	public void onBtSalvarUsuarioAction(ActionEvent event) {

		setUser(getFormData());
		
		if (user != null) {
			
			usuarioService.usuarioNovoOuEditar(user);
		
		} 
		
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		usuarioService = new UsuarioService();
		user = new Usuario();

	}

	private Usuario getFormData() {

		Usuario usuario = new Usuario();
	
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usuário", null, "Digite seu nome", AlertType.INFORMATION);

			txtNome.requestFocus();
			
			usuario = null;

		} else if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usuário", null, "Digite seu login", AlertType.INFORMATION);

			txtLogin.requestFocus();
			
			usuario = null;


		} else if (pswSenha.getText() == null || pswSenha.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usuário", null, "Digite sua senha", AlertType.INFORMATION);

			pswSenha.requestFocus();
			
			usuario = null;


		} else if (pswRepetirSenha.getText() == null || pswRepetirSenha.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usuário", null, "Digite a confirmação da senha", AlertType.INFORMATION);

			pswRepetirSenha.requestFocus();
			
			usuario = null;

		} else if (!pswSenha.getText().equals(pswRepetirSenha.getText()) || !pswRepetirSenha.getText().equals(pswSenha.getText()) ) {

			Alerts.showAlert("Novo Usuário", null, "A confirmação da senha não está igual a senha!", AlertType.INFORMATION);

			pswRepetirSenha.requestFocus();
			
			usuario = null;

		} else {

			usuario.setName(txtNome.getText());
			usuario.setLogin(txtLogin.getText());
			usuario.setSenha(pswSenha.getText());

		}

		return usuario;
		
	}

}
