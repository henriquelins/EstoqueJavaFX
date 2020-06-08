package gui.util;

import application.SCE1Main;
import gui.PrincipalFormController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	public static Stage currentStage(ActionEvent event) {

		return (Stage) ((Node) event.getSource()).getScene().getWindow();

	}

	public static Integer tryParseToInt(String str) {

		try {

			return Integer.parseInt(str);

		} catch (NumberFormatException e) {

			return null;

		}

	}

	public static void fecharTelaAction() {

		Stage stage = (Stage) SCE1Main.getMainScene().getWindow(); // Obtendo a janela atual
		stage.close(); // Fechando o Stage

	}

	public static void fecharDialogAction() {

		Stage stage = (Stage) SCE1Main.getDialogScene().getWindow(); // Obtendo a janela dialog atual
		stage.close(); // Fechando o Stage

	}

	// fechar tela principal

	public static void fecharTelaPrincipalFormAction() {

		Stage stage = (Stage) PrincipalFormController.getPrincipalFormScene().getWindow(); // Obtendo a janela atual
		stage.close();// Fechando o Stage

	}
}
