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
import model.entities.Produto;
import model.entities.Setor;
import model.services.CategoriaService;
import model.services.ProdutoService;
import model.services.SetorService;

public class ProdutoEditarFormController implements Initializable, DataChangeListener {

	private ProdutoService produtoService;

	private Produto produtoComparar;

	private PrincipalFormController principalController;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private static byte[] bytes;

	@FXML
	private TextField txtIdProduto;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private TextField txtEstoqueMinimo;

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

			boolean ok = false;

			ok = compararCampos();

			if (ok == false) {

				produtoService.produtoNovoOuEditar(PrincipalFormController.getProduto());
				notifyDataChangeListeners();
				Utils.fecharDialogAction();

			} else {

				Alerts.showAlert("Produto", "Editar", "Não houve alteração no registro", AlertType.INFORMATION);

			}

		}

	}

	@FXML
	public void onBtFotoProdutoAction(ActionEvent event) {

		FileChooser chooser = new FileChooser();
		File arquivo = null;
		String local = "";

		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));

		chooser.setTitle("Escolher foto do produto");

		arquivo = chooser.showOpenDialog(new Stage());

		if (arquivo != null) {

			local = arquivo.getAbsolutePath();
			InputStream converter = null;

			setBytes(new byte[(int) arquivo.length()]);

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

		} else {

			local = "";
			setBytes(null);

		}

	}

	public static byte[] getBytes() {

		return bytes;

	}

	public static void setBytes(byte[] bytes) {

		ProdutoEditarFormController.bytes = bytes;

	}

	public void subscribeDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);

	}

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

	@FXML
	public void onBtVisualizarFotoAction(ActionEvent event) {

		if (!bytes.equals(null) == true) {

			createVisualizarFotoDialogForm(Strings.getVisualizarFotoView());

		} else {

			Alerts.showAlert("Visualizar foto", "Selecionar a foto do produto", "Primeiro selecione um imagem",
					AlertType.ERROR);

		}

	}

	private void createVisualizarFotoDialogForm(String absoluteName) {

		try {

			Produto prod = new Produto();
			prod.setFoto(bytes);
			PrincipalFormController.setProduto(prod);

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		comboBoxSetor.setItems(FXCollections.observableArrayList(listaSetor()));
		comboBoxCategoria.setItems(FXCollections.observableArrayList(listaCategoria()));

		produtoService = new ProdutoService();

		Constraints.setTextFieldInteger(txtQuantidade);
		Constraints.setTextFieldInteger(txtEstoqueMinimo);

		updateFormData();

	}

	private Produto getFormData() {

		Produto prod = new Produto();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Digite o nome do Produto", AlertType.INFORMATION);

			txtNome.requestFocus();

			prod = null;

		} else if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Digite a quantidade inicial do produto",
					AlertType.INFORMATION);

			txtQuantidade.requestFocus();

			prod = null;

		} else if (txtEstoqueMinimo.getText() == null || txtEstoqueMinimo.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Digite o estoque mínimo", AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			prod = null;

		} else if (comboBoxSetor.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Novo Produto", "Campos obrigatório", "Selecione o setor", AlertType.INFORMATION);

			comboBoxSetor.requestFocus();

			prod = null;

		} else if (comboBoxCategoria.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Selecione a categoria", AlertType.INFORMATION);

			comboBoxCategoria.requestFocus();

			prod = null;

		} else if (txtAreaDescricao.getText() == null || txtAreaDescricao.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigatório", "Digite a descrição do produto",
					AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			prod = null;

		} else {

			prod.setIdProduto(Integer.valueOf(txtIdProduto.getText()));
			prod.setNome(txtNome.getText());
			prod.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
			prod.setEstoqueMinimo(Integer.valueOf(txtEstoqueMinimo.getText()));
			prod.setSetor(String.valueOf(comboBoxSetor.getSelectionModel().getSelectedItem()));
			prod.setCategoria(String.valueOf(comboBoxCategoria.getSelectionModel().getSelectedItem()));
			prod.setDescricao(txtAreaDescricao.getText());
			prod.setFoto(bytes);

		}

		return prod;

	}

	public void updateFormData() {

		txtIdProduto.setText(String.valueOf(PrincipalFormController.getProduto().getIdProduto()));
		txtNome.setText(PrincipalFormController.getProduto().getNome());
		txtQuantidade.setText(String.valueOf(PrincipalFormController.getProduto().getQuantidade()));
		comboBoxSetor.setValue(PrincipalFormController.getProduto().getSetor());
		comboBoxCategoria.setValue(PrincipalFormController.getProduto().getCategoria());
		txtEstoqueMinimo.setText(String.valueOf(PrincipalFormController.getProduto().getEstoqueMinimo()));
		txtAreaDescricao.setText(PrincipalFormController.getProduto().getDescricao());
		bytes = PrincipalFormController.getProduto().getFoto();

		produtoComparar = PrincipalFormController.getProduto();

	}

	@Override
	public void onDataChanged() {

		principalController.updateTableView();

	}

	public boolean compararCampos() {

		boolean ok = false;

		if (produtoComparar == null) {

			return ok;

		} else if (PrincipalFormController.getProduto().getNome().equals(produtoComparar.getNome())
				&& PrincipalFormController.getProduto().getSetor().equals(produtoComparar.getSetor())
				&& PrincipalFormController.getProduto().getCategoria().equals(produtoComparar.getCategoria())
				&& PrincipalFormController.getProduto().getDescricao().equals(produtoComparar.getDescricao())) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	};

	public byte[] getByte(String local) {

		InputStream converter = null;
		bytes = new byte[(int) local.length()];

		int offset = 0;
		int numRead = 0;

		try {

			converter = new FileInputStream(local);

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
