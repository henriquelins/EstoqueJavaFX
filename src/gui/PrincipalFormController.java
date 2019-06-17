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
import javafx.scene.control.ComboBox;
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

public class PrincipalFormController implements Initializable {

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
	private TableColumn<Produto, String> tableColumnCod;

	@FXML
	private TableColumn<Produto, String> tableColumnNome;

	@FXML
	private TableColumn<Produto, String> tableColumnDescricao;

	@FXML
	private TableColumn<Produto, Integer> tableColumnQuantidade;

	@FXML
	private TableColumn<Produto, Produto> tableColumnSAIDA;
	
	@FXML
	private TableColumn<Produto, Produto> tableColumnENTRADA;

	@FXML
	private TableColumn<Produto, Produto> tableColumnEDIT;

	@FXML
	private TableColumn<Produto, Produto> tableColumnREMOVE;
	
	@FXML
	private Button btNovo;
	
	@FXML
	private Button btLogout;
	
	@FXML
	private Label labelLogado;
	
	@FXML
	private ComboBox <String> cbProduto;
	
	@FXML
	private Button btPesquisar;
	
	
	@FXML
	public void onMenuItemUsuario(ActionEvent event) {

		createUsuarioDialogForm("/gui/UsuarioView.fxml");

	}

	@FXML
	public void onMenuItemProduto(ActionEvent event) {

		createProdutoDialogForm("/gui/ProdutoView.fxml");

	}

	@FXML
	public void onMenuItemSobre(ActionEvent event) {

		createSobreDialogForm("/gui/SobreView.fxml");

	}
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
	
		 createProdutoDialogForm("/gui/ProdutoView.fxml");
		 
	}
	
	@FXML
	public void onBtLogoutAction(ActionEvent event) {
		
		 createProdutoDialogForm("/gui/LoginView.fxml");
		 System.exit(0);
		 
	}
	
	@FXML
	public void onBtPesquisarAction(ActionEvent event) {
		System.out.println("onBtPesquisarAction");
	}
	
	
	@FXML
	public void onCbProdutoAction(ActionEvent event) {

		System.out.println("onCbProduto");

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initializeNodes();

	}

	private void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
		tableColumnCod.setCellValueFactory(new PropertyValueFactory<>("Cod"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));

		updateTableView();

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty());

	}

	private ObservableList<Produto> listaProdutos() {
		return FXCollections.observableArrayList(

				new Produto(1, "001", "Garrafa", "Garrafa de coca-cola", 10),
				new Produto(2, "051", "Copo", "Copo de cerveja", 05),
				new Produto(3, "023", "Prato", "Prato de porcelana", 05));

	}

	public void updateTableView() {

		tableViewProduto.setItems(listaProdutos());
		initSaidaButtons();
		initEntradaButtons();
		initEditButtons();
		initRemoveButtons();

	}

	/*
	 * public void onDataChanged() { updateTableView(); }
	 */

	private void createProdutoDialogForm(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Scene produtoScene = new Scene(pane);

			Stage produtoStage = new Stage();
			produtoStage.setTitle("Produtos");
			produtoStage.setScene(produtoScene);
			produtoStage.setResizable(true);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Produtos", e.getMessage(), AlertType.ERROR);

		}

	}

	private void createUsuarioDialogForm(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Scene produtoScene = new Scene(pane);

			Stage produtoStage = new Stage();
			produtoStage.setTitle("Usuários");
			produtoStage.setScene(produtoScene);
			produtoStage.setResizable(true);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Usuários", e.getMessage(), AlertType.ERROR);

		}

	}

	private void createSobreDialogForm(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Scene produtoScene = new Scene(pane);

			Stage produtoStage = new Stage();
			produtoStage.setTitle("Sobre o aplicativo");
			produtoStage.setScene(produtoScene);
			produtoStage.setResizable(true);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Sobre o aplicativo", e.getMessage(),
					AlertType.ERROR);

		}

	}

	private void initEditButtons() {
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
				button.setOnAction(event -> createProdutoDialogForm("/gui/ProdutoView.fxml"));
			}

		});
	}

	private void initRemoveButtons() {
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

	private void initSaidaButtons() {
		tableColumnSAIDA.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnSAIDA.setCellFactory(param -> new TableCell<Produto, Produto>() {

			private final Button button = new Button("Saída");

			@Override
			protected void updateItem(Produto prod, boolean empty) {
				super.updateItem(prod, empty);
				if (prod == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createProdutoDialogForm("/gui/ProdutoView.fxml"));
			}

		});
	}

	private void initEntradaButtons() {
		tableColumnENTRADA.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnENTRADA.setCellFactory(param -> new TableCell<Produto, Produto>() {

			private final Button button = new Button("Entrada");

			@Override
			protected void updateItem(Produto prod, boolean empty) {
				super.updateItem(prod, empty);
				if (prod == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createProdutoDialogForm("/gui/ProdutoView.fxml"));
			}

		});
	}

	protected Object removeEntity(Produto obj) {

		return null;
	}

}
