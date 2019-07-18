package model.services;

import java.util.List;
import java.util.Optional;

import gui.PrincipalFormController;
import gui.util.Alerts;
import gui.util.Utils;
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

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Você deseja salvar um novo produto?");

			if (result.get() == ButtonType.OK) {

				dao.insert(produto);
				Utils.fecharDialogAction();

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Você deseja salvar a edição do produto?");

			if (result.get() == ButtonType.OK) {

				dao.update(produto);
				Utils.fecharDialogAction();

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

}
