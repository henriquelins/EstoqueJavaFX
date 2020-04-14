package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Produto;
import model.entities.Usuario;
import model.services.UsuarioService;

public class UsuarioFormController implements Initializable, DataChangeListener {

	private UsuarioService service;

	private static Usuario usuario;

	private Usuario usuarioTabela;

	private Usuario usuarioComparar;

	private static ObservableList<Usuario> listaUsuarios;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Label labelTitle;

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
	private Button btNovoUsuario;

	@FXML
	private Button btSalvarUsuario;

	@FXML
	private Button btCancelarEditarUsuario;

	@FXML
	private ComboBox<String> comboBoxAcesso;

	@FXML
	public TableView<Usuario> tableViewUsuario;

	@FXML
	private TableColumn<Produto, String> tableColumnIndex;

	@FXML
	private TableColumn<Usuario, String> tableColumnId;

	@FXML
	private TableColumn<Usuario, String> tableColumnNome;

	@FXML
	private TableColumn<Usuario, String> tableColumnLogin;

	@FXML
	private TableColumn<Usuario, String> tableColumnAcesso;

	@FXML
	private TableColumn<Usuario, Usuario> tableColumnEDIT;

	@FXML
	private TableColumn<Usuario, Usuario> tableColumnREMOVE;

	@FXML
	public void onBtNovoUsuarioAction(ActionEvent event) {

		limparCampos();
		editarCamposFalso();
		novoCamposDoUsuario();
		tableViewUsuario.setDisable(true);

	}

	@FXML
	public void onBtSalvarUsuarioAction(ActionEvent event) {

		setUsuario(getFormData());

		if (usuario != null) {

			editarCamposFalso();
			tableViewUsuario.setDisable(false);

			boolean ok = false;

			ok = compararCampos();

			if (ok == false) {
							
				service.usuarioNovoOuEditar(usuario);
				botoesFalso();
				onDataChanged();

			} else {

				Alerts.showAlert("Usu�rio", "Editar Usu�rio", "N�o houve altera��o no registro", AlertType.INFORMATION);

				botoesFalso();

			}

		}
	}

	@FXML
	public void onBtCancelarEditarUsuarioAction(ActionEvent event) {

		limparCampos();
		botoesFalso();
		editarCamposFalso();

		tableViewUsuario.setDisable(false);

		if (usuarioTabela.getIdUsuario() != null) {

			showUsuarioDetails(usuarioTabela);

		} else {

			limparCampos();

		}

	}

	protected void editarCamposDoUsuario() {

		if (usuarioTabela.getIdUsuario() != null) {

			txtNome.setEditable(true);
			txtLogin.setEditable(true);
			pswSenha.setEditable(true);
			pswRepetirSenha.setEditable(true);

			btNovoUsuario.setDisable(false);
			btCancelarEditarUsuario.setVisible(true);
			btSalvarUsuario.setVisible(true);

			tableViewUsuario.setDisable(true);

			btNovoUsuario.setDisable(true);

		} else {

			Alerts.showAlert("Usu�rios", "Editar Usu�rio", "Selecione um registro", AlertType.INFORMATION);

		}

	}

	protected void novoCamposDoUsuario() {

		txtNome.setEditable(true);
		txtLogin.setEditable(true);
		pswSenha.setEditable(true);
		pswRepetirSenha.setEditable(true);

		btNovoUsuario.setDisable(true);
		btCancelarEditarUsuario.setVisible(true);
		btSalvarUsuario.setVisible(true);

		tableViewUsuario.setDisable(true);

	}

	protected void limparCampos() {

		txtIdUsuario.setText("");
		txtNome.setText("");
		txtLogin.setText("");
		pswSenha.setText("");
		pswRepetirSenha.setText("");
		comboBoxAcesso.setValue("SELECIONE O TIPO DE ACESSO...");

		setUsuario(new Usuario());

	}

	protected void editarCamposFalso() {

		txtIdUsuario.setEditable(false);
		txtNome.setEditable(false);
		txtLogin.setEditable(false);
		pswSenha.setEditable(false);
		pswRepetirSenha.setEditable(false);

	}

	protected void botoesFalso() {

		btCancelarEditarUsuario.setVisible(false);
		btSalvarUsuario.setVisible(false);
		btNovoUsuario.setDisable(false);

	}

	public void showUsuarioDetails(Usuario usuario) {

		if (btCancelarEditarUsuario.isVisible() != true) {

			if (usuario != null) {
				txtIdUsuario.setText(Constraints.tresDigitos(usuario.getIdUsuario()));
				txtNome.setText(usuario.getNome().toUpperCase());
				txtLogin.setText(usuario.getLogin());
				pswSenha.setText("******");
				pswRepetirSenha.setText("******");
				comboBoxAcesso.setValue(selectChoiceBox(usuario.getAcesso()));

				setUsuarioTabela(usuario);

				setUsuario(usuario);
				usuarioComparar = usuario;

			} else {

				setUsuarioTabela(null);
				limparCampos();

			}
		}
	}

	public void setUsuarioService(UsuarioService service) {

		this.service = service;

	}

	public void setUsuario(Usuario usuario) {

		UsuarioFormController.usuario = usuario;

	}

	public static Usuario getUsuario() {

		return usuario;

	}

	public void setUsuarioTabela(Usuario usuario) {

		this.usuarioTabela = usuario;

	}

	private ObservableList<String> listaAcesso() {

		ObservableList<String> listaChoiceBox = FXCollections.observableArrayList(Strings.getCase1choiceBoxAcesso1(),
				Strings.getCase1choiceBoxAcesso2());
		return listaChoiceBox;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	@SuppressWarnings("unlikely-arg-type")
	private void initializeNodes() {

		labelTitle.setText(Strings.getTitleUsuario());

		showUsuarioDetails(null);

		btCancelarEditarUsuario.setVisible(false);
		btSalvarUsuario.setVisible(false);

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewUsuario.getItems().indexOf(column.getValue()) + 1)));

		tableColumnId.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.tresDigitos(param.getValue().getIdUsuario())));

		tableColumnNome
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome().toUpperCase()));

		tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("Login"));

		tableColumnAcesso.setCellValueFactory(
				(param) -> new SimpleStringProperty(selectChoiceBox(param.getValue().getAcesso()).substring(3)));

		tableViewUsuario.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showUsuarioDetails(newValue));

		service = new UsuarioService();
		usuarioTabela = new Usuario();

		comboBoxAcesso.setItems(listaAcesso());

		updateTableView();

	}

	public void updateTableView() {

		if (service == null) {

			throw new IllegalStateException("Servi�o est� nulo");

		}

		listaUsuarios = FXCollections.observableArrayList(service.findAll());
		tableViewUsuario.setItems(listaUsuarios);

		tableViewUsuario.setDisable(false);
		limparCampos();

		initEditButton();
		initRemoveButton();

	}

	private Usuario getFormData() {

		Usuario usuario = new Usuario();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usu�rio", "Campo obrigat�rio", "Digite seu nome!", AlertType.INFORMATION);
			txtNome.requestFocus();
			usuario = null;

		} else if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usu�rio", "Campo obrigat�rio", "Digite seu login!", AlertType.INFORMATION);
			txtLogin.requestFocus();
			usuario = null;

		} else if (pswSenha.getText() == null || pswSenha.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usu�rio", "Campo obrigat�rio", "Digite sua senha!", AlertType.INFORMATION);

			pswSenha.requestFocus();
			usuario = null;

		} else if (pswRepetirSenha.getText() == null || pswRepetirSenha.getText().trim().equals("")) {

			Alerts.showAlert("Novo Usu�rio", "Campo obrigat�rio", "Digite a confirma��o da senha!",
					AlertType.INFORMATION);

			pswRepetirSenha.requestFocus();
			usuario = null;

		} else if (!pswSenha.getText().equals(pswRepetirSenha.getText())
				|| !pswRepetirSenha.getText().equals(pswSenha.getText())) {

			Alerts.showAlert("Novo Usu�rio", "Campo obrigat�rio", "A confirma��o da senha n�o est� igual a senha!",
					AlertType.INFORMATION);

			pswRepetirSenha.requestFocus();
			usuario = null;

		} else if (comboBoxAcesso.getSelectionModel().getSelectedItem().equals("SELECIONE O TIPO DE ACESSO...")) {

			Alerts.showAlert("Novo Usu�rio", "Campo obrigat�rio", "Selecione o acesso!", AlertType.INFORMATION);

			comboBoxAcesso.requestFocus();
			usuario = null;

		} else {

			if (txtIdUsuario.getText().equals("")) {

				usuario.setIdUsuario(null);

			} else {

				usuario.setIdUsuario(Integer.valueOf(txtIdUsuario.getText()));

			}

			usuario.setNome(txtNome.getText().toUpperCase());
			usuario.setLogin(txtLogin.getText());
			usuario.setSenha(pswSenha.getText());
			usuario.setAcesso(selectAcesso(comboBoxAcesso.getSelectionModel().getSelectedItem()));

		}

		return usuario;

	}

	private void initEditButton() {

		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Usuario, Usuario>() {
			private final Button btEditar = new Button("Editar");

			@Override
			protected void updateItem(Usuario usuario, boolean empty) {

				super.updateItem(usuario, empty);

				if (usuario == null) {

					setGraphic(null);
					return;

				}

				setGraphic(btEditar);
				btEditar.setOnAction(event -> editarCamposDoUsuario());

			}
		});
	}

	private void initRemoveButton() {

		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Usuario, Usuario>() {
			private final Button btExcluir = new Button("Excluir");

			@Override
			protected void updateItem(Usuario usuario, boolean empty) {

				super.updateItem(usuario, empty);

				if (usuario == null) {

					setGraphic(null);
					return;

				}

				setGraphic(btExcluir);
				btExcluir.setOnAction(event -> removeEntity(usuario));

			}
		});
	}

	private void removeEntity(Usuario usuario) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
				"Voc� deseja deletar o usu�rio " + usuario.getNome() + " ?");

		if (result.get() == ButtonType.OK) {

			if (service == null) {

				throw new IllegalThreadStateException("Service est� nulo");

			}

			try {

				service.remove(usuario);
				onDataChanged();

			} catch (DbIntegrityException e) {

				Alerts.showAlert("Erro ao remover o usu�rio " + usuario.getNome() + " !", null, e.getMessage(),
						AlertType.ERROR);

			}
		}
	}

	@Override
	public void onDataChanged() {

		this.updateTableView();

	}

	public boolean compararCampos() {

		boolean ok = false;

		if (usuarioComparar == null) {

			return ok;

		} else if (usuario.getNome().equals(usuarioComparar.getNome())
				&& usuario.getLogin().equals(usuarioComparar.getLogin())
				&& usuario.getSenha().equals(usuarioComparar.getSenha())
				&& usuario.getAcesso() == usuarioComparar.getAcesso()) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	};

	public String selectChoiceBox(int acesso) {

		String selectComboBoxAcesso = "";

		switch (acesso) {

		case 1:

			selectComboBoxAcesso = Strings.getCase1choiceBoxAcesso1();
			break;

		case 2:

			selectComboBoxAcesso = Strings.getCase1choiceBoxAcesso2();
			break;

		}

		return selectComboBoxAcesso;

	}

	public int selectAcesso(String selectChoiceBox) {

		int acesso = Integer.valueOf(selectChoiceBox.substring(0, 1));

		return acesso;
	}

}
