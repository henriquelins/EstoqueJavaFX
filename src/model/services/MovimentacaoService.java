package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.scene.control.ButtonType;
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

			case ("Entrada de produtos (+)"):

				Optional<ButtonType> result1 = Alerts.showConfirmation("Confirmação",
						"Você deseja dar entrada no produto?");

				if (result1.get() == ButtonType.OK) {

					estoqueAtual = movimentacao.getQuantidadeAnterior() + movimentacao.getValorMovimento();
					daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());
					dao.insert(movimentacao);
				
				}

				break;

			case ("Saída de produtos (-)"):

				Optional<ButtonType> result2 = Alerts.showConfirmation("Confirmação",
						"Você deseja dar saída no produto?");

				if (result2.get() == ButtonType.OK) {

					estoqueAtual = movimentacao.getQuantidadeAnterior() - movimentacao.getValorMovimento();
					daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());
					dao.insert(movimentacao);
				

				}

				break;

			}

			Utils.fecharDialogAction();

		} catch (Exception e) {

			Alerts.showAlert("Movimentação", null, e.getLocalizedMessage(), AlertType.ERROR);

		}
	}

	public List<Movimentacao> findAll() {
		
		return dao.findAll();
		
	}

}
