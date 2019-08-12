package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import gui.util.Strings;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entities.Produto;

public class ProdutoShowFormController implements Initializable {

	private Produto produto;

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

		labelNome.setText(PrincipalFormController.getProduto().getNome());
		labelSetor.setText(PrincipalFormController.getProduto().getSetor());
		labelCategoria.setText(PrincipalFormController.getProduto().getCategoria());
		labelSaldoAtual.setText(String.valueOf(PrincipalFormController.getProduto().getQuantidade()));

		labelStatus.setText(status(PrincipalFormController.getProduto().getEstoqueMinimo(),
				PrincipalFormController.getProduto().getQuantidade()));

		labelDetalhes.setText(PrincipalFormController.getProduto().getDescricao());


		if (PrincipalFormController.getProduto().getFoto().getFoto() == null) {

			imageViewProduto.setImage(new Image(Strings.getSemFoto()));

		} else {

			try {

				imageViewProduto.setImage(byteToImage(PrincipalFormController.getProduto().getFoto().getFoto()));

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	}

	public static Image byteToImage(byte[] img) throws IOException {

		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(img));
		Image image = SwingFXUtils.toFXImage(bi, null);

		return image;
	}
	

	public String status(Integer estoque_minimo, Integer quantidade) {

		String status = "";

		if (quantidade <= estoque_minimo) {

			status = "Estoque baixo";

		} else if ((quantidade >= estoque_minimo * 3) && (quantidade <= estoque_minimo * 6)) {

			status = "Estoque normal";

		} else {

			status = "Estoque alto";

		}

		return status;

	}

}
