package gui.forms;

import java.io.IOException;

import application.EstoqueJavaFxMain;
import gui.ConfigurarPerpetiesDBFormController;
import gui.PrincipalFormController;
import gui.util.Alerts;
import gui.util.Strings;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.entities.Usuario;

public class Forms {

	// Todas são telas simples sem DataChangeList

	// forms tela splash

	public void splashForm(String tela) throws IOException {

		try {

			StackPane pane = FXMLLoader.load(getClass().getResource(tela));

			Scene scene = new Scene(pane);
			scene.setFill(Color.TRANSPARENT);

			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.TRANSPARENT);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.show();

			// Carrega a tela splash com fade in effect
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(2);
			fadeIn.setCycleCount(1);

			// Termina a tela splash com fade out effect
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
			fadeOut.setFromValue(2);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			fadeIn.play();

			// Depois de fade in, inicia o fade out
			fadeIn.setOnFinished((e) -> {

				fadeOut.play();

			});

			// Depois do fade out, carrega a tela inicial - login
			fadeOut.setOnFinished((e) -> {

				primaryStage.close();
				loginForm(Strings.getLoginView());

			});

		} catch (IOException ex) {

			Alerts.showAlert("Controle de Estoque", "Erro ao abrir o splash", ex.getLocalizedMessage(),
					AlertType.ERROR);

		}

	}

	// forms tela login

	public void loginForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);

			EstoqueJavaFxMain.setMainScene(scene);

			Stage principalStage = new Stage();
			principalStage.setTitle(Strings.getTitle());
			principalStage.setScene(EstoqueJavaFxMain.getMainScene());
			principalStage.setResizable(false);

			principalStage.initStyle(StageStyle.TRANSPARENT);
			// principalStage.setOpacity(0.7);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			principalStage.getIcons().add(applicationIcon);

			principalStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}

	public void principalForm(Usuario usuario, String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			ScrollPane pane = loader.load();

			PrincipalFormController controller = loader.getController();
			controller.setLabelLogado(usuario.toUsuarioLogado());
			controller.setUsuario(usuario);

			PrincipalFormController.setPrincipalFormScene(new Scene(pane));

			pane.setFitToHeight(true);
			pane.setFitToWidth(true);

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(PrincipalFormController.getPrincipalFormScene());

			primaryStage.setResizable(true);
			primaryStage.setMaximized(true);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.show();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela principal", e.getCause().toString(),
					AlertType.ERROR);

		}

	}

	public void ConfigurarPerpetiesDBForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			VBox pane = loader.load();

			ConfigurarPerpetiesDBFormController controller = loader.getController();
			controller.carregarCampos();

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}
	
	public void LogSegurancaForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			VBox pane = loader.load();

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}
	
	public void BackupBancoForm(String tela) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
			VBox pane = loader.load();

			Stage primaryStage = new Stage();
			primaryStage.setTitle(Strings.getTitle());
			primaryStage.setScene(new Scene(pane));
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);

			Image applicationIcon = new Image(getClass().getResourceAsStream(Strings.getIcone()));
			primaryStage.getIcons().add(applicationIcon);

			primaryStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("IO Exception", "Erro ao carregar a tela", e.getCause().toString(), AlertType.ERROR);

		}

	}

}
