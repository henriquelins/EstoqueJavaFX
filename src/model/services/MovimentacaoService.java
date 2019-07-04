package model.services;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.scene.control.Alert.AlertType;
import model.dao.DaoFactory;
import model.dao.MovimentacaoDao;
import model.dao.ProdutoDao;
import model.entities.Movimentacao;

public class MovimentacaoService {

	private MovimentacaoDao dao = DaoFactory.createMovimentacaoDao();
	private ProdutoDao daoProd = DaoFactory.createProdutoDao();

	public void movimentacaoSaidaOuEntrada(Movimentacao movimentacao) {

		int estoqueAtual = 0;

		try {

			switch (movimentacao.getTipo()) {

			case ("Entrada do produtos"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() + movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getIdProduto());

				break;

			case ("Ajuste de entrada"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() - movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getIdProduto());

				break;

			case ("Saída de produtos"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() - movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getIdProduto());

				break;

			case ("Ajuste de saída"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() + movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getIdProduto());

				break;

			}

			dao.insert(movimentacao);
			Alerts.showAlert("Movimentação", null, movimentacao.getTipo().toString() , AlertType.INFORMATION);
			//Utils.fecharDialogAction();
			
		} catch (Exception e) {
			Alerts.showAlert("Movimentação", null, e.getLocalizedMessage(), AlertType.ERROR);
		}
	}

}
