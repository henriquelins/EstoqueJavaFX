package gui.util;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	public static Stage currentStage(ActionEvent event) {
		
		 return (Stage)((Node) event.getSource()).getScene().getWindow();
		
	}

	public static Integer tryParseToInt(String str){
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}		
	}

	public static void fecharTelaAction() {
		 Stage stage = (Stage) Main.getMainScene().getWindow(); //Obtendo a janela atual
		    stage.close(); //Fechando o Stage	
	}
}
