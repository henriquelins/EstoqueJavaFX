package model.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
import gui.util.Alerts;
import gui.util.Constraints;
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
				
				estoqueAtual = movimentacao.getQuantidadeAnterior() + movimentacao.getValorMovimento();
			
			
			
				Optional<ButtonType> result1 = Alerts.showConfirmation("Confirmação",
						"Entrada no produto " + movimentacao.getProduto().getNome().toUpperCase() + " ? "
								+ "Com a entrada de " + Constraints.tresDigitos(movimentacao.getValorMovimento()) 
								+ " unid(s) adicionado(s) ao saldo atual " + Constraints.tresDigitos(movimentacao.getQuantidadeAnterior()) 
								+ " unid(s), o novo saldo será de " + Constraints.tresDigitos(estoqueAtual) 
								+ " unid(s).");

				if (result1.get() == ButtonType.OK) {

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
				
				estoqueAtual = movimentacao.getQuantidadeAnterior() - movimentacao.getValorMovimento();

				Optional<ButtonType> result2 = Alerts.showConfirmation("Confirmação",
						"Saída no produto " + movimentacao.getProduto().getNome().toUpperCase() + " ? "
								+ "Com a saída de " + Constraints.tresDigitos(movimentacao.getValorMovimento()) 
								+ " unid(s) subtraído(s) do saldo atual de " + Constraints.tresDigitos(movimentacao.getQuantidadeAnterior()) 
								+ " unid(s), o novo saldo será de " + Constraints.tresDigitos(estoqueAtual) 
								+ " unid(s).");

				if (result2.get() == ButtonType.OK) {

					
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
