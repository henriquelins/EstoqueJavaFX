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
import model.entities.Setor;
import model.services.SetorService;

public class SetorNovoFormController implements Initializable {

	private Setor setor;

	private SetorService service;
	
	private static ObservableList<Setor> listaSetor;

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
	private TableColumn<Setor, Integer> tableColumnId;

	@FXML
	private TableColumn<Setor, String> tableColumnNome;

	@FXML
	public void onBtSalvarSetorAction(ActionEvent event) {

		setSetor(getFormData());

		if (setor != null) {

			service.setorNovoOuEditar(setor);

		}

	}
	
	@FXML
	public void onBtExcluirSetoroAction(ActionEvent event) {

		service.remove(setor);

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

	private void initializeNodes() {
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idSetor"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		
		service = new SetorService();
		setor = new Setor();
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service está nulo");
		}

		listaSetor = FXCollections.observableArrayList(service.findAll());
		tableViewSetor.setItems(listaSetor);
		
	}


	private Setor getFormData() {

		Setor setor = new Setor();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Setor", null, "Digite o nome setor", AlertType.INFORMATION);

			txtNome.requestFocus();

			setor = null;

		} else {
			
			setor.setIdSetor(Integer.valueOf(txtId.getText()));
			setor.setNome(txtNome.getText());

		}

		return setor;

	}

}
