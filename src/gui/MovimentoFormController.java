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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.entities.Usuario;
import model.entities.enums.TipoMovimentacao;
import model.services.MovimentacaoService;
import model.services.ProdutoService;

public class MovimentoFormController implements Initializable{
	
	MovimentacaoService movimentacaoService;
	
	Movimentacao movimentacao;
	
	@FXML
	private Label labelNome;
	
	@FXML
	private Label labelEstoqueAtual;
	
	@FXML
	private ComboBox <TipoMovimentacao> comboBoxTipoDeSaida;
	
	private ObservableList<TipoMovimentacao> obsListTipoMovimentacao;
	
	@FXML
	private TextField txtQuantidade;
	
	@FXML
	private TextArea txtAreaObservacoes;
	
	@FXML
	private Button btnSalvarMovimentacao;
	
	@FXML
	public void onBtnSalvarMovimentacaoAction(ActionEvent event) {
		
			
		
		setMovimentacao(getFormData(LoginFormController.getLogadoIdUsuario(), ));

		if (movimentacao != null) {

			movimentacaoService.movimentacaoSaidaOuEntrada(movimentacao);

		}

	}
	
	
	public void setMovimentacaService(MovimentacaoService movimentacaoService) {
		this.movimentacaoService = movimentacaoService;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		obsListTipoMovimentacao = FXCollections.observableArrayList(TipoMovimentacao.values());
		comboBoxTipoDeSaida.setItems(obsListTipoMovimentacao);
		
		initializeNodes();

	}

	private void initializeNodes() {

		movimentacaoService = new MovimentacaoService();
		movimentacao = new Movimentacao();
		
		Constraints.setTextFieldInteger(txtQuantidade);
		
	}

	private Movimentacao getFormData(Integer idUsuario, Integer idProduto) {

		Movimentacao mov = new Movimentacao();
		
		
		if (comboBoxTipoDeSaida.getSelectionModel().getSelectedItem() == null) {
			
			Alerts.showAlert("Movimenta��o do Produto", null, "Selecione o tipo de movimenta��o", AlertType.INFORMATION);
			
			comboBoxTipoDeSaida.requestFocus();

			mov = null;
		
		} else if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {
			
			Alerts.showAlert("Sa�da Produto", null, "Digite a quantidade do produto", AlertType.INFORMATION);
			
			txtQuantidade.requestFocus();

			mov = null;
			
		}  else if (txtAreaObservacoes.getText() == null || txtAreaObservacoes.getText().trim().equals("")) {

			Alerts.showAlert("Sa�da Produto", null, "Digite a quantidade do produto", AlertType.INFORMATION);

			txtAreaObservacoes.requestFocus();

			mov = null;

		} else {
				
			mov.setIdProduto(idProduto);
			mov.setIdUsuario(idUsuario);
			mov.setTipo(comboBoxTipoDeSaida.getSelectionModel().getSelectedItem());
			mov.setValorMovimento(Integer.valueOf(txtQuantidade.getText()));
			mov.setObservacoesMovimentacao(txtAreaObservacoes.getText());
			mov.setQuantidadeAnterior(Integer.valueOf(labelEstoqueAtual.getText()));
			
		}

		return mov;

	}


}
