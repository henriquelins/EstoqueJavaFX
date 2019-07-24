package gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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

	private Image imageProduto;
	
	private File arquivo;
	
	
	

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		initializeNodes();

	}

	private void initializeNodes() {
		
		txtEndereco.setText(arquivo.getPath());
		
		imageProduto = new Image(arquivo.toURI().toString());
				
		imageView.setImage(imageProduto);
		
		
	}

}
