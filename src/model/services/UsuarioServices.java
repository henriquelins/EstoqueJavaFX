package model.services;

import model.entities.Usuario;

public class UsuarioServices {

	private Usuario logado = new Usuario();

	public Usuario login(Usuario usuario) {

		this.logado = usuario;

		return logado;
	}

}
