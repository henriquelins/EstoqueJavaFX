package model.services;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.scene.control.Alert.AlertType;
import model.entities.Movimentacao;

public class MovimentacaoService {

	public void movimentacaoSaidaOuEntrada(Movimentacao movimentacao) {
		
		Alerts.showAlert("onBtnSalvarMovimentacaoAction", "Não implementado", "movimentacaoSaidaOuEntrada", AlertType.ERROR);
		
		Utils.fecharDialogAction();
	}

}
