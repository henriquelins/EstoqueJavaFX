package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SetorDao;
import model.entities.Setor;

public class SetorService {

	Setor setor = new Setor();

	private SetorDao dao = DaoFactory.createSetorDao();

	public void setorNovoOuEditar(Setor setor) {

		if (setor.getIdSetor() == null) {
			dao.insert(setor);
		} else {
			dao.update(setor);
		}
	}

	public void remove(Setor setor) {
		dao.deleteById(setor.getIdSetor());
	}

	public List<Setor> findAll() {
		return dao.findAll();
	}
}
