package model.services;

import java.util.List;
import java.util.Optional;

import gui.util.Alerts;
import javafx.scene.control.ButtonType;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioService {

	Usuario user = new Usuario();

	private UsuarioDao dao = DaoFactory.createUsuarioDao();

	public Usuario login(Usuario usuario) {
		user = dao.login(usuario);
		return user;
	}

	public void usuarioNovoOuEditar(Usuario usuario) {

		if (usuario.getIdUsuario() == null) {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja salvar o usuário " + usuario.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.insert(usuario);

			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirmação",
					"Você deseja editar o usuário " + usuario.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.update(usuario);

			}

		}
	}

	public void remove(Usuario usuario) {

		dao.deleteById(usuario.getIdUsuario());

	}

	public List<Usuario> findAll() {
		return dao.findAll();
	}
}
