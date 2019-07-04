package model.services;

import java.util.List;

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
			dao.insert(usuario);
		} else {
			dao.update(usuario);
		}
	}

	public void remove(Usuario usuario) {
		dao.deleteById(usuario.getIdUsuario());
	}

	public List<Usuario> findAll() {
		return dao.findAll();
	}
}
