package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DbIntegrityException;
import gui.util.Alerts;
import gui.util.Constraints;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.entities.Produto;
import model.entities.Setor;
import model.services.SetorService;

public class SetorNovoFormController implements Initializable {

	private Setor setor;

	private Setor setorComparar;

	private SetorService service;

	private static ObservableList<Setor> listaSetor;

	@FXML
	private Button btNovo;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btExcluir;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	public TableView<Setor> tableViewSetor;

	@FXML
	private TextField txtIdUsuario;

	@FXML
	private TableColumn<Produto, String> tableColumnIndex;

	@FXML
	private TableColumn<Setor, String> tableColumnId;

	@FXML
	private TableColumn<Setor, String> tableColumnNome;

	@FXML
	public void onBtNovoAction(ActionEvent event) {

		txtNome.setText("");
		txtId.setText("");
		txtNome.requestFocus();

		Setor setorNovo = new Setor(null, "");

		setSetor(setorNovo);

	}

	@FXML
	public void onBtSalvarAction(ActionEvent event) {

		setSetor(getFormData());

		if (this.setor != null) {

			boolean ok = false;

			ok = compararCampos();

			if (ok == false) {

				service.setorNovoOuEditar(this.setor);
				limparCampos();
				updateTableView();

			}

		}

	}

	@FXML
	public void onBtExcluirAction(ActionEvent event) {

		if (service == null) {

			throw new IllegalThreadStateException("Service está nulo");

		}
		try {

			if (setor.getIdSetor() != null) {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
						"Você deseja deletar o setor " + setor.getNome().toUpperCase() + " ?");

				if (result.get() == ButtonType.OK) {

					service.remove(setor);
					updateTableView();

				}

			} else {

				Alerts.showAlert("Setor", "Excluir", "Selecione um registro", AlertType.INFORMATION);

			}

		} catch (DbIntegrityException e) {

			Alerts.showAlert("Setor", "Excluir", "Erro ao excluir o setor", AlertType.INFORMATION);
			limparCampos();

		}

	}

	public void setService(SetorService service) {
		this.service = service;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	@SuppressWarnings("unlikely-arg-type")
	private void initializeNodes() {

		showDetails(null);

		tableViewSetor.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDetails(newValue));

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewSetor.getItems().indexOf(column.getValue()) + 1)));

		tableColumnId.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.tresDigitos(param.getValue().getIdSetor())));

		tableColumnNome
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome().toUpperCase()));

		service = new SetorService();
		setor = new Setor();

		updateTableView();

	}

	public void updateTableView() {

		if (service == null) {

			throw new IllegalStateException("Service nulo");
		}

		listaSetor = FXCollections.observableArrayList(service.findAllNome());
		tableViewSetor.setItems(listaSetor);

	}

	private Setor getFormData() {

		Setor setor = new Setor();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Setor", "Campo obrigatório", "Digite o nome setor", AlertType.INFORMATION);

			txtNome.requestFocus();

			setor = null;

		} else {

			if (txtId.getText().equals("")) {

				setor.setIdSetor(null);

			} else {

				setor.setIdSetor(Integer.valueOf(txtId.getText()));

			}

			setor.setNome(txtNome.getText().toUpperCase());

		}

		return setor;

	}

	public void showDetails(Setor setor) {

		if (setor != null) {

			txtId.setText(Constraints.tresDigitos(setor.getIdSetor()));
			txtNome.setText(setor.getNome().toUpperCase());
			setSetor(setor);
			setorComparar = this.setor;

		} else {

			limparCampos();

		}

	}

	public void limparCampos() {

		txtId.setText("");
		txtNome.setText("");

		setSetor(new Setor());

	};

	public boolean compararCampos() {

		boolean ok = false;

		if (setorComparar == null) {

			return ok;

		} else if (this.setor.getNome().equalsIgnoreCase(setorComparar.getNome())) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	};

}
