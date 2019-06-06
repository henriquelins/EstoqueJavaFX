package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController implements Initializable {
	
	@FXML
	private TextField txtLogin;
	
	@FXML
	private PasswordField txtSenha;
	
	@FXML
	private Button btOK;
	
	@FXML
	private Button btCancelar;

	@FXML
	public void onBtOKAction(ActionEvent event) {
		System.out.println("btOK");
	}
	
	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
}
