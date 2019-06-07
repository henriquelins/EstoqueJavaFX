package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrincipalViewController implements Initializable {

	@FXML
	private MenuItem menuItemLogout;

	@FXML
	private MenuItem menuItemFechar;

	@FXML
	private MenuItem menuItemUsuario;

	@FXML
	private MenuItem menuItemProduto;

	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public void onMenuItemLogout(ActionEvent event) {
		
		Utils.currentStage(event).close();
		
		Stage parentStage = Utils.currentStage(event);
		createLoginForm("/gui/LoginView.fxml", parentStage);
	}

	@FXML
	public void onMenuItemFechar(ActionEvent event) {

		Utils.currentStage(event).close();
		
	}

	@FXML
	public void onMenuItemUsuario(ActionEvent event) {

		System.out.println("onMenuItemUsuario");

	}

	@FXML
	public void onMenuItemProduto(ActionEvent event) {
		
		Stage parentStage = Utils.currentStage(event);
		createProdutoForm("/gui/ProdutoView.fxml", parentStage);

	}

	@FXML
	public void onMenuItemSobre(ActionEvent event) {

		System.out.println("onMenuItemSobre");

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	
	}
	

	private void createLoginForm(String absoluteName, Stage parenteStage) {
		
		Scene loginScene;
		Stage LoginStage = new Stage();
		
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			loginScene = new Scene(pane);
			LoginStage.setScene(loginScene);
			LoginStage.setTitle("Estoque Crachás");
			LoginStage.show();
			
		} catch (IOException e) {
			
			Alerts.showAlert("IO Exception", "Erro ao carregar a tela Login", e.getMessage(), AlertType.ERROR);
			
		}
		
	}
	
	private void createProdutoForm(String absoluteName, Stage parenteStage) {

		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			Stage produtoStage = new Stage();
			produtoStage.setTitle("Produto");
			produtoStage.setScene(new Scene(pane));
			produtoStage.setResizable(false);
			produtoStage.initOwner(parenteStage);
			produtoStage.initModality(Modality.WINDOW_MODAL);
			produtoStage.showAndWait();
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}

	}

}
