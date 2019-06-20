package model.entities;

public class Usuario {

	private Integer idUsuario;
	private String name;
	private String login;
	private String senha;
	
	public Usuario() {}

	public Usuario(Integer idUsuario, String name, String login, String senha) {
		this.idUsuario = idUsuario;
		this.name = name;
		this.login = login;
		this.senha = senha;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Usuário logado: " + login + "";
	}


	
}
