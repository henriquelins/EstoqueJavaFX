package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Produto;
import model.entities.enums.Categoria;
import model.entities.enums.Setor;
import model.services.ProdutoService;

public class ProdutoEditarFormController implements Initializable {

	private ProdutoService produtoService;

	private Produto produto;

	@FXML
	private TextField txtIdProduto;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private ComboBox<Setor> comboBoxSetor;

	private ObservableList<Setor> obsListSetor;

	@FXML
	private ComboBox<Categoria> comboBoxCategoria;

	private ObservableList<Categoria> obsListCategoria;

	@FXML
	private TextArea txtAreaDescricao;

	@FXML
	private Button btSalvarProduto;

	@FXML
	public void onBtSalvarProdutoAction(ActionEvent event) {

		setProduto(getFormData());

		if (produto != null) {

			produtoService.produtoNovoOuEditar(produto);

		}

	}

	public void setProdutoService(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		produtoService = new ProdutoService();
		produto = new Produto();

		Constraints.setTextFieldInteger(txtQuantidade);
		
		txtNome.setText(produto.getNome());
		txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
		txtAreaDescricao.setText(produto.getDescricao());
		 
		obsListSetor = FXCollections.observableArrayList(Setor.values());
		comboBoxSetor.setItems(obsListSetor);

		obsListCategoria = FXCollections.observableArrayList(Categoria.values());
		comboBoxCategoria.setItems(obsListCategoria);
		
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

			Alerts.showAlert("Novo Produto", null, "Digite a descri��o do produto", AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			produto = null;

		} else {

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
		comboBoxSetor.setPromptText(PrincipalFormController.getProduto().getSetor());
		comboBoxCategoria.setPromptText(PrincipalFormController.getProduto().getCategoria());
		txtAreaDescricao.setText(PrincipalFormController.getProduto().getDescricao());		

	}
	 

}