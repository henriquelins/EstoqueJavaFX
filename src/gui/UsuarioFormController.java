package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import model.entities.Produto;
import model.entities.Usuario;
import model.entities.enums.Categoria;
import model.entities.enums.Setor;
import model.services.UsuarioService;

public class UsuarioFormController implements Initializable {

	private UsuarioService usuarioService;

	private Usuario usuario;

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
	private HBox hbox;
	
	
	@FXML
	public TableView<Usuario> tableViewUsuario;
	
	@FXML
	private TableColumn<Usuario, Integer> tableColumnId;

	@FXML
	private TableColumn<Usuario, String> tableColumnNome;

	@FXML
	private TableColumn<Usuario, String> tableColumnLogin;
	

	@FXML
	public void onBtSalvarUsuarioAction(ActionEvent event) {

		setUser(getFormData());
		
		if (usuario != null) {
			
			usuarioService.usuarioNovoOuEditar(usuario);
		
		} 
		
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setUser(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("Login"));
		
		updateTableView();
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		hbox.prefHeightProperty().bind(stage.heightProperty());
		tableViewUsuario.prefHeightProperty().bind(stage.heightProperty());
		
		usuarioService = new UsuarioService();
		usuario = new Usuario();

	}
	
	private ObservableList<Usuario> listaUsuarios() {
		return FXCollections.observableArrayList(
		
				new Usuario(1,"Administrador", "Adm", "10"),
				new Usuario(2,"Operador1", "op1", "10"),
				new Usuario(3,"Operador2", "op2", "10"));
	
	}
	
	public void updateTableView() {

		tableViewUsuario.setItems(listaUsuarios());
		//initMovimentacaoButton();
		//initEditButton();
		//initRemoveButton();

	}

	private Usuario getFormData() {

		Usuario usuario = new Usuario();
	
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usu�rio", null, "Digite seu nome", AlertType.INFORMATION);

			txtNome.requestFocus();
			
			usuario = null;

		} else if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usu�rio", null, "Digite seu login", AlertType.INFORMATION);

			txtLogin.requestFocus();
			
			usuario = null;


		} else if (pswSenha.getText() == null || pswSenha.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usu�rio", null, "Digite sua senha", AlertType.INFORMATION);

			pswSenha.requestFocus();
			
			usuario = null;


		} else if (pswRepetirSenha.getText() == null || pswRepetirSenha.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usu�rio", null, "Digite a confirma��o da senha", AlertType.INFORMATION);

			pswRepetirSenha.requestFocus();
			
			usuario = null;

		} else if (!pswSenha.getText().equals(pswRepetirSenha.getText()) || !pswRepetirSenha.getText().equals(pswSenha.getText()) ) {

			Alerts.showAlert("Novo Usu�rio", null, "A confirma��o da senha n�o est� igual a senha!", AlertType.INFORMATION);

			pswRepetirSenha.requestFocus();
			
			usuario = null;

		} else {

			usuario.setNome(txtNome.getText());
			usuario.setLogin(txtLogin.getText());
			usuario.setSenha(pswSenha.getText());

		}

		return usuario;
		
	}

}
