package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VisualizarFotoFormController implements Initializable {

	@FXML
	private ImageView imageView;

	private String endereco;

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		imageView.setImage(new Image(getClass().getResourceAsStream(endereco)));

	}

}
