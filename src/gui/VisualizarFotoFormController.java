package gui;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initializeNodes();

	}

	private void initializeNodes() {

		txtEndereco.setText(ProdutoNovoFormController.getLocal());

		imageView.setImage(new Image(ProdutoNovoFormController.getArquivo().toURI().toString()));

	}

}
