package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Produto;
import model.services.ProdutoService;

public class ProdutoNovoFormController implements Initializable {

	ProdutoService produtoService;

	Produto prod;

	@FXML
	private TextField txtIdProduto;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private TextField txtNome;

	@FXML
	private TextArea txtAreaDescricao;

	@FXML
	private Button btSalvarProduto;

	@FXML
	public void onBtSalvarProdutoAction(ActionEvent event) {
		
		setProd(getFormData());
		
		if (prod != null) {
			
			produtoService.prdutoNovoOuEditar(prod);
			
		}
		

	}
	

	public void setProdutoService(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	public void setProd(Produto prod) {
		this.prod = prod;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		produtoService = new ProdutoService();
		prod = new Produto();
		
		Constraints.setTextFieldInteger(txtQuantidade);
		
	}

	private Produto getFormData() {

		Produto produto = new Produto();

		if (txtCodigo.getText() == null || txtCodigo.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", null, "Digite o código do produto", AlertType.INFORMATION);

			txtCodigo.requestFocus();

			produto = null;

		} else if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", null, "Digite a quantidade inicial do produto", AlertType.INFORMATION);

			txtQuantidade.requestFocus();

			produto = null;

		} else if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", null, "Digite o nome do Produto", AlertType.INFORMATION);

			txtNome.requestFocus();

			produto = null;

		} else if (txtAreaDescricao.getText() == null || txtAreaDescricao.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", null, "Digite a descrição do produto", AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			produto = null;

		} else {

			produto.setCod(txtCodigo.getText());
			produto.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
			produto.setNome(txtNome.getText());
			produto.setDescricao(txtAreaDescricao.getText());

		}

		return produto;

	}

}
