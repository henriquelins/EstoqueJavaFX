package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.services.MovimentacaoService;
import model.services.ProdutoService;

public class PrincipalFormController implements Initializable, DataChangeListener {

	private static Produto produto;

	private static ObservableList<Produto> listaProdutos;

	private ProdutoService service;

	private Movimentacao movimentacao;

	@FXML
	private MenuItem menuItemUsuario;

	@FXML
	private MenuItem menuItemProduto;

	@FXML
	private MenuItem menuItemSetor;

	@FXML
	private MenuItem menuItemCategoria;

	@FXML
	private MenuItem menuItemSobre;

	@FXML
	private MenuItem menuItemMovimentacao;

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
	public void onMenuItemMovimentacao(ActionEvent event) {
		createMovimentacaoListDialogForm("/gui/MovimentacaoListView.fxml");
	}

	@FXML
	public void onMenuItemUsuario(ActionEvent event) {
		createUsuarioDialogForm("/gui/UsuarioView.fxml");
	}

	@FXML
	public void onMenuItemProduto(ActionEvent event) {
		createProdutoNovoDialogForm("/gui/ProdutoNovoView.fxml");
	}

	@FXML
	public void onMenuItemSetor(ActionEvent event) {
		createSetorNovoDialogForm("/gui/SetorNovoView.fxml");
	}

	@FXML
	public void onMenuItemCategoria(ActionEvent event) {
		createCategoriaNovoDialogForm("/gui/CategoriaNovoView.fxml");
	}

	@FXML
	public void onMenuItemSobre(ActionEvent event) {
		createSobreDialogForm("/gui/SobreView.fxml");
	}

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		createProdutoNovoDialogForm("/gui/ProdutoNovoView.fxml");
	}

	@FXML
	public void onBtPesquisarAction(ActionEvent event) {
		Alerts.showAlert("Button Pesquisar", "Não implementado", "onBtPesquisarAction", AlertType.ERROR);
	}

	public static void setProduto(Produto produto) {
		PrincipalFormController.produto = produto;
	}

	public static Produto getProduto() {
		return produto;
	}

	public void setProdutoService(ProdutoService service) {
		this.service = service;
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

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty());

		labelLogado.setText(LoginFormController.usuarioLogado());
		service = new ProdutoService();
		movimentacao = new Movimentacao();
		updateTableView();
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço nulo");
		}

		listaProdutos = FXCollections.observableArrayList(service.findAll());
		tableViewProduto.setItems(listaProdutos);
		initMovimentacaoButton();
		initEditButton();
		initRemoveButton();
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void createSetorNovoDialogForm(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
					
			Stage produtoStage = new Stage();
			produtoStage.setTitle("Setor");
			produtoStage.setScene(new Scene(pane));
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Setor", e.getLocalizedMessage(), AlertType.ERROR);
		}
	}

	private void createCategoriaNovoDialogForm(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
					
			Stage produtoStage = new Stage();
			produtoStage.setTitle("Categoria");
			produtoStage.setScene(new Scene(pane));
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Categoria", e.getLocalizedMessage(),
					AlertType.ERROR);
		}

	}

	private void createMovimentacaoListDialogForm(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			ScrollPane scrollPane = loader.load();

			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			Scene mainScene = new Scene(scrollPane);
			// mainScene.getStylesheets().add(Main.style);

			Main.setMainScene(mainScene);
			Stage produtoStage = new Stage();
			produtoStage.setTitle("Movimetação");
			produtoStage.setScene(Main.getMainScene());
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			// produtoStage.initOwner(null);
			produtoStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Movimentação", e.getLocalizedMessage(),
					AlertType.ERROR);
		}

	}

	private void createProdutoEditarDialogForm(Produto prod, String absoluteName) {
		try {
			setProduto(prod);

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProdutoEditarFormController controller = loader.getController();
			controller.setProduto(produto);
			controller.setProdutoService(new ProdutoService());
			controller.subscribeDataChangeListener(this);

			Scene mainScene = new Scene(pane);
			// mainScene.getStylesheets().add(Main.style);

			Main.setDialogScene(mainScene);
			Stage produtoStage = new Stage();
			produtoStage.setTitle("Editar Produto");
			produtoStage.setScene(Main.getDialogScene());
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a tela editar produtos", e.getMessage(),
					AlertType.ERROR);
		}
	}

	private void createProdutoNovoDialogForm(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProdutoNovoFormController controller = loader.getController();
			controller.setProduto(produto);
			controller.setProdutoService(new ProdutoService());
			controller.subscribeDataChangeListener(this);

			Scene mainScene = new Scene(pane);
			// mainScene.getStylesheets().add(Main.style);

			Main.setDialogScene(mainScene);
			Stage produtoStage = new Stage();
			produtoStage.setTitle("Novo Produto");
			produtoStage.setScene(Main.getDialogScene());
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);
			produtoStage.showAndWait();
		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Produtos", e.getMessage(), AlertType.ERROR);

		}
	}

	private void createMovimentacaoDialogForm(Produto prod, String absoluteName) {
		try {
			setProduto(prod);

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			MovimentacaoFormController controller = loader.getController();
			controller.setProduto(produto);
			controller.setMovimentacao(movimentacao);
			controller.setMovimentacaoService(new MovimentacaoService());
			controller.subscribeDataChangeListener(this);

			Scene mainScene = new Scene(pane);
			// mainScene.getStylesheets().add(Main.style);

			Main.setDialogScene(mainScene);
			Stage produtoStage = new Stage();
			produtoStage.setTitle("Movimentação de Produtos");
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
			ScrollPane scrollPane = loader.load();

			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			Scene mainScene = new Scene(scrollPane);
			// mainScene.getStylesheets().add(Main.style);

			Main.setMainScene(mainScene);
			Stage produtoStage = new Stage();
			produtoStage.setTitle("Usuários");
			produtoStage.setScene(Main.getMainScene());
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

			Scene mainScene = new Scene(pane);
			// mainScene.getStylesheets().add(Main.style);

			Main.setDialogScene(mainScene);
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
				button.setOnAction(event -> createMovimentacaoDialogForm(prod, "/gui/MovimentacaoView.fxml"));
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
				button.setOnAction(event -> createProdutoEditarDialogForm(prod, "/gui/ProdutoEditarView.fxml"));
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

	private void removeEntity(Produto prod) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Você quer deletar o produto?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalThreadStateException("Service está nulo");
			}
			try {
				service.remove(prod);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
