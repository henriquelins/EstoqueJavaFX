package model.services;

import java.util.List;

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
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());

				break;

			case ("Ajuste de entrada"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() - movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());

				break;

			case ("Sa�da de produtos"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() - movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());

				break;

			case ("Ajuste de sa�da"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() + movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());

				break;

			}

			dao.insert(movimentacao);
			Alerts.showAlert("Movimenta��o", "Movimenta��o de produtos", movimentacao.getTipo().toString(),
					AlertType.INFORMATION);
			Utils.fecharDialogAction();

		} catch (Exception e) {
			Alerts.showAlert("Movimenta��o", null, e.getLocalizedMessage(), AlertType.ERROR);
		}
	}

	public List<Movimentacao> findAll() {
		return dao.findAll();
	}

}
