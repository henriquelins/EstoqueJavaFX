package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import gui.util.Strings;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VisualizarFotoFormController implements Initializable {

	@FXML
	private ImageView imageView;

	@FXML
	private TextField txtEndereco;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		txtEndereco.setText(ProdutoNovoFormController.getLocal().toString());
		visualisarFoto();
	}

	public void visualisarFoto() {
		
		if (PrincipalFormController.getProduto().getFoto().getFoto() == null) {

			imageView.setImage(new Image( Strings.getSemFoto()));

		} else {

			//imageView.setImage(
					//new Image(new File(PrincipalFormController.getProduto().getFoto().getLocal()).toURI().toString()));
			
			try {
				
				imageView.setImage(byteToImage(ProdutoNovoFormController.getBytes()));
				
				//imageView.setImage(byteToImage(PrincipalFormController.getProduto().getFoto().getFoto()));
				
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

}
