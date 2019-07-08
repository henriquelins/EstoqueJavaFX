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

			case ("Saída de produtos"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() - movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());

				break;

			case ("Ajuste de saída"):

				estoqueAtual = movimentacao.getQuantidadeAnterior() + movimentacao.getValorMovimento();
				daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());

				break;

			}

			dao.insert(movimentacao);
			Alerts.showAlert("Movimentação", "Movimentação de produtos", movimentacao.getTipo().toString(),
					AlertType.INFORMATION);
			Utils.fecharDialogAction();

		} catch (Exception e) {
			Alerts.showAlert("Movimentação", null, e.getLocalizedMessage(), AlertType.ERROR);
		}
	}

	public List<Movimentacao> findAll() {
		return dao.findAll();
	}

}
