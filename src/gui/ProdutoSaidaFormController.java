package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Produto;
import model.services.ProdutoService;

public class ProdutoSaidaFormController implements Initializable{
	
	ProdutoService produtoService;

	Produto prod;
	
	@FXML
	private Label labelNome;
	
	@FXML
	private Label labelEstoqueAtual;
	
	@FXML
	private ComboBox <String> cbTipoDeSaida;
	
	@FXML
	private TextField txtQuantidade;
	
	@FXML
	private TextArea txtAreaObservacoes;
	
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
		
		
		if (cbTipoDeSaida.getSelectionModel().isSelected(0)) {
			
			Alerts.showAlert("Saída Produto", null, "Selecione o tipo da Saída produto", AlertType.INFORMATION);
			
			cbTipoDeSaida.requestFocus();

			produto = null;
		
		} else if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {
			
			Alerts.showAlert("Saída Produto", null, "Digite a quantidade do produto", AlertType.INFORMATION);
			
			txtQuantidade.requestFocus();

			produto = null;
			
		}  else if (txtAreaObservacoes.getText() == null || txtAreaObservacoes.getText().trim().equals("")) {

			Alerts.showAlert("Saída Produto", null, "Digite a quantidade do produto", AlertType.INFORMATION);

			txtAreaObservacoes.requestFocus();

			produto = null;

		} else {
						
			
		}

		/*if (labelNome.getText() == null || labelNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", null, "Digite o código do produto", AlertType.INFORMATION);

			labelNome.requestFocus();

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

		}*/

		return produto;

	}


}
