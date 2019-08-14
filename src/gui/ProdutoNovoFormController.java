package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Strings;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Categoria;
import model.entities.Foto;
import model.entities.Produto;
import model.entities.Setor;
import model.services.CategoriaService;
import model.services.ProdutoService;
import model.services.SetorService;

public class ProdutoNovoFormController implements Initializable, DataChangeListener {

	private ProdutoService produtoService;

	private PrincipalFormController principalController;

	private static byte[] bytes;

	private static File arquivo;

	private static String local = "";

	// Lista de ouvintes para receber alguma modificação
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdProduto;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private TextField txtEstoqueMinimo;

	@FXML
	private TextField txtEnderecoDaFoto;

	@FXML
	private ComboBox<String> comboBoxSetor;

	@FXML
	private ComboBox<String> comboBoxCategoria;

	@FXML
	private TextArea txtAreaDescricao;

	@FXML
	private Button btSalvarProduto;

	@FXML
	private Button btFotoProduto;

	@FXML
	private Button btVisualizarFoto;

	@FXML
	public void onBtSalvarProdutoAction(ActionEvent event) {

		PrincipalFormController.setProduto(getFormData());

		if (PrincipalFormController.getProduto() != null) {

			produtoService.produtoNovoOuEditar(PrincipalFormController.getProduto());
			notifyDataChangeListeners();
			Utils.fecharDialogAction();

		}

	}

	@FXML
	public void onBtFotoProdutoAction(ActionEvent event) {

		FileChooser chooser = new FileChooser();
		arquivo = null;

		chooser.getExtensionFilters().addAll(//
				new FileChooser.ExtensionFilter("All Files", "*.*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));

		chooser.setTitle("Escolher foto do produto");

		arquivo = chooser.showOpenDialog(new Stage());

		if (arquivo != null) {

			local = arquivo.getAbsolutePath();
			txtEnderecoDaFoto.setText(local);
			InputStream converter = null;

			bytes = new byte[(int) arquivo.length()];

			try {

				converter = new FileInputStream(local);

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

			int offset = 0;
			int numRead = 0;

			try {

				while (offset < bytes.length && (numRead = converter.read(bytes, offset, bytes.length - offset)) >= 0) {

					offset += numRead;
				}

			} catch (IOException e) {

				e.printStackTrace();

			}

			Foto fot = new Foto();
			fot.setLocal(local);
			fot.setFoto(bytes);

			Produto prod = new Produto();
			prod.setFoto(fot);

			PrincipalFormController.setProduto(prod);

			local = "";
			bytes = null;

		} else {

			local = "";
			bytes = null;
			txtEnderecoDaFoto.setText(local);

		}

	}

	public static byte[] getBytes() {

		return bytes;

	}

	public static void setBytes(byte[] bytes) {

		ProdutoNovoFormController.bytes = bytes;

	}

	public static String getLocal() {

		return local;

	}

	public static void setLocal(String local) {

		ProdutoNovoFormController.local = local;

	}

	public static File getArquivo() {

		return arquivo;

	}

	public static void setArquivo(File arquivo) {

		ProdutoNovoFormController.arquivo = arquivo;

	}

	@FXML
	public void onBtVisualizarFotoAction(ActionEvent event) {

		if (!txtEnderecoDaFoto.getText().equals("")) {

			createVisualizarFotoDialogForm(Strings.getVisualizarFotoView());

		} else {

			Alerts.showAlert("Visualizar foto", "Selecionar a foto do produto", "Primeiro selecione um imagem",
					AlertType.ERROR);

		}

	}

	private void createVisualizarFotoDialogForm(String absoluteName) {

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

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela foto do produto", e.getMessage(),
					AlertType.ERROR);

		}

	}

	// Adiciona a lista um ouvinte, quando há uma modificação
	public void subscribeDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);

	}

	// Função que faz a atualização da tabela
	private void notifyDataChangeListeners() {

		for (DataChangeListener listener : dataChangeListeners) {

			listener.onDataChanged();

		}

	}

	public void setProdutoService(ProdutoService produtoService) {

		this.produtoService = produtoService;

	}

	private List<String> listaSetor() {

		SetorService setorService = new SetorService();
		List<String> listaSetor = new ArrayList<>();

		for (Setor setor : setorService.findAllNome()) {

			listaSetor.add(setor.getNome());
		}

		return listaSetor;

	}

	private List<String> listaCategoria() {

		CategoriaService categoriaService = new CategoriaService();
		List<String> listaCategoria = new ArrayList<>();

		for (Categoria categoria : categoriaService.findAllNome()) {

			listaCategoria.add(categoria.getNome());
		}

		return listaCategoria;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		comboBoxSetor.setItems(FXCollections.observableArrayList(listaSetor()));
		comboBoxCategoria.setItems(FXCollections.observableArrayList(listaCategoria()));

		produtoService = new ProdutoService();
		local = new String();

		Constraints.setTextFieldInteger(txtQuantidade);
		Constraints.setTextFieldInteger(txtEstoqueMinimo);

		principalController = new PrincipalFormController();

	}

	private Produto getFormData() {

		Produto produto = new Produto();
		Foto foto = new Foto();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Digite o nome do Produto", AlertType.INFORMATION);

			txtNome.requestFocus();

			produto = null;

		} else if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Digite a quantidade inicial do produto",
					AlertType.INFORMATION);

			txtQuantidade.requestFocus();

			produto = null;

		} else if (txtEstoqueMinimo.getText() == null || txtEstoqueMinimo.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Digite o estoque mínimo", AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			produto = null;

		} else if (comboBoxSetor.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Novo Produto", "Campos obrigatório", "Selecione o setor", AlertType.INFORMATION);

			comboBoxSetor.requestFocus();

			produto = null;

		} else if (comboBoxCategoria.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Selecione a categoria", AlertType.INFORMATION);

			comboBoxCategoria.requestFocus();

			produto = null;

		} else if (txtAreaDescricao.getText() == null || txtAreaDescricao.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Digite a descrição do produto",
					AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			produto = null;

		} else {
			
			if (PrincipalFormController.getProduto().getFoto().getIdFoto() == 0) {

				foto.setIdFoto(0);

			} else {

				foto.setIdFoto(PrincipalFormController.getProduto().getFoto().getIdFoto());
			}

			foto.setLocal(PrincipalFormController.getProduto().getFoto().getLocal());
			foto.setFoto(PrincipalFormController.getProduto().getFoto().getFoto());

			produto.setNome(txtNome.getText());
			produto.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
			produto.setEstoqueMinimo(Integer.valueOf(txtEstoqueMinimo.getText()));
			produto.setSetor(String.valueOf(comboBoxSetor.getSelectionModel().getSelectedItem()));
			produto.setCategoria(String.valueOf(comboBoxCategoria.getSelectionModel().getSelectedItem()));
			produto.setDescricao(txtAreaDescricao.getText());
			produto.setFoto(foto);

		}

		return produto;

	}

	@Override
	public void onDataChanged() {

		principalController.updateTableView();

	}

	public TextField getTxtEnderecoDaFoto() {

		return txtEnderecoDaFoto;

	}

	public void setTxtEnderecoDaFoto(TextField txtEnderecoDaFoto) {

		this.txtEnderecoDaFoto = txtEnderecoDaFoto;

	}

	public byte[] getByte() {

		InputStream converter = null;
		bytes = new byte[(int) local.length()];

		int offset = 0;
		int numRead = 0;

		try {

			converter = new FileInputStream(ProdutoNovoFormController.local);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		try {

			while (offset < bytes.length && (numRead = converter.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

		} catch (IOException e) {

			e.printStackTrace();

		}

		return bytes;
	}

}
