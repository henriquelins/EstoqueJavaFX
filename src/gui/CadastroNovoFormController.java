package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Categoria;
import model.services.CategoriaService;

public class CadastroNovoFormController implements Initializable {

	private Categoria categoria;

	private CategoriaService service;

	private static ObservableList<Categoria> listaCategoria;

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
	public void onBtSalvarCategoriaAction(ActionEvent event) {

		setCategoria(getFormData());

		if (categoria != null) {

			service.categoriaNovoOuEditar(categoria);

		}

	}

	public void setService(CategoriaService service) {
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

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));

		service = new CategoriaService();
		categoria = new Categoria();

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service está nulo");
		}

		listaCategoria = FXCollections.observableArrayList(service.findAll());
		tableViewCategoria.setItems(listaCategoria);

	}

	private Categoria getFormData() {

		Categoria categoria = new Categoria();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Categoria", null, "Digite o nome da Categoria", AlertType.INFORMATION);

			txtNome.requestFocus();

			categoria = null;

		} else {

			categoria.setIdCategoria(Integer.valueOf(txtId.getText()));
			categoria.setNome(txtNome.getText());

		}

		return categoria;

	}

}
