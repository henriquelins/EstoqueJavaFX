package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DbIntegrityException;
import gui.util.Alerts;
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
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Categoria;
import model.services.CategoriaService;

public class CategoriaNovoFormController implements Initializable {

	private Categoria categoria;

	private Categoria categoriaComparar;

	private CategoriaService service;

	private static ObservableList<Categoria> listaCategoria;

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
	public TableView<Categoria> tableViewCategoria;

	@FXML
	private TableColumn<Categoria, Integer> tableColumnId;

	@FXML
	private TableColumn<Categoria, String> tableColumnNome;

	@FXML
	public void onBtNovoAction(ActionEvent event) {

		txtNome.setText("");
		txtId.setText("");
		txtNome.requestFocus();

		Categoria categoriaNova = new Categoria(null, "");

		setCategoria(categoriaNova);

	}

	@FXML
	public void onBtSalvarAction(ActionEvent event) {

		setCategoria(getFormData());

		if (this.categoria != null) {

			boolean ok = false;

			ok = compararCampos();

			if (ok == false) {

				service.categoriaNovoOuEditar(this.categoria);
				limparCampos();
				updateTableView();

			} else {

				Alerts.showAlert("Categoria", "Salvar / Editar", "Não houve alteração no registro", AlertType.INFORMATION);

			}

		}

	}

	@FXML
	public void onBtExcluirAction(ActionEvent event) {

		if (service == null) {
			throw new IllegalThreadStateException("Service está nulo");
		}
		try {

			if (categoria.getIdCategoria() != null) {

				Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Você quer deletar o setor?");

				if (result.get() == ButtonType.OK) {

					service.remove(categoria);
					updateTableView();

				}

			} else {

				Alerts.showAlert("Categoria", "Excluir", "Selecione um registro", AlertType.INFORMATION);

			}

		} catch (DbIntegrityException e) {
			Alerts.showAlert("Categoria", "Excluir", "Erro ao excluir a categoria", AlertType.INFORMATION);
			limparCampos();
		}

	}

	public void setCategoria(CategoriaService service) {
		this.service = service;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {
		
		showDetails(null);

		tableViewCategoria.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDetails(newValue));

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
	
		service = new CategoriaService();
		categoria = new Categoria();

		updateTableView();
		
	}

	public void updateTableView() {

		if (service == null) {
			throw new IllegalStateException("Service nulo");
		}

		listaCategoria = FXCollections.observableArrayList(service.findAll());
		tableViewCategoria.setItems(listaCategoria);

	}

	private Categoria getFormData() {

		Categoria categoria = new Categoria();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Categoria", null, "Digite o nome da categoria", AlertType.INFORMATION);

			txtNome.requestFocus();

			categoria = null;

		} else {

			if (txtId.getText().equals("")) {

				categoria.setIdCategoria(null);

			} else {

				categoria.setIdCategoria(Integer.valueOf(txtId.getText()));

			}

			categoria.setNome(txtNome.getText());

		}

		return categoria;

	}

	public void showDetails(Categoria categoria) {

		if (categoria != null) {

			txtId.setText(String.valueOf(categoria.getIdCategoria()));
			txtNome.setText(categoria.getNome());
			setCategoria(categoria);
			categoriaComparar = this.categoria;

		} else {

			limparCampos();

		}

	}

	public void limparCampos() {
		txtId.setText("");
		txtNome.setText("");
	};

	public boolean compararCampos() {

		boolean ok = false;

		if (categoriaComparar == null) {

			return ok;

		} else if (this.categoria.getNome().equals(categoriaComparar.getNome())) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	};

}
