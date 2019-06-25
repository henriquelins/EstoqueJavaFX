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
import model.entities.enums.TipoMovimentacao;
import model.services.MovimentacaoService;

public class MovimentacaoFormController implements Initializable {

	MovimentacaoService movimentacaoService;

	Movimentacao movimentacao;
	
	@FXML
	private Label labelNome;

	@FXML
	private Label labelEstoqueAtual;

	@FXML
	private ComboBox<TipoMovimentacao> comboBoxTipoDeSaida;

	private ObservableList<TipoMovimentacao> obsListTipoMovimentacao;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private TextArea txtAreaObservacoes;

	@FXML
	private Button btnSalvarMovimentacao;

	@FXML
	public void onBtnSalvarMovimentacaoAction(ActionEvent event) {

		setMovimentacao(getFormData());

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
	
	public void setProduto(Produto produto) {
	}
	
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		initializeNodes();

	}

	private void initializeNodes() {
		
		obsListTipoMovimentacao = FXCollections.observableArrayList(TipoMovimentacao.values());
		comboBoxTipoDeSaida.setItems(obsListTipoMovimentacao);
		
		labelNome.setText(PrincipalFormController.getProduto().getNome());
		labelEstoqueAtual.setText(String.valueOf(PrincipalFormController.getProduto().getQuantidade()));
		
		movimentacaoService = new MovimentacaoService();
		movimentacao = new Movimentacao();
		new Produto(); 

		Constraints.setTextFieldInteger(txtQuantidade);
		
		updateFormData();
			
	}
	
	private Movimentacao getFormData() {

		Movimentacao mov = new Movimentacao();

		if (comboBoxTipoDeSaida.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Movimentação do Produto", null, "Selecione o tipo de movimentação",
					AlertType.INFORMATION);

			comboBoxTipoDeSaida.requestFocus();

			mov = null;

		} else if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {

			Alerts.showAlert("Saída Produto", null, "Digite a quantidade do produto", AlertType.INFORMATION);

			txtQuantidade.requestFocus();

			mov = null;

		} else if (txtAreaObservacoes.getText() == null || txtAreaObservacoes.getText().trim().equals("")) {

			Alerts.showAlert("Saída Produto", null, "Digite um observação sobre o produto", AlertType.INFORMATION);

			txtAreaObservacoes.requestFocus();

			mov = null;

		} else {

			mov.setIdProduto(PrincipalFormController.getProduto().getIdProduto());
			mov.setIdUsuario(LoginFormController.getLogado().getIdUsuario());
			mov.setTipo(comboBoxTipoDeSaida.getSelectionModel().getSelectedItem());
			mov.setValorMovimento(Integer.valueOf(txtQuantidade.getText()));
			mov.setObservacoesMovimentacao(txtAreaObservacoes.getText());
			mov.setQuantidadeAnterior(Integer.valueOf(labelEstoqueAtual.getText()));

		}

		return mov;

	}

		public void updateFormData() {
		
		labelNome.setText(PrincipalFormController.getProduto().getNome());
		labelEstoqueAtual.setText(String.valueOf(PrincipalFormController.getProduto().getQuantidade()));
		
	}
	
}
