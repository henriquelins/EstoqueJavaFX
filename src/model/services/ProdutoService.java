package model.services;

import java.util.List;
import java.util.Optional;

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
					"Você deseja salvar o novo  " + produto.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.insert(produto);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar a edição do produto " + produto.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.update(produto);

			}

		}
	}

	public void remove(Produto produto) {

		dao.deleteById(produto.getIdProduto());

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
