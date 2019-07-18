package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Movimentacao;
import model.services.MovimentacaoService;

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
	
	@FXML
	public void onBtPesquisarAction(ActionEvent event) {
		
		Alerts.showAlert("Button Pesquisar", "Não implementado", "onBtPesquisarAction", AlertType.ERROR);
		
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		initializeNodes();
	}

	private void initializeNodes() {
				
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idMovimentacao"));
		
		tableColumnProduto
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getProduto().getNome()));
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
		
		updateTableView();
		
	}

	public void updateTableView() {

		if (service == null) {
			
			throw new IllegalStateException("Service está nulo");
			
		}

		listaMovimentação = FXCollections.observableArrayList(service.findAll());
		tableViewMovimentacao.setItems(listaMovimentação);
		
	}

}
