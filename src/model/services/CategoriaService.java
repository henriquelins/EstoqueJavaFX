package model.services;

import java.util.List;

import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.entities.Categoria;

public class CategoriaService {

	Categoria categoria = new Categoria();

	private CategoriaDao dao = DaoFactory.createCategoriaDao();

	public void categoriaNovoOuEditar(Categoria categoria) {

		if (categoria.getIdCategoria() == null) {
			dao.insert(categoria);
		} else {
			dao.update(categoria);
		}
	}

	public void remove(Categoria categoria) {
		dao.deleteById(categoria.getIdCategoria());
	}

	public List<Categoria> findAll() {
		return dao.findAll();
	}

}
