package model.services;

import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
import gui.PrincipalFormController;
import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoService {

	private ProdutoDao dao = DaoFactory.createProdutoDao();

	public List<Produto> findAll() {

		return dao.findAll();

	}

	public void produtoNovoOuEditar(Produto produto) {

		if (produto.getIdProduto() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar o novo produto " + produto.getNome().toUpperCase() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.insert(produto);
				
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Produto criado: " + produto.getNome().toUpperCase());

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar a edição do produto " + produto.getNome().toUpperCase() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.update(produto);
				
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Produto editado: " + produto.getNome().toUpperCase());


			}

		}

	}

	public void remove(Produto produto) {

		dao.deleteById(produto.getIdProduto());
		
		new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
				"Produto excluído: " + produto.getNome().toUpperCase());


	}

	public Produto findById(Integer id) {

		PrincipalFormController.setProduto(dao.findById(id));
		return PrincipalFormController.getProduto();

	}

	public List<Produto> PesquisarNomeProduto(String nomeProduto) {

		return dao.findNomeProduto(nomeProduto);

	}

	public List<Produto> PesquisarNomeSetor(String nomeSetor) {

		return dao.findNomeSetor(nomeSetor);

	}

}
