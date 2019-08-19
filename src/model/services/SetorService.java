package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.SetorDao;
import model.entities.Setor;

public class SetorService {

	Setor setor = new Setor();

	private SetorDao dao = DaoFactory.createSetorDao();

	public void setorNovoOuEditar(Setor setor) {

		if (setor.getIdSetor() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Você deseja salvar o setor " + setor.getNome()+ " ?");

			if (result.get() == ButtonType.OK) {

				dao.insert(setor);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Você deseja editar o setor " + setor.getNome()+ " ?");

			if (result.get() == ButtonType.OK) {

				dao.update(setor);

			}

		}
	}

	public void remove(Setor setor) {
		
		dao.deleteById(setor.getIdSetor());
		
	}

	public List<Setor> findAllId() {
		
		return dao.findAllId();
		
	}
	
	public Integer findNomeIdSetor(String nomeSetor) {
		
		return dao.findIdSetor(nomeSetor);
		
	}

	public List<Setor> findAllNome() {
		
		return dao.findAllNome();
	}
	
}
