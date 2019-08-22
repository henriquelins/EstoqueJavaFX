package model.entities;

import java.io.Serializable;

public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idCategoria;
	private String nome;
	private Integer idSetor;

	public Categoria() {}

	public Categoria(Integer idCategoria, String nome, Integer idSetor) {

		this.idCategoria = idCategoria;
		this.nome = nome;
		this.idSetor = idSetor;

	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdSetor() {
		return idSetor;
	}

	public void setIdSetor(Integer idSetor) {
		this.idSetor = idSetor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());
		result = prime * result + ((idSetor == null) ? 0 : idSetor.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		if (idSetor == null) {
			if (other.idSetor != null)
				return false;
		} else if (!idSetor.equals(other.idSetor))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;	
	}

	@Override
	public String toString() {	
		return "Categoria [idCategoria=" + idCategoria + ", nome=" + nome + ", idSetor=" + idSetor + "]";	
	}

}
