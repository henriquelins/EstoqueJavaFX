package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.services.MovimentacaoService;

public class ProdutoShowFormController implements Initializable {

	private Produto produto;
	
	private Movimentacao movimentacao;

	@FXML
	private Label labelNome;

	@FXML
	private Label labelSetor;

	@FXML
	private Label labelCategoria;

	@FXML
	private Label labelSaldoAtual;

	@FXML
	private Label labelDetalhes;

	@FXML
	private Label labelStatus;

	@FXML
	private ImageView imageViewProduto;

	@FXML
	private Button btMovimentacao;
	
	@FXML
	public void onBtMovimentacaoAction(ActionEvent event) {
		
		
		Utils.fecharDialogAction();
		createMovimentacaoDialogForm(produto,"/gui/MovimentacaoView.fxml");

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
		
			Main.setDialogScene(new Scene(pane));
			Stage produtoStage = new Stage();
			produtoStage.setTitle("Movimentação de Produtos");
			produtoStage.setScene(Main.getDialogScene());
			produtoStage.setResizable(false);
			produtoStage.initModality(Modality.APPLICATION_MODAL);
			produtoStage.initOwner(null);

			Image applicationIcon = new Image(getClass().getResourceAsStream("/imagens/bozo.jpg"));
			produtoStage.getIcons().add(applicationIcon);

			produtoStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela movimentação de produtos", e.getMessage(), AlertType.ERROR);
		}
		
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		produto = new Produto();
		movimentacao = new Movimentacao();


		labelNome.setText(produto.getNome());
		labelSetor.setText(produto.getSetor());
		labelCategoria.setText(produto.getCategoria());
		labelSaldoAtual.setText(String.valueOf(produto.getQuantidade()));
		labelStatus.setText(String.valueOf(produto.getEstoqueMinimo()));
		labelDetalhes.setText(produto.getDescricao());

		// imageViewProduto.setImage(new Image(produto.getFoto().getFoto()));

	}

}
