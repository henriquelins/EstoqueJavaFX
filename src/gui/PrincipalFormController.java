package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import application.SCE1Main;
import db.DbIntegrityException;
import exportarXLS.ExportarListaProdutosEstoqueXLS;
import gui.forms.Forms;
import gui.listeners.DataChangeListener;
import gui.util.Acesso;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jxl.write.WriteException;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.entities.Setor;
import model.entities.Usuario;
import model.services.LogSegurancaService;
import model.services.MovimentacaoService;
import model.services.ProdutoService;
import model.services.SetorService;

public class PrincipalFormController implements Initializable, DataChangeListener {

	private static List<Produto> listaLembrete;

	private static Scene PrincipalFormScene;

	private static byte[] bytes;

	private Usuario usuario;

	private static Produto produto;

	private static ObservableList<Produto> listaProdutos;

	private ProdutoService service;

	private Movimentacao movimentacao;

	@FXML
	private MenuItem menuItemLogout;

	@FXML
	private MenuItem menuItemSair;

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
	private MenuItem menuItemLog;

	@FXML
	private MenuItem menuItemBackup;

	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public TableView<Produto> tableViewProduto;

	@FXML
	private TableColumn<Produto, String> tableColumnIndex;

	@FXML
	private TableColumn<Produto, String> tableColumnCodProduto;

	@FXML
	private TableColumn<Produto, String> tableColumnNome;

	@FXML
	private TableColumn<Produto, String> tableColumnStatus;

	@FXML
	private Button buttonNovo;

	@FXML
	private Button buttonMovimentacao;

	@FXML
	private Button buttonEditar;

	@FXML
	private Button buttonExcluir;

	@FXML
	private Button buttonExportarParaExcel;

	@FXML
	private Button buttonVerMovimento;

	@FXML
	private Label labelLogado;

	@FXML
	private TextField txtPesquisar;

	@FXML
	private Button btPesquisar;

	@FXML
	private Label labelNome;

	@FXML
	private Label labelSetor;

	@FXML
	private Label labelCategoria;

	@FXML
	private Label labelSaldoAtual;

	@FXML
	private Label labelEstoqueMinimo;

	@FXML
	private Label labelDetalhes;

	@FXML
	private Label labelStatus;

	@FXML
	private ImageView imageViewProduto;

	@FXML
	private ComboBox<String> cbPesquisaSetor;

	static String pesquisarProduto;

	static String pesquisarSetor;

	// Evento clique na linha da tabela

	@FXML
	public void onMouseClickedAction(MouseEvent event) {

		if (event.getClickCount() == 1) {

			event.consume();

			produto = tableViewProduto.getSelectionModel().getSelectedItem();

			if (produto != null) {

				labelNome.setText(produto.getNome().toUpperCase());
				labelSetor.setText(produto.getSetor().toUpperCase());
				labelCategoria.setText(produto.getCategoria().toUpperCase());
				labelSaldoAtual.setText(Constraints.tresDigitos(produto.getQuantidade()) + " Unidade(s)");
				labelEstoqueMinimo.setText(Constraints.tresDigitos(produto.getEstoqueMinimo()) + " Unidade(s)");
				labelStatus.setText(status(produto.getEstoqueMinimo(), produto.getQuantidade()));
				labelDetalhes.setText(produto.getDescricao().toUpperCase());

				if (produto.getFoto() == null) {

					imageViewProduto.setImage(new Image(Strings.getSemFoto()));

				} else {

					try {

						imageViewProduto.setImage(byteToImage(produto.getFoto()));

					} catch (IOException e) {

						e.printStackTrace();
					}

				}

				new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
						"Produto: " + produto.getNome().toUpperCase());

			} else {

				apagarDetalhes();

			}

		} else {

		}

	}

	// evento menu item logout

	@FXML
	public void onMenuItemLogoutAction(ActionEvent event) {

		Utils.fecharTelaPrincipalFormAction();
		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage007());

		new Forms().loginForm(Strings.getLoginView());

	}

	// evento menu item sair

	@FXML
	public void onMenuItemSairAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage008());
		System.exit(0);

	}

	// evento menu item usuário

	@FXML
	public void onMenuItemUsuario(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage009());

		createUsuarioDialogForm(Strings.getUsuarioView());

	}

	// evento menu item produto

	@FXML
	public void onMenuItemProduto(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage012());

		createProdutoDialogForm(Strings.getProdutoNovoView());

	}

	// evento menu item setor

	@FXML
	public void onMenuItemSetor(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage006());

		createVBoxDialogForm(Strings.getSetorNovoView());

	}

	// evento menu item categoria

	@FXML
	public void onMenuItemCategoria(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage005());

		createVBoxDialogForm(Strings.getCategoriaNovoView());

	}

	// evento menu item backup

	@FXML
	public void onMenuItemBackupAction(ActionEvent event) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), Strings.getBackupBancoView());

		if (concedido == true) {

			new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage023());

			new Forms().BackupBancoForm(Strings.getBackupBancoView());

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// evento menu item log

	@FXML
	public void onMenuItemLogAction(ActionEvent event) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(usuario.getAcesso(), Strings.getLogSegurancaView());

		if (concedido == true) {

			new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage022());

			new Forms().LogSegurancaForm(Strings.getLogSegurancaView());

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	// evento menu item sobre

	@FXML
	public void onMenuItemSobre(ActionEvent event) {

		createPaneDialogForm(Strings.getSobreView());

	}

	// evento botão novo produto

	@FXML
	public void onBtNovoAction(ActionEvent event) {

		new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage012());
		createProdutoDialogForm(Strings.getProdutoNovoView());

		apagarDetalhes();
		tableViewProduto.setItems(null);

		limparCbPesquisaSetor();

	}

	@FXML
	public void onBtExportarExcelAction(ActionEvent event) {

		ExportarListaProdutosEstoqueXLS exportarXLS = new ExportarListaProdutosEstoqueXLS();

		String caminho = "C:/temp/listaProdutosEstoque.xls";

		try {

			if (tableViewProduto.getItems().isEmpty() != true) {

				try {

					exportarXLS.exportarListaClientesXLS(caminho, listaProdutos);

					new LogSegurancaService().novoLogSeguranca(usuario.getNome(), Strings.getLogMessage004());

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

			Alerts.showAlert("Exportar lista", "Lista vazia", "Exportar para Excel ", AlertType.ERROR);

		}

	}

	@FXML
	public void onBtMovimentacaoAction(ActionEvent event) {

		try {

			if (listaProdutos.isEmpty() == true) {

				Alerts.showAlert("Lista vazia ou nula", "Pesquise um produto ou selecione um setor", "Lista vazia",
						AlertType.ERROR);

			} else {

				if (produto != null) {

					boolean concedido = false;
					Acesso acesso = new Acesso();
					concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
							Strings.getMovimentacaoView());

					if (concedido == true) {

						Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
								"Você tem certeza que quer movimentar o produto " + produto.getNome().toUpperCase()
										+ " ?");

						if (result.get() == ButtonType.OK) {

							if (service == null) {

								throw new IllegalThreadStateException("Serviço nulo");

							} else {

								new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
										Strings.getLogMessage015());

								createMovimentacaoDialogForm(produto, Strings.getMovimentacaoView());
								atualizarDetalhes();

							}

						}

					} else {

						Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
								"Entre em contato com o Administrador do sistema", AlertType.ERROR);

					}

				} else {

					Alerts.showAlert("Produto não selecionado", "Selecione um produto para ver seus detalhes",
							"Produto não selecionado", AlertType.ERROR);

				}

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Exportar lista", "Lista vazia", "Movimentação", AlertType.ERROR);

		}

	}

	@FXML
	public void onBtVerMovimentoAction(ActionEvent event) {

		try {

			if (listaProdutos.isEmpty() == true) {

				Alerts.showAlert("Lista vazia ou nula", "Pesquise um produto ou selecione um setor", "Lista vazia",
						AlertType.ERROR);

			} else {

				if (produto != null) {

					boolean concedido = false;
					Acesso acesso = new Acesso();
					concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
							Strings.getMovimentacaoListView());

					if (concedido == true) {

						Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
								"Você tem certeza que quer ver a movimentação do produto "
										+ produto.getNome().toUpperCase() + " ?");

						if (result.get() == ButtonType.OK) {

							if (service == null) {

								throw new IllegalThreadStateException("Serviço nulo");

							} else {

								new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
										Strings.getLogMessage014());

								createMovimentacaoListDialogForm(usuario, produto, Strings.getMovimentacaoListView());

							}

						}

					} else {

						Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
								"Entre em contato com o Administrador do sistema", AlertType.ERROR);

					}

				} else {

					Alerts.showAlert("Produto não selecionado", "Selecione um produto para ver seus detalhes",
							"Produto não selecionado", AlertType.ERROR);

				}

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Exportar lista", "Lista vazia", "Ver Movimentação", AlertType.ERROR);

		}

	}

	@FXML
	public void onBtEditarAction(ActionEvent event) {

		try {

			if (listaProdutos.isEmpty() == true) {

				Alerts.showAlert("Lista vazia ou nula", "Pesquise um produto ou selecione um setor", "Lista vazia",
						AlertType.ERROR);

			} else {

				if (produto != null) {

					boolean concedido = false;
					Acesso acesso = new Acesso();
					concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
							Strings.getProdutoEditarView());

					if (concedido == true) {

						Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
								"Você tem certeza que quer editar o produto " + produto.getNome().toUpperCase() + " ?");

						if (result.get() == ButtonType.OK) {

							if (service == null) {

								throw new IllegalThreadStateException("Serviço nulo");

							} else {

								new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
										Strings.getLogMessage016());

								createProdutoEditarDialogForm(produto, Strings.getProdutoEditarView());

								if (pesquisarSetor.equals("")) {

									listaPesquisa();

								} else {

									if (pesquisarSetor.equals("TODOS")) {

										listaTodos();

									} else {

										listaCbSetor();

									}

								}

								atualizarDetalhes();
								updateTableView();

							}

						}

					} else {

						Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
								"Entre em contato com o Administrador do sistema", AlertType.ERROR);

					}

				} else {

					Alerts.showAlert("Produto não selecionado", "Selecione um produto para ver seus detalhes",
							"Produto não selecionado", AlertType.ERROR);

				}

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Exportar lista", "Lista vazia", "Editar", AlertType.ERROR);

		}

	}

	@FXML
	public void onBtExcluirAction(ActionEvent event) {

		try {

			if (listaProdutos.isEmpty() == true) {

				Alerts.showAlert("Lista vazia ou nula", "Pesquise um produto ou selecione um setor", "Lista vazia",
						AlertType.ERROR);

			} else {

				boolean concedido = false;
				Acesso acesso = new Acesso();
				concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(),
						Strings.getExcluirProduto());

				if (concedido == true) {

					Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
							"Você tem certeza que quer excluir o produto " + produto.getNome().toUpperCase() + " ?");

					if (result.get() == ButtonType.OK) {

						if (service == null) {

							throw new IllegalThreadStateException("Serviço nulo");

						} else {

							try {

								new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
										Strings.getLogMessage017());

								service.remove(produto);
								apagarDetalhes();
								tableViewProduto.setItems(null);

								limparCbPesquisaSetor();

							} catch (DbIntegrityException e) {

								Alerts.showAlert("Erro ao excluir o produto " + produto.getNome() + " !", null,
										e.getMessage(), AlertType.ERROR);

							}

						}
					}

				} else {

					Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
							"Entre em contato com o Administrador do sistema", AlertType.ERROR);

				}

			}

		} catch (java.lang.NullPointerException e) {

			Alerts.showAlert("Exportar lista", "Lista vazia", "Excluir ", AlertType.ERROR);

		}

	}

	@FXML
	public void onBtPesquisarAction(ActionEvent event) {

		limparCbPesquisaSetor();

		if (txtPesquisar.getText() == null || txtPesquisar.getText().trim().equals("")) {

			Alerts.showAlert("Pesquisar produto", "Campo obrigatório para pesquisar", "Digite o nome do produto",
					AlertType.ERROR);

		} else {

			setPesquisarProduto(txtPesquisar.getText());

			new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
					"Pesquisa: " + txtPesquisar.getText().toUpperCase());

			pesquisarSetor = "";

			listaPesquisa();
			apagarDetalhes();

			if (listaProdutos.isEmpty() == true) {

				Alerts.showAlert("Pesquisar produto", "Lista vazia", "O produto não foi encontrado", AlertType.ERROR);

				updateTableView();
				updatePesquisa();
				apagarDetalhes();

				listaProdutos.clear();

			} else {

				updateTableView();
				updatePesquisa();

			}

		}

	}

	/**
	 * @param event
	 */
	@FXML
	public void onCbPesquisarAction(ActionEvent event) {

		if (cbPesquisaSetor.getSelectionModel().getSelectedItem() == null
				|| cbPesquisaSetor.getSelectionModel().getSelectedItem().trim().equals("")) {

			limparCbPesquisaSetor();

		} else {

			setPesquisarSetor(cbPesquisaSetor.getSelectionModel().getSelectedItem());

			pesquisarProduto = "";

			apagarDetalhes();

			new LogSegurancaService().novoLogSeguranca(usuario.getNome(),
					"Setor: " + cbPesquisaSetor.getSelectionModel().getSelectedItem().toUpperCase());

			if (getPesquisarSetor().equals("TODOS")) {

				listaTodos();

				if (listaProdutos.isEmpty() == true) {

					Alerts.showAlert("Pesquisar setor", "Lista vazia", "Produtos não encontrados", AlertType.ERROR);

					updateTableView();
					apagarDetalhes();

					limparCbPesquisaSetor();

				} else {

					updateTableView();

				}

			} else if (getPesquisarSetor().equals("LIMPAR")) {

				tableViewProduto.setItems(null);
				apagarDetalhes();

				limparCbPesquisaSetor();

			} else {

				listaCbSetor();

				if (listaProdutos.isEmpty() == true) {

					Alerts.showAlert("Pesquisar setor", "Lista vazia", "O setor não foi encontrado", AlertType.ERROR);

					updateTableView();
					apagarDetalhes();

					limparCbPesquisaSetor();

				} else {

					updateTableView();

				}

			}

		}

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

		listaSetor.add("TODOS");
		listaSetor.add("LIMPAR");

		for (Setor setor : setorService.findAllId()) {

			listaSetor.add(setor.getNome().toUpperCase());

		}

		return listaSetor;

	}

	private void limparCbPesquisaSetor() {
		cbPesquisaSetor.getSelectionModel().clearSelection();
		cbPesquisaSetor.setItems(FXCollections.observableArrayList(listaSetor()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initializeNodes();

	}

	private void initializeNodes() {

		tableColumnIndex.setSortable(false);
		tableColumnIndex.setCellValueFactory(column -> new ReadOnlyObjectWrapper<String>(
				Constraints.tresDigitos(tableViewProduto.getItems().indexOf(column.getValue()) + 1)));

		tableColumnCodProduto.setCellValueFactory(
				(param) -> new SimpleStringProperty(Constraints.tresDigitos(param.getValue().getIdProduto())));

		tableColumnNome
				.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome().toUpperCase()));

		tableColumnStatus.setCellValueFactory((param) -> new SimpleStringProperty(
				status(param.getValue().getEstoqueMinimo(), param.getValue().getQuantidade())));

		Stage stage = (Stage) SCE1Main.getMainScene().getWindow();
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty());

		service = new ProdutoService();
		movimentacao = new Movimentacao();

		cbPesquisaSetor.setItems(FXCollections.observableArrayList(listaSetor()));

		listaLembrete = service.PesquisarEstoqueBaixo();

		try {

			if (!listaLembrete.isEmpty()) {

				new Forms().LembreteForm(Strings.getLembreteView(), listaLembrete);

			}

		} catch (NullPointerException e) {

		}

	}

	public void updateTableView() {

		if (service == null) {

			throw new IllegalStateException("Service está nulo");

		}

		try {

			tableViewProduto.setItems(listaProdutos);

		} catch (NullPointerException e) {

			listaTodos();

		}

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

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(), absoluteName);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				VBox pane = loader.load();

				SCE1Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(SCE1Main.getDialogScene());
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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	private void createMovimentacaoListDialogForm(Usuario usuario, Produto produto, String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(), absoluteName);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				ScrollPane pane = loader.load();

				MovimentacaoListFormController controller = loader.getController();
				controller.setMovimentacaoService(new MovimentacaoService());
				controller.setLabelLogado(usuario.toUsuarioLogado());
				controller.carregarCampos(produto);
				controller.setProduto(produto);

				pane.setFitToHeight(true);
				pane.setFitToWidth(true);

				SCE1Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(SCE1Main.getDialogScene());
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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	private void createProdutoDialogForm(String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(), absoluteName);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Pane pane = loader.load();

				ProdutoNovoFormController controller = loader.getController();
				controller.setProdutoService(new ProdutoService());
				controller.subscribeDataChangeListener(this);

				SCE1Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(SCE1Main.getDialogScene());
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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

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

				SCE1Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(SCE1Main.getDialogScene());
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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

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
				Parent root = loader.load();

				Scene scene = new Scene(root);
				// scene.setFill(Color.TRANSPARENT);

				SCE1Main.setMainScene(scene);

				MovimentacaoFormController controller = loader.getController();
				controller.setProduto(produto);
				controller.setMovimentacao(movimentacao);
				controller.setMovimentacaoService(new MovimentacaoService());
				controller.subscribeDataChangeListener(this);

				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(SCE1Main.getMainScene());
				produtoStage.setResizable(false);
				produtoStage.initModality(Modality.APPLICATION_MODAL);
				produtoStage.initOwner(null);

				// produtoStage.initStyle(StageStyle.TRANSPARENT);

				Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
				produtoStage.getIcons().add(applicationIcon);

				produtoStage.showAndWait();

			} catch (IOException e) {

				Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
			}

		} else {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	private void createUsuarioDialogForm(String absoluteName) {

		boolean concedido = false;
		Acesso acesso = new Acesso();

		concedido = acesso.concederAcesso(LoginFormController.getLogado().getAcesso(), absoluteName);

		if (concedido == true) {

			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				BorderPane pane = loader.load();

				SCE1Main.setDialogScene(new Scene(pane));
				Stage produtoStage = new Stage();
				produtoStage.setTitle(Strings.getTitle());
				produtoStage.setScene(SCE1Main.getDialogScene());
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

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	private void createPaneDialogForm(String absoluteName) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			SCE1Main.setDialogScene(new Scene(pane));
			Stage produtoStage = new Stage();
			produtoStage.setTitle(Strings.getTitle());
			produtoStage.setScene(SCE1Main.getDialogScene());
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			produtoStage.getIcons().add(applicationIcon);

			produtoStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("Acesso negado", "Acesso não concedido ao usuário logado",
					"Entre em contato com o Administrador do sistema", AlertType.ERROR);

		}

	}

	private List<Produto> listaPesquisa() {

		listaProdutos = FXCollections.observableArrayList(service.PesquisarNomeProduto(pesquisarProduto));

		return listaProdutos;

	}

	private List<Produto> listaCbSetor() {

		String nomeSetor = cbPesquisaSetor.getSelectionModel().getSelectedItem();
		listaProdutos = FXCollections.observableArrayList(service.PesquisarNomeSetor(nomeSetor.toUpperCase()));

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

	public Label getLabelLogado() {
		return labelLogado;
	}

	public void setLabelLogado(String logado) {

		this.labelLogado.setText(logado);

	}

	public String status(Integer estoque_minimo, Integer quantidade) {

		String status = "";

		if (quantidade <= estoque_minimo * 3) {

			status = Strings.getStatus1();

		} else if ((quantidade >= estoque_minimo * 3) && (quantidade <= estoque_minimo * 6)) {

			status = Strings.getStatus2();

		} else {

			status = Strings.getStatus3();

		}

		return status;

	}

	public static Image byteToImage(byte[] img) throws IOException {

		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(img));
		Image image = SwingFXUtils.toFXImage(bi, null);

		return image;
	}

	public void apagarDetalhes() {

		labelNome.setText("");
		labelSetor.setText("");
		labelCategoria.setText("");
		labelSaldoAtual.setText("");
		labelEstoqueMinimo.setText("");
		labelStatus.setText("");
		labelDetalhes.setText("");
		imageViewProduto.setImage(null);

		// listaProdutos.clear();

	}

	public void atualizarDetalhes() {

		produto = service.findById(produto.getIdProduto());

		labelNome.setText(produto.getNome().toUpperCase());
		labelSetor.setText(produto.getSetor().toUpperCase());
		labelCategoria.setText(produto.getCategoria().toUpperCase());
		labelSaldoAtual.setText(Constraints.tresDigitos(produto.getQuantidade()) + " Unidade(s)");
		labelEstoqueMinimo.setText(Constraints.tresDigitos(produto.getEstoqueMinimo()) + " Unidade(s)");
		labelStatus.setText(status(produto.getEstoqueMinimo(), produto.getQuantidade()));
		labelDetalhes.setText(produto.getDescricao().toUpperCase());

		if (produto.getFoto() == null) {

			imageViewProduto.setImage(new Image(Strings.getSemFoto()));

		} else {

			try {

				imageViewProduto.setImage(byteToImage(produto.getFoto()));

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public static byte[] getBytes() {
		return bytes;
	}

	public static void setBytes(byte[] bytes) {
		PrincipalFormController.bytes = bytes;
	}

	public static Scene getPrincipalFormScene() {
		return PrincipalFormScene;
	}

	public static void setPrincipalFormScene(Scene principalFormScene) {
		PrincipalFormScene = principalFormScene;
	}

}
