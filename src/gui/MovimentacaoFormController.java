package gui;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.services.MovimentacaoService;

public class MovimentacaoFormController implements Initializable, DataChangeListener {

	private PrincipalFormController principalController;

	// Lista de ouvintes para receber alguma modificação
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	MovimentacaoService movimentacaoService;

	Movimentacao movimentacao;

	Produto produto;

	@FXML
	private TextField textFieldNome;

	@FXML
	private TextField textFieldEstoqueAtual;

	@FXML
	private ComboBox<String> comboBoxTipoDeSaida;

	@FXML
	private TextField textFieldQuantidade;

	@FXML
	private TextArea txtAreaObservacoes;

	@FXML
	private Button buttonSalvar;

	@FXML
	private Button buttonVoltar;
	
	@FXML
	public void onBtnSalvarMovimentacaoAction(ActionEvent event) {

		setMovimentacao(getFormData());

		if (movimentacao != null) {

			movimentacaoService.movimentacaoSaidaOuEntrada(movimentacao);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();

		} else {
			
			Alerts.showAlert("Movimentação do Produto", "Erro ao abrir o objeto movimento", "Objeto nulo",
					AlertType.INFORMATION);
			
		}
	}

	@FXML
	public void onBtnVoltarMovimentacaoAction(ActionEvent event) {

		Utils.currentStage(event).close();

	}

	// Adiciona a lista um ouvinte, quando há uma modificação
	public void subscribeDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);
	}

	// Função que faz a atualização da tabela
	private void notifyDataChangeListeners() {

		for (DataChangeListener listener : dataChangeListeners) {

			listener.onDataChanged();

		}

	}

	public void setMovimentacaoService(MovimentacaoService movimentacaoService) {

		this.movimentacaoService = movimentacaoService;

	}

	public void setMovimentacao(Movimentacao movimentacao) {

		this.movimentacao = movimentacao;

	}

	public void setProduto(Produto produto) {

		this.produto = produto;

	}

	private List<String> listaTipos() {

		List<String> listaTipos = new ArrayList<>();
		listaTipos.add("ENTRADA DE PRODUTOS (+)");
		listaTipos.add("SAÍDA DE PRODUTOS (-)");

		return listaTipos;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();
	}

	private void initializeNodes() {

		comboBoxTipoDeSaida.setItems(FXCollections.observableArrayList(listaTipos()));

		textFieldNome.setText(PrincipalFormController.getProduto().getNome().toUpperCase());
		textFieldEstoqueAtual
				.setText(Constraints.tresDigitos(PrincipalFormController.getProduto().getQuantidade()) + " Unidade(s)");

		movimentacaoService = new MovimentacaoService();
		movimentacao = new Movimentacao();
		produto = new Produto();

		Constraints.setTextFieldInteger(textFieldQuantidade);

		updateFormData();

	}

	private Movimentacao getFormData() {

		Movimentacao mov = new Movimentacao();

		if (comboBoxTipoDeSaida.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Movimentação do Produto", null, "Selecione o tipo de movimentação",
					AlertType.INFORMATION);

			comboBoxTipoDeSaida.requestFocus();

			mov = null;

		} else if (textFieldQuantidade.getText() == null || textFieldQuantidade.getText().trim().equals("")) {

			Alerts.showAlert("Saída Produto", null, "Digite a quantidade do produto", AlertType.INFORMATION);

			textFieldQuantidade.requestFocus();

			mov = null;

		} else if (txtAreaObservacoes.getText() == null || txtAreaObservacoes.getText().trim().equals("")) {

			Alerts.showAlert("Saída Produto", null, "Digite uma observação sobre o produto", AlertType.INFORMATION);

			txtAreaObservacoes.requestFocus();

			mov = null;

		} else {

			Date hoje = new Date(System.currentTimeMillis());
			java.sql.Date data = new java.sql.Date(hoje.getTime());

			mov.setProduto(PrincipalFormController.getProduto());
			mov.setUsuario(LoginFormController.getLogado());
			mov.setTipo(String.valueOf(comboBoxTipoDeSaida.getSelectionModel().getSelectedItem().toUpperCase()));
			mov.setValorMovimento(Integer.valueOf(textFieldQuantidade.getText()));
			mov.setObservacoesMovimentacao(txtAreaObservacoes.getText().toUpperCase());
			mov.setQuantidadeAnterior(Integer.valueOf(textFieldEstoqueAtual.getText().replaceAll("\\D", "")));
			mov.setDataDaTransacao(data);

		}

		return mov;

	}

	public void updateFormData() {

		textFieldNome.setText(PrincipalFormController.getProduto().getNome().toUpperCase());
		textFieldEstoqueAtual
				.setText(Constraints.tresDigitos(PrincipalFormController.getProduto().getQuantidade()) + " Unidade(s)");

	}

	public void updateFormData2() {

		textFieldNome.setText(PrincipalFormController.getProduto().getNome().toUpperCase());
		textFieldEstoqueAtual
				.setText(Constraints.tresDigitos(PrincipalFormController.getProduto().getQuantidade()) + " Unidade(s)");
		textFieldQuantidade.setText("");
		textFieldQuantidade.requestFocus();
		txtAreaObservacoes.setText("");
		comboBoxTipoDeSaida.setValue("SELECIONE...");

	}

	@Override
	public void onDataChanged() {

		principalController.updateTableView();

	}

}
