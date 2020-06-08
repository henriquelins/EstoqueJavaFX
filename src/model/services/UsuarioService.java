package model.services;

import java.util.List;
import java.util.Optional;

import gui.LoginFormController;
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

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja salvar o usu�rio " + usuario.getNome() + " ?");

			if (result.get() == ButtonType.OK) {
				
				dao.insert(usuario);
				
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Usu�rio criado: " + usuario.getNome().toUpperCase());
			}

		} else {

			Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o",
					"Voc� deseja editar o usu�rio " + usuario.getNome() + " ?");

			if (result.get() == ButtonType.OK) {

				dao.update(usuario);
				
				new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
						"Usu�rio editado: " + usuario.getNome().toUpperCase());

			}

		}
	}

	public void remove(Usuario usuario) {

		dao.deleteById(usuario.getIdUsuario());
		new LogSegurancaService().novoLogSeguranca(LoginFormController.getLogado().getNome(),
				"Usu�rio exclu�do: " + usuario.getNome().toUpperCase());

	}

	public List<Usuario> findAll() {
		return dao.findAll();
	}
	
	public Usuario find(int id) {
		
		return dao.findById(id);
	}
	
}
