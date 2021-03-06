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

import application.SCE1Main;
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

	private Produto produtoComparar;

	private ProdutoService produtoService;

	private PrincipalFormController principalController;

	private static byte[] bytes;

	private String setor;

	private int id_setor;

	// Lista de ouvintes para receber alguma modifica��o
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
				Utils.currentStage(event).close();
				
				//PrincipalFormController.getProduto().setFoto(getBytes());
				
				bytes = null;
				

			} else {

				Alerts.showAlert("Produto", "Editar", "N�o houve altera��o nos dados do produto!",
						AlertType.INFORMATION);

			}

		}

	}

	@FXML
	public void onSelectComboBoxSetorAction(ActionEvent event) {

		SetorService setorService = new SetorService();
		CategoriaService categoriaService = new CategoriaService();

		setSetor(comboBoxSetor.getSelectionModel().getSelectedItem());
		setId_setor(setorService.findNomeIdSetor(getSetor()));

		List<String> listaCategoria = new ArrayList<>();

		for (Categoria categoria : FXCollections.observableArrayList(categoriaService.findIdSetor(id_setor))) {

			listaCategoria.add(categoria.getNome().toUpperCase());
		}

		comboBoxCategoria.setItems(FXCollections.observableArrayList(listaCategoria));
	}

	@FXML
	public void onBtFotoProdutoAction(ActionEvent event) {

		FileChooser chooser = new FileChooser();
		File arquivo = null;
		String local = "";

		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));

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

			listaSetor.add(setor.getNome().toUpperCase());
		}

		return listaSetor;

	}

	@FXML
	public void onBtVisualizarFotoAction(ActionEvent event) {

		if (!bytes.equals(null) == true) {
			
			PrincipalFormController.setBytes(getBytes());

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

			Produto prod = new Produto();
			prod.setFoto(getBytes());
			PrincipalFormController.setProduto(prod);

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

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela foto do produto", e.getMessage(),
					AlertType.ERROR);

		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {
		
		txtQuantidade.setEditable(false);

		comboBoxSetor.setItems(FXCollections.observableArrayList(listaSetor()));

		produtoService = new ProdutoService();

		Constraints.setTextFieldInteger(txtQuantidade);
		Constraints.setTextFieldInteger(txtEstoqueMinimo);

		updateFormData();

	}

	private Produto getFormData() {

		Produto prod = new Produto();

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigat�rio", "Digite o nome do Produto", AlertType.INFORMATION);

			txtNome.requestFocus();

			prod = null;

		} else if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigat�rio", "Digite a quantidade inicial do produto",
					AlertType.INFORMATION);

			txtQuantidade.requestFocus();

			prod = null;

		} else if (txtEstoqueMinimo.getText() == null || txtEstoqueMinimo.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigat�rio", "Digite o estoque m�nimo", AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			prod = null;

		} else if (comboBoxSetor.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Novo Produto", "Campos obrigat�rio", "Selecione o setor", AlertType.INFORMATION);

			comboBoxSetor.requestFocus();

			prod = null;

		} else if (comboBoxCategoria.getSelectionModel().getSelectedItem() == null) {

			Alerts.showAlert("Novo Produto", "Campo obrigat�rio", "Selecione a categoria", AlertType.INFORMATION);

			comboBoxCategoria.requestFocus();

			prod = null;

		} else if (txtAreaDescricao.getText() == null || txtAreaDescricao.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigat�rio", "Digite a descri��o do produto",
					AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			prod = null;

		} else if (txtAreaDescricao.getText() == null || txtAreaDescricao.getText().trim().equals("")) {

			Alerts.showAlert("Novo Produto", "Campo obrigat�rio", "Digite a descri��o do produto",
					AlertType.INFORMATION);

			txtAreaDescricao.requestFocus();

			prod = null;

		} else {

			prod.setIdProduto(Integer.valueOf(txtIdProduto.getText()));
			prod.setNome(txtNome.getText().toUpperCase());
			prod.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
			prod.setEstoqueMinimo(Integer.valueOf(txtEstoqueMinimo.getText()));
			prod.setSetor(String.valueOf(comboBoxSetor.getSelectionModel().getSelectedItem().toUpperCase()));
			prod.setCategoria(String.valueOf(comboBoxCategoria.getSelectionModel().getSelectedItem().toUpperCase()));
			prod.setDescricao(txtAreaDescricao.getText());
			prod.setFoto(getBytes());

		}

		return prod;

	}

	public void updateFormData() {

		txtIdProduto.setText(Constraints.tresDigitos(PrincipalFormController.getProduto().getIdProduto()));
		txtNome.setText(PrincipalFormController.getProduto().getNome().toUpperCase());
		txtQuantidade.setText(Constraints.tresDigitos(PrincipalFormController.getProduto().getQuantidade()));
		comboBoxSetor.setValue(PrincipalFormController.getProduto().getSetor().toUpperCase());
		comboBoxCategoria.setValue(PrincipalFormController.getProduto().getCategoria().toUpperCase());
		txtEstoqueMinimo.setText(Constraints.tresDigitos(PrincipalFormController.getProduto().getEstoqueMinimo()));
		txtAreaDescricao.setText(PrincipalFormController.getProduto().getDescricao().toUpperCase());
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
				&& PrincipalFormController.getProduto().getDescricao().equals(produtoComparar.getDescricao())
				&& PrincipalFormController.getProduto().getFoto().equals(produtoComparar.getFoto())) {

			ok = true;
			return ok;

		} else {

			return ok;

		}

	}

	// Getters and Setters

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public int getId_setor() {
		return id_setor;
	}

	public void setId_setor(int id_setor) {
		this.id_setor = id_setor;
	};

}
