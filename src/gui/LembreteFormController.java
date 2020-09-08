package gui;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Constraints;
import gui.util.Strings;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.entities.Produto;

public class LembreteFormController implements Initializable {

	// @FXML variáveis

	@FXML
	private Label labelTitle;

	@FXML
	private Button buttonFechar;

	@FXML
	private TextArea textAreaLembrete;

	@FXML
	public void onButtonFecharAction(ActionEvent event) {

		Utils.currentStage(event).close();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	

		initializeNodes();

	}

	private void initializeNodes() {
		
		labelTitle.setText(Strings.getTitleLembrete());

	}

	public void carregarCampos(List <Produto> listaLembrete) {
		
		
		textAreaLembrete.appendText("LEMBRETE PRODUTO(S) PARA FAZER PEDIDO - DATA: " + Constraints.setDateFormat(new Date(System.currentTimeMillis())));
		textAreaLembrete.appendText("\n");
		textAreaLembrete.appendText("\n");
		
		for (Produto pr : listaLembrete) {

			textAreaLembrete.appendText("PRODUTO: " + pr.getNome() + " - TOTAL: " + Constraints.tresDigitos(pr.getQuantidade())
					+ " - ESTOQUE BAIXO" + "\n");

		}
		
		textAreaLembrete.appendText("\n");
		textAreaLembrete.appendText("DÚVIDAS, ENTRE EM CONTATO COM O ADMINISTRADOR");
				
	}

}
