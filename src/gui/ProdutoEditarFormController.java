package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Produto;
import model.services.ProdutoService;

public class ProdutoEditarFormController implements Initializable, DataChangeListener {

	private ProdutoService produtoService;

	private Produto produto;

	private PrincipalFormController principalController;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdProduto;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private ComboBox<String> comboBoxSetor;

	@FXML
	private ComboBox<String> comboBoxCategoria;

	@FXML
	private TextArea txtAreaDescricao;

	@FXML
	private Button btSalvarProduto;

	@FXML
	public void onBtSalvarProdutoAction(ActionEvent event) {
		setProduto(getFormData());

		if (produto != null) {

			produtoService.produtoNovoOuEditar(produto);
			notifyDataChangeListeners();
		}

	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	private void notifyDataChangeListeners() {

		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	public void setProdutoService(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	private List<String> listaSetor() {

		List<String> listaSetor = new ArrayList<>();
		listaSetor.add("Crachás");
		listaSetor.add("Suporte");
		return listaSetor;

	}

	private List<String> listaCategoria() {

		List<String> listaCategoria = new ArrayList<>();
		listaCategoria.add("Tintas crachás");
		listaCategoria.add("Insumos crachás");
		listaCategoria.add("Insumos Laminação");
		listaCategoria.add("Equipamentos Suporte");
		listaCategoria.add("Insumos suporte");
		listaCategoria.add("Material escritório");

		return listaCategoria;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		comboBoxSetor.setItems(FXCollections.observableArrayList(listaSetor()));
		comboBoxCategoria.setItems(FXCollections.observableArrayList(listaCategoria()));

		produtoService = new ProdutoService();
		produto = new Produto();

		Constraints.setTextFieldInteger(txtQuantidade);

		txtNome.setText(produto.getNome());
		txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
		txtAreaDescricao.setText(produto.getDescricao());

		startFields();

		updateFormData();

	}

	private Produto getFormData() {

		Produto produto = new Produto();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", null, "Digite o nome do Produto", AlertType.INFORMATION);

			txtNome.requestFocus();

			produto = null;

		} else if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", null, "Digite a quantidade inicial do produto", AlertType.INFORMATION);

			txtQuantidade.requestFocus();

			produto = null;

		} else if (comboBoxSetor.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Novo Produto", null, "Selecione o setor", AlertType.INFORMATION);

			comboBoxSetor.requestFocus();

			produto = null;

		} else if (comboBoxCategoria.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Novo Produto", null, "Selecione a categoria", AlertType.INFORMATION);

			comboBoxCategoria.requestFocus();

			produto = null;

		} else if (txtAreaDescricao.getText() == null || txtAreaDescricao.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", null, "Digite a descrição do produto", AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			produto = null;

		} else {

			produto.setIdProduto(Integer.valueOf(txtIdProduto.getText()));
			produto.setNome(txtNome.getText());
			produto.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
			produto.setSetor(String.valueOf(comboBoxSetor.getSelectionModel().getSelectedItem()));
			produto.setCategoria(String.valueOf(comboBoxCategoria.getSelectionModel().getSelectedItem()));
			produto.setDescricao(txtAreaDescricao.getText());

		}

		return produto;

	}

	public void updateFormData() {

		txtIdProduto.setText(String.valueOf(PrincipalFormController.getProduto().getIdProduto()));
		txtNome.setText(PrincipalFormController.getProduto().getNome());
		txtQuantidade.setText(String.valueOf(PrincipalFormController.getProduto().getQuantidade()));
		comboBoxSetor.setValue(PrincipalFormController.getProduto().getSetor());
		comboBoxCategoria.setValue(PrincipalFormController.getProduto().getCategoria());
		txtAreaDescricao.setText(PrincipalFormController.getProduto().getDescricao());

	}

	@Override
	public void onDataChanged() {

		principalController.updateTableView();

	}

	public void startFields() {
		txtNome.setText(produto.getNome());
		txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
		txtAreaDescricao.setText(produto.getDescricao());
	}

}
