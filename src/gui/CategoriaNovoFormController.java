package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Categoria;
import model.entities.Setor;
import model.services.CategoriaService;
import model.services.SetorService;

public class CategoriaNovoFormController implements Initializable {

	private Categoria categoria;

	private Categoria categoriaComparar;

	private CategoriaService service;

	private static ObservableList<Categoria> listaCategoria;

	private int id_setor;

	private String setor;

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
	private ComboBox<String> comboBoxSetor;

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

		Categoria categoriaNova = new Categoria(null, "", 0);

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

				Alerts.showAlert("Categoria", "Editar Categoria", "Não houve alteração no registro",
						AlertType.INFORMATION);

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

				Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
						"Você deseja deletar a categoria " + categoria.getNome() + " ?");

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

	@FXML
	public void onSelectComboBoxSetorAction(ActionEvent event) {

		if (service == null) {

			throw new IllegalStateException("Service nulo");

		}

		SetorService setorService = new SetorService();
		setSetor(comboBoxSetor.getSelectionModel().getSelectedItem());
		setId_setor(setorService.findNomeIdSetor(getSetor()));

		listaCategoria = FXCollections.observableArrayList(service.findIdSetor(id_setor));
		tableViewCategoria.setItems(listaCategoria);

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

	private List<String> listaSetor() {

		SetorService setorService = new SetorService();
		List<String> listaSetor = new ArrayList<>();

		for (Setor setor : setorService.findAllNome()) {

			listaSetor.add(setor.getNome());

		}

		return listaSetor;

	}

	private void initializeNodes() {

		comboBoxSetor.setItems(FXCollections.observableArrayList(listaSetor()));

		showDetails(null);

		tableViewCategoria.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showDetails(newValue));

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));

		service = new CategoriaService();
		categoria = new Categoria();

	}

	public void updateTableView() {

		if (service == null) {
			throw new IllegalStateException("Service nulo");
		}

		SetorService setorService = new SetorService();	
		setId_setor(setorService.findNomeIdSetor(getSetor()));

		listaCategoria = FXCollections.observableArrayList(service.findIdSetor(id_setor));
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
			categoria.setIdSetor(getId_setor());

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

		setCategoria(new Categoria());

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

	}

	public int getId_setor() {
		return id_setor;
	}

	public void setId_setor(int id_setor) {
		this.id_setor = id_setor;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	};

}
