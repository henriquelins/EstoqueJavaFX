package model.services;

import java.util.List;

import gui.PrincipalFormController;
import gui.util.Utils;
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
			dao.insert(produto);
			Utils.fecharDialogAction();
		} else {		
			dao.update(produto);
			Utils.fecharDialogAction();
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
