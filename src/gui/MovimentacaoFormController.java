package gui;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.services.MovimentacaoService;
import model.services.ProdutoService;

public class MovimentacaoFormController implements Initializable, DataChangeListener {

	private PrincipalFormController principalController;

	// Lista de ouvintes para receber alguma modificação
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	MovimentacaoService movimentacaoService;

	Movimentacao movimentacao;

	Produto produto;

	@FXML
	private Label labelNome;

	@FXML
	private Label labelEstoqueAtual;

	@FXML
	private ComboBox<String> comboBoxTipoDeSaida;

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
			ProdutoService produtoService = new ProdutoService();
			produtoService.findById(PrincipalFormController.getProduto().getIdProduto());
			updateFormData2();
			notifyDataChangeListeners();

		}
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
		listaTipos.add("Entrada de produtos (+)");
		listaTipos.add("Saída de produtos (-)");
	
		return listaTipos;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();
	}

	private void initializeNodes() {

		comboBoxTipoDeSaida.setItems(FXCollections.observableArrayList(listaTipos()));

		labelNome.setText(PrincipalFormController.getProduto().getNome());
		labelEstoqueAtual.setText(String.valueOf(PrincipalFormController.getProduto().getQuantidade()));

		movimentacaoService = new MovimentacaoService();
		movimentacao = new Movimentacao();
		produto = new Produto();

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

			Date hoje = new Date(System.currentTimeMillis());
			java.sql.Date data = new java.sql.Date(hoje.getTime());

			// DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
			// String dataBr = formatBR.format(data);

			mov.setProduto(PrincipalFormController.getProduto());
			mov.setUsuario(LoginFormController.getLogado());
			mov.setTipo(String.valueOf(comboBoxTipoDeSaida.getSelectionModel().getSelectedItem()));
			mov.setValorMovimento(Integer.valueOf(txtQuantidade.getText()));
			mov.setObservacoesMovimentacao(txtAreaObservacoes.getText());
			mov.setQuantidadeAnterior(Integer.valueOf(labelEstoqueAtual.getText()));
			mov.setDataDaTransacao(data);

		}

		return mov;

	}

	public void updateFormData() {

		labelNome.setText(PrincipalFormController.getProduto().getNome());
		labelEstoqueAtual.setText(String.valueOf(PrincipalFormController.getProduto().getQuantidade()));

	}

	public void updateFormData2() {

		labelNome.setText(PrincipalFormController.getProduto().getNome());
		labelEstoqueAtual.setText(String.valueOf(PrincipalFormController.getProduto().getQuantidade()));
		txtQuantidade.setText("");
		txtQuantidade.requestFocus();
		txtAreaObservacoes.setText("");
		comboBoxTipoDeSaida.setValue("Selecione...");

	}

	@Override
	public void onDataChanged() {

		principalController.updateTableView();

	}

}
