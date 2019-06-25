package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Produto;
import model.entities.enums.Categoria;
import model.entities.enums.Setor;

public class PrincipalFormController implements Initializable {
	
	Produto produto = new Produto();	
	
	@FXML
	private MenuItem menuItemUsuario;

	@FXML
	private MenuItem menuItemProduto;

	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public TableView<Produto> tableViewProduto;

	@FXML
	private TableColumn<Produto, Integer> tableColumnId;

	@FXML
	private TableColumn<Produto, String> tableColumnNome;

	@FXML
	private TableColumn<Produto, String> tableColumnDescricao;
	
	@FXML
	private TableColumn<Produto, String> tableColumnSetor;
	
	@FXML
	private TableColumn<Produto, String> tableColumnCategoria;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumnQuantidade;

	@FXML
	private TableColumn<Produto, Produto> tableColumnMOVIMENTACAO;
	
	@FXML
	private TableColumn<Produto, Produto> tableColumnEDIT;

	@FXML
	private TableColumn<Produto, Produto> tableColumnREMOVE;

	@FXML
	private Button btNovo;
	
	@FXML
	private Label labelLogado;

	@FXML
	private Button btPesquisar;

	@FXML
	public void onMenuItemUsuario(ActionEvent event) {

		createUsuarioDialogForm("/gui/UsuarioNovoView.fxml");

	}

	@FXML
	public void onMenuItemProduto(ActionEvent event) {

		createProdutoDialogForm("/gui/ProdutoNovoView.fxml");

	}

	@FXML
	public void onMenuItemSobre(ActionEvent event) {

		createSobreDialogForm("/gui/SobreView.fxml");

	}

	@FXML
	public void onBtNovoAction(ActionEvent event) {

		createProdutoDialogForm("/gui/ProdutoNovoView.fxml");

	}

	@FXML
	public void onBtPesquisarAction(ActionEvent event) {
		Alerts.showAlert("Button Pesquisar", "Não implementado", "onBtPesquisarAction", AlertType.ERROR);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initializeNodes();
		
	}

	private void initializeNodes() {
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColumnSetor.setCellValueFactory(new PropertyValueFactory<>("Setor"));
		tableColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));

		updateTableView();

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty());

		labelLogado.setText(LoginFormController.LogadoToString());

	}
	
	
	private ObservableList<Produto> listaProdutos() {
		return FXCollections.observableArrayList(
				new Produto(1,"Tinta black", "Tinta pigmentada",Setor.CRACHAS.toString(), Categoria.TINTAS_CRACHAS.toString(),2),
				new Produto(2,"Protetor rígido", "Protetor para crachá transparente",Setor.CRACHAS.toString(),Categoria.INSUMOS_CRACHAS.toString(),20),
				new Produto(3,"Presilhas", "Presilhas leitosas para crachás",Setor.CRACHAS.toString(), Categoria.INSUMOS_CRACHAS.toString(),100));
	}

	public void updateTableView() {

		tableViewProduto.setItems(listaProdutos());
		initMovimentacaoButton();
		initEditButton();
		initRemoveButton();

	}

	/*
	 * public void onDataChanged() { updateTableView(); }
	 */

	/*private void createLoginForm(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Main.setDialogScene(new Scene(pane));

			Stage loginStage = new Stage();
			loginStage.setTitle("Login");
			loginStage.setScene(Main.getDialogScene());
			loginStage.setResizable(false);
			loginStage.initModality(Modality.APPLICATION_MODAL);
			loginStage.initOwner(null);
			loginStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Login", e.getMessage(), AlertType.ERROR);

		}

	}*/

	private void createProdutoDialogForm(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Main.setDialogScene(new Scene(pane));

			Stage produtoStage = new Stage();
			produtoStage.setTitle("Produtos");
			produtoStage.setScene(Main.getDialogScene());
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Produtos", e.getMessage(), AlertType.ERROR);

		}

	}

	private void createUsuarioDialogForm(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Main.setDialogScene(new Scene(pane));

			Stage produtoStage = new Stage();
			produtoStage.setTitle("Usuários");
			produtoStage.setScene(Main.getDialogScene());
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.showAndWait();
			
		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Usuários", e.getMessage(), AlertType.ERROR);

		}

	}

	private void createSobreDialogForm(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Main.setDialogScene(new Scene(pane));

			Stage produtoStage = new Stage();
			produtoStage.setTitle("Sobre o aplicativo");
			produtoStage.setScene(Main.getDialogScene());
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Sobre o aplicativo", e.getMessage(),
					AlertType.ERROR);

		}

	}
	
	private void initMovimentacaoButton() {
		tableColumnMOVIMENTACAO.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnMOVIMENTACAO.setCellFactory(param -> new TableCell<Produto, Produto>() {

			private final Button button = new Button("Movimentação");

			@Override
			protected void updateItem(Produto prod, boolean empty) {
				super.updateItem(prod, empty);
				if (prod == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createProdutoDialogForm("/gui/MovimentacaoView.fxml"));
			}

		});
	}


	private void initEditButton() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Produto, Produto>() {

			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Produto prod, boolean empty) {
				super.updateItem(prod, empty);
				if (prod == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createProdutoDialogForm("/gui/ProdutoEditarView.fxml"));
			}

		});
	}

	private void initRemoveButton() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Produto, Produto>() {

			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Produto prod, boolean empty) {
				super.updateItem(prod, empty);
				if (prod == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(prod));
			}
		});
	}
	
	
	protected void removeEntity(Produto obj) {

		Alerts.showAlert("removeEntity", "Não implementado", "Button Exit", AlertType.ERROR);
	}

}
