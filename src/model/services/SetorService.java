package model.services;

import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
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

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar o setor " + setor.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.insert(setor);
				
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Setor criado: " + setor.getNome().toUpperCase());

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja editar o setor " + setor.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.update(setor);
				
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Setor editado: " + setor.getNome().toUpperCase());


			}

		}
	}

	public void remove(Setor setor) {

		dao.deleteById(setor.getIdSetor());
		
		new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
				"Setor excluido: " + setor.getNome().toUpperCase());


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
