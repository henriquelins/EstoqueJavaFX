package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Acesso;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.entities.Setor;
import model.services.MovimentacaoService;
import model.services.ProdutoService;
import model.services.SetorService;

public class PrincipalFormController implements Initializable, DataChangeListener {

	private static Produto produto;

	private static ObservableList<Produto> listaProdutos;

	private ProdutoService service;

	private Movimentacao movimentacao;

	@FXML
	private MenuBar menuBarPrincipal;

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
	private TableColumn<Produto, String> tableColumnStatus;

	@FXML
	private TableColumn<Produto, Produto> tableColumnMOVIMENTACAO;

	@FXML
	private TableColumn<Produto, Produto> tableColumnEDIT;

	@FXML
	private TableColumn<Produto, Produto> tableColumnREMOVE;

	@FXML
	private TableColumn<Produto, Produto> tableColumnSHOW;

	@FXML
	private Button btNovo;

	@FXML
	private Label labelLogado;

	@FXML
	private TextField txtPesquisar;

	@FXML
	private Button btPesquisar;

	static String pesquisarProduto;

	static String pesquisarSetor;

	@FXML
	private ComboBox<String> cbPesquisaSetor;

	@FXML
	public void onMenuItemMovimentacao(ActionEvent event) {

		createMovimentacaoListDialogForm(Strings.getMovimentacaoListView());

	}

	@FXML
	public void onMenuItemUsuario(ActionEvent event) {

		createUsuarioDialogForm(Strings.getUsuarioView());

	}

	@FXML
	public void onMenuItemProduto(ActionEvent event) {

		createProdutoDialogForm(Strings.getProdutoNovoView());

	}

	@FXML
	public void onMenuItemSetor(ActionEvent event) {

		createVBoxDialogForm(Strings.getSetorNovoView());

	}

	@FXML
	public void onMenuItemCategoria(ActionEvent event) {

		createVBoxDialogForm(Strings.getCategoriaNovoView());

	}

	@FXML
	public void onMenuItemSobre(ActionEvent event) {

		createPaneDialogForm(Strings.getSobreView());

	}

	@FXML
	public void onBtNovoAction(ActionEvent event) {

		createProdutoDialogForm(Strings.getProdutoNovoView());

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

			if (listaProdutos.isEmpty() == true) {

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

				if (listaProdutos.isEmpty() == true) {

					Alerts.showAlert("Pesquisar setor", "Lista vazia", "Produtos não encontrados", AlertType.ERROR);

					updateTableView();
					updatePesquisa();

				} else {

					updateTableView();
					updatePesquisa();

				}

			} else {

				listaCbSetor();

				if (listaProdutos.isEmpty() == true) {

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

	public static void setProduto(Produto produto) {

		PrincipalFormController.produto = produto;

	}

	public static Produto getProduto() {

		return produto;

	}

	public void setProdutoService(ProdutoService service) {

		this.service = service;

	}

	private List<String> listaSetor() {

		SetorService setorService = new SetorService();
		List<String> listaSetor = new ArrayList<>();

		listaSetor.add("Todos");

		for (Setor setor : setorService.findAllId()) {

			listaSetor.add(setor.getNome());

		}

		return listaSetor;

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

		tableColumnStatus.setCellValueFactory((param) -> new SimpleStringProperty(
				status(param.getValue().getEstoqueMinimo(), param.getValue().getQuantidade())));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty());

		labelLogado.setText(LoginFormController.usuarioLogado());
		service = new ProdutoService();
		movimentacao = new Movimentacao();

		cbPesquisaSetor.setItems(FXCollections.observableArrayList(listaSetor()));

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

		tableViewProduto.setItems(listaProdutos);
		initMovimentacaoButton();
		initEditButton();
		initRemoveButton();
		initShowButton();

	}

	public void updatePesquisa() {

		txtPesquisar.setText("");
		txtPesquisar.requestFocus();

	}

	@Override
	public void onDataChanged() {

		updateTableView();

	}

	private void createVBoxDialogForm(String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
				Strings.getMovimentacaoListView());

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				VBox pane = loader.load();

				Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(Main.getDialogScene());
				produtoStage.setResizable(false);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getLocalizedMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

		}

	}

	private void createMovimentacaoListDialogForm(String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
				Strings.getMovimentacaoListView());

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				ScrollPane pane = loader.load();

				pane.setFitToHeight(true);
				pane.setFitToWidth(true);

				Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(Main.getDialogScene());
				produtoStage.setResizable(true);
				produtoStage.setMaximized(true);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getLocalizedMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

		}

	}

	private void createProdutoDialogForm(String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
				Strings.getMovimentacaoListView());

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Pane pane = loader.load();

				ProdutoNovoFormController controller = loader.getController();
				controller.setProdutoService(new ProdutoService());
				controller.subscribeDataChangeListener(this);

				Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(Main.getDialogScene());
				produtoStage.setResizable(false);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

		}

	}

	private void createProdutoEditarDialogForm(Produto prod, String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(), absoluteName);

		if (concedido == true) {

			try {

				setProduto(prod);

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Pane pane = loader.load();

				ProdutoEditarFormController controller = loader.getController();
				controller.setProdutoService(new ProdutoService());
				controller.subscribeDataChangeListener(this);

				Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(Main.getDialogScene());
				produtoStage.setResizable(false);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

		}

	}

	private void createProdutoShowDialogForm(Produto prod, String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
				Strings.getMovimentacaoListView());

		if (concedido == true) {

			try {

				setProduto(prod);

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Pane pane = loader.load();

				ProdutoShowFormController controller = loader.getController();
				controller.setProduto(produto);

				Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(Main.getDialogScene());
				produtoStage.setResizable(false);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

		}

	}

	private void createMovimentacaoDialogForm(Produto prod, String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();
		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(), absoluteName);

		if (concedido == true) {

			try {

				setProduto(prod);

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Pane pane = loader.load();

				MovimentacaoFormController controller = loader.getController();
				controller.setProduto(produto);
				controller.setMovimentacao(movimentacao);
				controller.setMovimentacaoService(new MovimentacaoService());
				controller.subscribeDataChangeListener(this);

				Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(Main.getDialogScene());
				produtoStage.setResizable(false);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

		}

	}

	private void createUsuarioDialogForm(String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
				Strings.getMovimentacaoListView());

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				BorderPane pane = loader.load();

				Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(Main.getDialogScene());
				produtoStage.setResizable(false);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

		}

	}

	private void createPaneDialogForm(String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
				Strings.getMovimentacaoListView());

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Pane pane = loader.load();

				Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(Main.getDialogScene());
				produtoStage.setResizable(false);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);

			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

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
				button.setOnAction(event -> createMovimentacaoDialogForm(prod, Strings.getMovimentacaoView()));

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
				button.setOnAction(event -> createProdutoEditarDialogForm(prod, Strings.getProdutoEditarView()));

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

		boolean concedido = false;
		Acesso acesso = new Acesso();
		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(), Strings.getExcluirProduto());

		if (concedido == true) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você quer deletar o produto " + prod.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				if (service == null) {

					throw new IllegalThreadStateException("Service está nulo");
				}

				try {

					service.remove(prod);
					listaProdutos = FXCollections.observableArrayList(service.findAll());
					updateTableView();

				} catch (DbIntegrityException e) {

					Alerts.showAlert("Erro ao remover o produto " + prod.getNome() + " !", null, e.getMessage(),
							AlertType.ERROR);

				}
			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado", null, AlertType.ERROR);

		}

	}

	private void initShowButton() {

		tableColumnSHOW.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnSHOW.setCellFactory(param -> new TableCell<Produto, Produto>() {

			private final Button button = new Button("Detalhes");

			@Override
			protected void updateItem(Produto prod, boolean empty) {

				super.updateItem(prod, empty);

				if (prod == null) {

					setGraphic(null);
					return;

				}

				setGraphic(button);
				button.setOnAction(event -> createProdutoShowDialogForm(prod, Strings.getProdutoShowView()));

			}
		});

	}

	private List<Produto> listaPesquisa() {

		listaProdutos = FXCollections.observableArrayList(service.PesquisarNomeProduto(pesquisarProduto));

		return listaProdutos;

	}

	private List<Produto> listaCbSetor() {

		String nomeSetor = cbPesquisaSetor.getSelectionModel().getSelectedItem();
		listaProdutos = FXCollections.observableArrayList(service.PesquisarNomeSetor(nomeSetor));

		return listaProdutos;
	}

	private List<Produto> listaTodos() {

		listaProdutos = FXCollections.observableArrayList(service.findAll());
		return listaProdutos;

	}

	public String getPesquisarProduto() {

		return pesquisarProduto;

	}

	public static void setPesquisarProduto(String pesquisarProduto) {

		PrincipalFormController.pesquisarProduto = pesquisarProduto;

	}

	public static String getPesquisarSetor() {

		return pesquisarSetor;

	}

	public static void setPesquisarSetor(String pesquisarSetor) {

		PrincipalFormController.pesquisarSetor = pesquisarSetor;

	}

	public String status(Integer estoque_minimo, Integer quantidade) {

		String status = "";

		if (quantidade <= estoque_minimo) {

			status = "Estoque baixo";

		} else if ((quantidade >= estoque_minimo * 3) || (quantidade <= estoque_minimo * 6)) {

			status = "Estoque normal";

		} else {

			status = "Estoque alto";

		}

		return status;

	}

}
