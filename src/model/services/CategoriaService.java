package model.services;

import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.entities.Categoria;

public class CategoriaService {

	Categoria categoria = new Categoria();

	private CategoriaDao dao = DaoFactory.createCategoriaDao();

	public void categoriaNovoOuEditar(Categoria categoria) {

		if (categoria.getIdCategoria() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar a categoria " + categoria.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.insert(categoria);
				
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Categoria criada: " + categoria.getNome().toUpperCase());


			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja editar a categoria " + categoria.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.update(categoria);
				
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Categoria editada: " + categoria.getNome().toUpperCase());

			}

		}
	}

	public void remove(Categoria categoria) {

		dao.deleteById(categoria.getIdCategoria());
		
		new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
				"Categoria excluída: " + categoria.getNome().toUpperCase());

	}

	public List<Categoria> findAllId() {

		return dao.findAllId();

	}

	public List<Categoria> findAllNome() {

		return dao.findAllNome();

	}

	public List<Categoria> findIdSetor(int id_setor) {

		return dao.findIdSetor(id_setor);

	}

}
