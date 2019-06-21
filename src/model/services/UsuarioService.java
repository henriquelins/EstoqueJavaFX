package model.services;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.scene.control.Alert.AlertType;
import model.entities.Usuario;

public class UsuarioService {

	Usuario user = new Usuario();

	public Usuario login(Usuario usuario) {

		String login = "adm";
		String senha = "10";

		if (login.equals(usuario.getLogin()) == true && senha.equals(usuario.getSenha())) {

			user.setLogin(login);
			user.setSenha(senha);

		} else {

			user.setLogin(null);
			user.setSenha(null);
		}

		return user;

	}

	public void usuarioNovoOuEditar(Usuario usuario) {

		if (usuario.getIdUsuario() == null) {

			Alerts.showAlert("Usuário", null, "Novo Usuário", AlertType.ERROR);
						
			Utils.fecharDialogAction();
		
		} else {

			Alerts.showAlert("Usuário", null, "Editar Usuário", AlertType.ERROR);
						
			Utils.fecharDialogAction();
		
		}

	}

}
