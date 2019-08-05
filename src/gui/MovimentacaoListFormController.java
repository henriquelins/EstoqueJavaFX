package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Movimentacao;
import model.entities.Setor;
import model.services.MovimentacaoService;
import model.services.SetorService;

public class MovimentacaoListFormController implements Initializable {

	private MovimentacaoService service;

	private static ObservableList<Movimentacao> listaMovimentação;

	@FXML
	public TableView<Movimentacao> tableViewMovimentacao;

	@FXML
	private TextField txtPesquisar;

	@FXML
	private Button btPesquisar;

	@FXML
	private TableColumn<Movimentacao, Integer> tableColumnId;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnProduto;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnSetor;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnUsuario;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnTipo;

	@FXML
	private TableColumn<Movimentacao, Integer> tableColumnEstoqueAnterior;

	@FXML
	private TableColumn<Movimentacao, Integer> tableColumnValorDoMovimentacao;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnEstoqueAtual;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnObservacoes;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnDataDaMovimentacao;

	static String pesquisarProduto;

	static String pesquisarSetor;

	@FXML
	private ComboBox<String> cbPesquisaSetor;

	public void setMovimentacaoService(MovimentacaoService service) {

		this.service = service;

	}

	@FXML
	public void onBtPesquisarAction(ActionEvent event) {

		setPesquisarProduto("");
		setPesquisarSetor("");

		if (txtPesquisar.getText() == null || txtPesquisar.getText().trim().equals("")) {

			Alerts.showAlert("Pesquisar produto", "Campo obrigatório para pesquisar", "Digite o nome do produto",
					AlertType.ERROR);

		} else {

			setPesquisarProduto(txtPesquisar.getText());

			listaPesquisa();

			if (listaMovimentação.isEmpty() == true) {

				Alerts.showAlert("Pesquisar produto", "Lista vazia", "O produto não foi encontrado", AlertType.ERROR);

				updateTableView();
				updatePesquisa();

			} else {

				updateTableView();
				updatePesquisa();

			}

		}

	}

	@FXML
	public void onCbPesquisarAction(ActionEvent event) {

		setPesquisarProduto("");
		setPesquisarSetor("");

		if (cbPesquisaSetor.getSelectionModel().getSelectedItem() == null
				|| cbPesquisaSetor.getSelectionModel().getSelectedItem().equals("Selecione o setor...")) {

			Alerts.showAlert("Pesquisar setor", "Campo obrigatório para pesquisar", "Selecione o setor",
					AlertType.ERROR);

		} else {

			setPesquisarSetor(cbPesquisaSetor.getSelectionModel().getSelectedItem());

			if (getPesquisarSetor().equals("Todos")) {

				listaTodos();

				if (listaMovimentação.isEmpty() == true) {

					Alerts.showAlert("Pesquisar setor", "Lista vazia", "Produtos não encontrados", AlertType.ERROR);

					updateTableView();
					updatePesquisa();

				} else {

					updateTableView();
					updatePesquisa();

				}

			} else {

				listaCbSetor();

				if (listaMovimentação.isEmpty() == true) {

					Alerts.showAlert("Pesquisar setor", "Lista vazia", "O setor não foi encontrado", AlertType.ERROR);

					updateTableView();
					updatePesquisa();

				} else {

					updateTableView();
					updatePesquisa();

				}

			}

		}

		cbPesquisaSetor.setPromptText("Selecione o setor...");

	}

	private List<String> listaSetor() {

		SetorService setorService = new SetorService();
		List<String> listaSetor = new ArrayList<>();

		listaSetor.add("Todos");

		for (Setor setor : setorService.findAllNome()) {

			listaSetor.add(setor.getNome());
		}

		return listaSetor;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		cbPesquisaSetor.setItems(FXCollections.observableArrayList(listaSetor()));

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idMovimentacao"));

		tableColumnProduto
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getProduto().getNome()));

		tableColumnSetor
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getProduto().getSetor()));

		tableColumnUsuario
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getUsuario().getNome()));

		tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		tableColumnEstoqueAnterior.setCellValueFactory(new PropertyValueFactory<>("quantidadeAnterior"));
		tableColumnValorDoMovimentacao.setCellValueFactory(new PropertyValueFactory<>("valorMovimento"));
		tableColumnEstoqueAtual.setCellValueFactory(new PropertyValueFactory<>("estoqueAtual"));
		tableColumnObservacoes.setCellValueFactory(new PropertyValueFactory<>("observacoesMovimentacao"));

		DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");

		tableColumnDataDaMovimentacao.setCellValueFactory(
				(param) -> new SimpleStringProperty(formatBR.format(param.getValue().getDataDaTransacao())));

		service = new MovimentacaoService();

	}

	public void updateTableView() {

		if (service == null) {

			throw new IllegalStateException("Service está nulo");

		}

		try {

			if (!getPesquisarProduto().equals("")) {

				listaPesquisa();

			} else if (!getPesquisarSetor().equals("")) {

				if (getPesquisarSetor().equals("Todos")) {

					listaTodos();

				} else {

					listaCbSetor();

				}

			}

		} catch (NullPointerException e) {

			listaTodos();

		}

		tableViewMovimentacao.setItems(listaMovimentação);

	}

	public void updatePesquisa() {

		txtPesquisar.setText("");
		txtPesquisar.requestFocus();

	}

	public static String getPesquisarProduto() {
		return pesquisarProduto;
	}

	public static void setPesquisarProduto(String pesquisarProduto) {
		MovimentacaoListFormController.pesquisarProduto = pesquisarProduto;
	}

	public static String getPesquisarSetor() {
		return pesquisarSetor;
	}

	public static void setPesquisarSetor(String pesquisarSetor) {
		MovimentacaoListFormController.pesquisarSetor = pesquisarSetor;
	}

	private List<Movimentacao> listaPesquisa() {

		return listaMovimentação = FXCollections.observableArrayList(service.PesquisarNomeProduto(pesquisarProduto));

	}

	private List<Movimentacao> listaTodos() {

		return listaMovimentação = FXCollections.observableArrayList(service.findAll());

	}

	private List<Movimentacao> listaCbSetor() {

		return listaMovimentação = FXCollections.observableArrayList(service.PesquisarNomeSetor(pesquisarSetor));

	}

}
