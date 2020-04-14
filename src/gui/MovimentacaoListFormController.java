package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import exportarXLS.ExportarListaMovimentacaoDoProdutoXLS;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import jxl.write.WriteException;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.services.LogSegurancaService;
import model.services.MovimentacaoService;

public class MovimentacaoListFormController implements Initializable {

	private MovimentacaoService movimentacaoService;

	private static ObservableList<Movimentacao> listaMovimentação;

	@FXML
	public TableView<Movimentacao> tableViewMovimentacao;

	@FXML
	private Button buttonPesquisar;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnIndex;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnTipo;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnEstoqueAnterior;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnValorDoMovimentacao;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnEstoqueAtual;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnObservacoes;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnUsuario;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnDataDaMovimentacao;

	static String pesquisarProduto;

	static String pesquisarSetor;

	@FXML
	private Label labelProdutoMovimentado;

	@FXML
	private DatePicker datePickerDataInicial;

	@FXML
	private DatePicker datePickerDataFinal;

	@FXML
	private Button buttonExportarParaExcel;

	private Produto produto;

	public void setMovimentacaoService(MovimentacaoService movimentacaoService) {

		this.movimentacaoService = movimentacaoService;

	}

	@FXML
	public void onBtPesquisarAction(ActionEvent event) {

		limparTable();

		if (datePickerDataInicial.getValue() == null || datePickerDataFinal.getValue() == null) {

			Alerts.showAlert("Ver lançamento", "Campo obrigatório", "Digite ou selecione a data", AlertType.ERROR);

		} else if (datePickerDataFinal.getValue().isBefore(datePickerDataInicial.getValue())) {

			Alerts.showAlert("Ver lançamento", "Campo obrigatório", "A data final não pode ser maior que a inicial",
					AlertType.ERROR);

		} else {

			new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome().toUpperCase(),
					"Movimentação do produto: " + produto.getNome().toUpperCase() + " - "
							+ Constraints.setLocalDateToDateSql(datePickerDataInicial.getValue()) + " até "
							+ Constraints.setLocalDateToDateSql(datePickerDataFinal.getValue()));

			listaPesquisa(datePickerDataInicial, datePickerDataFinal, getProduto().getIdProduto());

			if (listaMovimentação.isEmpty() == true) {

				Alerts.showAlert("Ver lançamento", "Lista vazia", "Selecione outras datas para pesquisar",
						AlertType.ERROR);

				limparDate();

			} else {

				updateTableView();

			}

		}

	}

	@FXML
	public void onBtExportarExcelAction(ActionEvent event) {

		ExportarListaMovimentacaoDoProdutoXLS exportarXLS = new ExportarListaMovimentacaoDoProdutoXLS();

		String caminho = "C:/temp/listaMovimentacaoDoProduto.xls";

		try {

			if (tableViewMovimentacao.getItems().isEmpty() != true) {

				try {

					exportarXLS.exportarListaMovimentacaoDoProdutoXLS(caminho, listaMovimentação,
							produto.getNome().toUpperCase(), datePickerDataInicial.getValue(),
							datePickerDataFinal.getValue());

					new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome().toUpperCase(),
							Strings.getLogMessage004());

				} catch (WriteException e) {

					Alerts.showAlert("Exportar lista", "Erro ao criar o arquivo!", "Exportar para Excel ",
							AlertType.ERROR);

				} catch (IOException e) {

					Alerts.showAlert("Exportar lista", "Feche o arquivo primeiro!", "Exportar para Excel ",
							AlertType.ERROR);

				}

			} else {

				Alerts.showAlert("Exportar lista", "Lista vazia", "Exportar para Excel ", AlertType.ERROR);

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Exportar lista", "Lista vazia " + e.getLocalizedMessage(), "Exportar para Excel ",
					AlertType.ERROR);

		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewMovimentacao.getItems().indexOf(column.getValue()) + 1)));

		tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		tableColumnEstoqueAnterior.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.tresDigitos(param.getValue().getQuantidadeAnterior())));
		tableColumnValorDoMovimentacao.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.tresDigitos(param.getValue().getValorMovimento())));
		tableColumnEstoqueAtual.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.tresDigitos(param.getValue().getEstoqueAtual())));
		tableColumnObservacoes.setCellValueFactory(
				(param) -> new SimpleStringProperty(param.getValue().getObservacoesMovimentacao().toUpperCase()));

		DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");

		tableColumnUsuario.setCellValueFactory(
				(param) -> new SimpleStringProperty(param.getValue().getUsuario().getNome().toUpperCase()));

		tableColumnDataDaMovimentacao.setCellValueFactory(
				(param) -> new SimpleStringProperty(formatBR.format(param.getValue().getDataDaTransacao())));

		movimentacaoService = new MovimentacaoService();

	}

	public void updateTableView() {

		if (movimentacaoService == null) {

			throw new IllegalStateException("Service está nulo");

		} else {

			tableViewMovimentacao.setItems(listaMovimentação);

		}

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

	private List<Movimentacao> listaPesquisa(DatePicker datePickerDataInicial, DatePicker datePickerDataFinal,
			int id_produto) {

		return listaMovimentação = FXCollections.observableArrayList(
				movimentacaoService.verMovimentacao(Constraints.setLocalDateToDateSql(datePickerDataInicial.getValue()),
						Constraints.setLocalDateToDateSql(datePickerDataFinal.getValue()), id_produto));

	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void carregarCampos(Produto produto) {

		if (movimentacaoService != null) {

			labelProdutoMovimentado.setText("Movimentação do Produto: " + produto.getNome().toUpperCase());

		} else {

			labelProdutoMovimentado.setText("Movimentação");

		}

		setProduto(produto);

	}

	public void limparDate() {

		datePickerDataInicial.setValue(null);
		datePickerDataFinal.setValue(null);

	}

	public void limparTable() {

		tableViewMovimentacao.setItems(null);

	}

}
