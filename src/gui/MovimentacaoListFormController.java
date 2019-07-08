package gui;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Movimentacao;
import model.services.MovimentacaoService;

public class MovimentacaoListFormController implements Initializable {

	private MovimentacaoService service;

	private static ObservableList<Movimentacao> listaMovimentações;

	@FXML
	public TableView<Movimentacao> tableViewMovimentacao;

	@FXML
	private TableColumn<Movimentacao, Integer> tableColumnId;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnProduto;

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

	public void setMovimentacaoService(MovimentacaoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	//DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
	//Date hoje = new Date(formatBR.format("dataDaTransacao"));
	
	//Date hoje = new Date(formatBR.format("dataDaTransacao"));
	
	String dataBr = "";
	
	
	
	//java.util.Date dataUtil = dcDataEntrada.getDate();

	DateFormat formatBR = new SimpleDateFormat("dd/MM/YYYY");
	//Date hoje = new Date((Long.getLong(formatBR.format("dataDaTransacao"))));

	private void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idMovimentacao"));
		tableColumnProduto
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getProduto().getNome()));
		tableColumnUsuario
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getUsuario().getNome()));
		tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		tableColumnEstoqueAnterior.setCellValueFactory(new PropertyValueFactory<>("quantidadeAnterior"));
		tableColumnValorDoMovimentacao.setCellValueFactory(new PropertyValueFactory<>("valorMovimento"));
		tableColumnEstoqueAtual.setCellValueFactory(
				(param) -> new SimpleStringProperty(String.valueOf(param.getValue().getProduto().getQuantidade())));
		tableColumnObservacoes.setCellValueFactory(new PropertyValueFactory<>("observacoesMovimentacao"));
		//tableColumnDataDaMovimentacao.setCellValueFactory(new PropertyValueFactory<>("dataDaTransacao"));

		tableColumnDataDaMovimentacao.setCellValueFactory((param) -> new SimpleStringProperty(dataBr = formatBR.format("dataDaTransacao").toString()));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewMovimentacao.prefHeightProperty().bind(stage.heightProperty());

		service = new MovimentacaoService();
		updateTableView();
	}

	public void updateTableView() {

		if (service == null) {
			throw new IllegalStateException("Serviço está nulo");
		}

		System.out.println(service.findAll());
		listaMovimentações = FXCollections.observableArrayList(service.findAll());
		tableViewMovimentacao.setItems(listaMovimentações);
	}

}
