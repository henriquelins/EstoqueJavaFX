package gui;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Movimentacao;
import model.services.MovimentacaoService;

public class MovimentacaoListFormController implements Initializable {

	MovimentacaoService service;

	private static ObservableList<Movimentacao> listaMovimenta��es;

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
	private TableColumn<Movimentacao, Integer>  tableColumnValorDoMovimentacao;
	
	@FXML
	private TableColumn<Movimentacao, Integer> tableColumnEstoqueAtual;

	@FXML
	private TableColumn<Movimentacao, String> tableColumnObservacoes;

	@FXML
	private TableColumn<Movimentacao, Date> tableColumnDataDaMovimentacao;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("Produto"));
		tableColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("Usu�rio"));
		tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
		tableColumnEstoqueAnterior.setCellValueFactory(new PropertyValueFactory<>("Estoque anterior"));
		tableColumnValorDoMovimentacao.setCellValueFactory(new PropertyValueFactory<>("Valor da movimenta��o"));
		tableColumnEstoqueAnterior.setCellValueFactory(new PropertyValueFactory<>("Estoque atual"));
		tableColumnObservacoes.setCellValueFactory(new PropertyValueFactory<>("Observa��es"));
		tableColumnDataDaMovimentacao.setCellValueFactory(new PropertyValueFactory<>("Data da movimenta��o"));
	
		service = new MovimentacaoService();

		updateTableView();
	}

	

	public void updateTableView() {

		if (service == null) {
			throw new IllegalStateException("Servi�o est� nulo");
		}
	 
		List<Movimentacao> listaMovimentacao = service.findAll();
		listaMovimenta��es = FXCollections.observableArrayList(listaMovimentacao);
		tableViewMovimentacao.setItems(listaMovimenta��es);
	}

	
}
