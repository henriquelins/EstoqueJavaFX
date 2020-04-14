package model.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

			case ("ENTRADA DE PRODUTOS (+)"):

				Optional<ButtonType> result1 = Alerts.showConfirmation("Confirmação",
						"Você deseja dar entrada no estoque do produto " + movimentacao.getProduto().getNome().toUpperCase() + " ?");

				if (result1.get() == ButtonType.OK) {

					estoqueAtual = movimentacao.getQuantidadeAnterior() + movimentacao.getValorMovimento();
					daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());
					dao.insert(movimentacao);
					
					new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
							"Movimentação: " + movimentacao.getTipo() + " - " + estoqueAtual);


				} else {
					
					Alerts.showAlert("Movimentação", "Erro", "Não foi possível fazer corretamenta a movimentação",
							AlertType.INFORMATION);
					
				}

				break;

			case ("SAÍDA DE PRODUTOS (-)"):

				Optional<ButtonType> result2 = Alerts.showConfirmation("Confirmação",
						"Você deseja dar saída no estoque do produto " + movimentacao.getProduto().getNome().toUpperCase() + " ?");

				if (result2.get() == ButtonType.OK) {

					estoqueAtual = movimentacao.getQuantidadeAnterior() - movimentacao.getValorMovimento();
					daoProd.updateEstoqueAtual(estoqueAtual, movimentacao.getProduto().getIdProduto());
					dao.insert(movimentacao);
					
					new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
							"Movimentação: " + movimentacao.getTipo() + " - " + estoqueAtual);

				} else {
					
					Alerts.showAlert("Movimentação", "Erro", "Não foi possível fazer corretamenta a movimentação",
							AlertType.INFORMATION);
					
				}

				break;

			}

			Utils.fecharDialogAction();

		} catch (Exception e) {

			//Alerts.showAlert("Movimentação", null, e.getLocalizedMessage(), AlertType.ERROR);

		}
	}

	public List<Movimentacao> findAll() {

		return dao.findAll();

	}

	public List<Movimentacao> pesquisarNomeProduto(String pesquisarProduto) {

		return dao.findNomeProduto(pesquisarProduto);

	}

	public List<Movimentacao> pesquisarNomeSetor(String pesquisarSetor) {

		return dao.findNomeSetor(pesquisarSetor);

	}
	
	
	public List<Movimentacao> verMovimentacao(Date DataInicial, Date DataFinal, int id_produto) {

		return dao.verMovimentacao(DataInicial, DataFinal, id_produto);

	}
	
	
}
