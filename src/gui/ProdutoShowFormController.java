package gui;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

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
		labelStatus.setText(String.valueOf(PrincipalFormController.getProduto().getEstoqueMinimo()));
		labelDetalhes.setText(PrincipalFormController.getProduto().getDescricao());

		// imageViewProduto.setImage(new Image ("/imagens/bozo.jpg"));
		// imageViewProduto.setImage(new Image ("/imagens/SEM-FOTO.png"));

		// imageViewProduto.setImage(new Image (new
		// ByteArrayInputStream(PrincipalFormController.getProduto().getFoto().getFoto())));

		// Image imagem = new Image(new
		// ByteArrayInputStream(PrincipalFormController.getProduto().getFoto().getFoto()));

		// imageViewProduto .setImage(imagem);

		// InputStream is = new ByteArrayInputStream(
		// PrincipalFormController.getProduto().getFoto().getFoto());

		// ImageIcon iconFrente = new
		// ImageIcon(PrincipalFormController.getProduto().getFoto().getFoto());

		/*
		 * try { 
		 * byte[] imageInbyte = PrincipalFormController.getProduto().getFoto().getFoto(); 
		 * BufferedImage img1;
		 * img1 = ImageIO.read(new ByteArrayInputStream(imageInbyte)); 
		 * Image image = SwingFXUtils.toFXImage(img1, null); 
		 * imageViewProduto.setImage(image);
		 * imageViewProduto.setPreserveRatio(true);
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		
		
		
        /*Image image = null;
		try {
			
			image = byteToImage(PrincipalFormController.getProduto().getFoto().getFoto());
			
		} catch (IOException e) {
			
			
			e.printStackTrace();
		}*/

		if (PrincipalFormController.getProduto().getFoto().getFoto() == null) {

			imageViewProduto.setImage(new Image("/imagens/SEM-FOTO.png"));

		} else {

			imageViewProduto.setImage(new Image(new File(PrincipalFormController.getProduto().getFoto().getLocal()).toURI().toString()));
			//System.out.println();
			

			// imageViewProduto.setImage(new Image (new
			// ByteArrayInputStream(PrincipalFormController.getProduto().getFoto().getFoto())));

		}

	}
	
	public static Image byteToImage(byte[] img) throws IOException {
        
	 	BufferedImage bi = ImageIO.read(new ByteArrayInputStream(img));
        Image image = SwingFXUtils.toFXImage(bi, null);
        
        return image;
  }


}
