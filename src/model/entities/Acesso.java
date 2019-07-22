package model.entities;

import java.io.Serializable;

public class Acesso implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idAcesso;
	private boolean usuarioCadastro = false;
	private boolean usuarioNovo = false;
	private boolean usuarioEditar = false;
	private boolean usuarioExcluir = false;
	private boolean setorCadastro = false;
	private boolean setorNovo = false;
	private boolean setorEditar = false;
	private boolean setorExcluir = false;
	private boolean categoriaCadastro = false;
	private boolean categoriaNovo = false;
	private boolean categoriaEditar = false;
	private boolean categoriaExcluir = false;
	private boolean produtoNovo = false;
	private boolean produtoEditar = false;
	private boolean produtoExcluir = false;
	private boolean movimentacao = false;

	public Acesso() {
	}
	
	public Acesso(int idAcesso, boolean usuarioCadastro, boolean usuarioNovo, boolean usuarioEditar,
			boolean usuarioExcluir, boolean setorCadastro, boolean setorNovo, boolean setorEditar, boolean setorExcluir,
			boolean categoriaCadastro, boolean categoriaNovo, boolean categoriaEditar, boolean categoriaExcluir,
			boolean produtoNovo, boolean produtoEditar, boolean produtoExcluir, boolean movimentacao) {

		this.idAcesso = idAcesso;
		this.usuarioCadastro = usuarioCadastro;
		this.usuarioNovo = usuarioNovo;
		this.usuarioEditar = usuarioEditar;
		this.usuarioExcluir = usuarioExcluir;
		this.setorCadastro = setorCadastro;
		this.setorNovo = setorNovo;
		this.setorEditar = setorEditar;
		this.setorExcluir = setorExcluir;
		this.categoriaCadastro = categoriaCadastro;
		this.categoriaNovo = categoriaNovo;
		this.categoriaEditar = categoriaEditar;
		this.categoriaExcluir = categoriaExcluir;
		this.produtoNovo = produtoNovo;
		this.produtoEditar = produtoEditar;
		this.produtoExcluir = produtoExcluir;
		this.movimentacao = movimentacao;

	}

	public int getIdAcesso() {
		return idAcesso;
	}

	public void setIdAcesso(int idAcesso) {
		this.idAcesso = idAcesso;
	}

	public boolean isUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(boolean usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public boolean isUsuarioNovo() {
		return usuarioNovo;
	}

	public void setUsuarioNovo(boolean usuarioNovo) {
		this.usuarioNovo = usuarioNovo;
	}

	public boolean isUsuarioEditar() {
		return usuarioEditar;
	}

	public void setUsuarioEditar(boolean usuarioEditar) {
		this.usuarioEditar = usuarioEditar;
	}

	public boolean isUsuarioExcluir() {
		return usuarioExcluir;
	}

	public void setUsuarioExcluir(boolean usuarioExcluir) {
		this.usuarioExcluir = usuarioExcluir;
	}

	public boolean isSetorCadastro() {
		return setorCadastro;
	}

	public void setSetorCadastro(boolean setorCadastro) {
		this.setorCadastro = setorCadastro;
	}

	public boolean isSetorNovo() {
		return setorNovo;
	}

	public void setSetorNovo(boolean setorNovo) {
		this.setorNovo = setorNovo;
	}

	public boolean isSetorEditar() {
		return setorEditar;
	}

	public void setSetorEditar(boolean setorEditar) {
		this.setorEditar = setorEditar;
	}

	public boolean isSetorExcluir() {
		return setorExcluir;
	}

	public void setSetorExcluir(boolean setorExcluir) {
		this.setorExcluir = setorExcluir;
	}

	public boolean isCategoriaCadastro() {
		return categoriaCadastro;
	}

	public void setCategoriaCadastro(boolean categoriaCadastro) {
		this.categoriaCadastro = categoriaCadastro;
	}

	public boolean isCategoriaNovo() {
		return categoriaNovo;
	}

	public void setCategoriaNovo(boolean categoriaNovo) {
		this.categoriaNovo = categoriaNovo;
	}

	public boolean isCategoriaEditar() {
		return categoriaEditar;
	}

	public void setCategoriaEditar(boolean categoriaEditar) {
		this.categoriaEditar = categoriaEditar;
	}

	public boolean isCategoriaExcluir() {
		return categoriaExcluir;
	}

	public void setCategoriaExcluir(boolean categoriaExcluir) {
		this.categoriaExcluir = categoriaExcluir;
	}

	public boolean isProdutoNovo() {
		return produtoNovo;
	}

	public void setProdutoNovo(boolean produtoNovo) {
		this.produtoNovo = produtoNovo;
	}

	public boolean isProdutoEditar() {
		return produtoEditar;
	}

	public void setProdutoEditar(boolean produtoEditar) {
		this.produtoEditar = produtoEditar;
	}

	public boolean isProdutoExcluir() {
		return produtoExcluir;
	}

	public void setProdutoExcluir(boolean produtoExcluir) {
		this.produtoExcluir = produtoExcluir;
	}

	public boolean isMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(boolean movimentacao) {
		this.movimentacao = movimentacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (categoriaCadastro ? 1231 : 1237);
		result = prime * result + (categoriaEditar ? 1231 : 1237);
		result = prime * result + (categoriaExcluir ? 1231 : 1237);
		result = prime * result + (categoriaNovo ? 1231 : 1237);
		result = prime * result + idAcesso;
		result = prime * result + (movimentacao ? 1231 : 1237);
		result = prime * result + (produtoEditar ? 1231 : 1237);
		result = prime * result + (produtoExcluir ? 1231 : 1237);
		result = prime * result + (produtoNovo ? 1231 : 1237);
		result = prime * result + (setorCadastro ? 1231 : 1237);
		result = prime * result + (setorEditar ? 1231 : 1237);
		result = prime * result + (setorExcluir ? 1231 : 1237);
		result = prime * result + (setorNovo ? 1231 : 1237);
		result = prime * result + (usuarioCadastro ? 1231 : 1237);
		result = prime * result + (usuarioEditar ? 1231 : 1237);
		result = prime * result + (usuarioExcluir ? 1231 : 1237);
		result = prime * result + (usuarioNovo ? 1231 : 1237);
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
		Acesso other = (Acesso) obj;
		if (categoriaCadastro != other.categoriaCadastro)
			return false;
		if (categoriaEditar != other.categoriaEditar)
			return false;
		if (categoriaExcluir != other.categoriaExcluir)
			return false;
		if (categoriaNovo != other.categoriaNovo)
			return false;
		if (idAcesso != other.idAcesso)
			return false;
		if (movimentacao != other.movimentacao)
			return false;
		if (produtoEditar != other.produtoEditar)
			return false;
		if (produtoExcluir != other.produtoExcluir)
			return false;
		if (produtoNovo != other.produtoNovo)
			return false;
		if (setorCadastro != other.setorCadastro)
			return false;
		if (setorEditar != other.setorEditar)
			return false;
		if (setorExcluir != other.setorExcluir)
			return false;
		if (setorNovo != other.setorNovo)
			return false;
		if (usuarioCadastro != other.usuarioCadastro)
			return false;
		if (usuarioEditar != other.usuarioEditar)
			return false;
		if (usuarioExcluir != other.usuarioExcluir)
			return false;
		if (usuarioNovo != other.usuarioNovo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Acesso [idAcesso=" + idAcesso + ", usuarioCadastro=" + usuarioCadastro + ", usuarioNovo=" + usuarioNovo
				+ ", usuarioEditar=" + usuarioEditar + ", usuarioExcluir=" + usuarioExcluir + ", setorCadastro="
				+ setorCadastro + ", setorNovo=" + setorNovo + ", setorEditar=" + setorEditar + ", setorExcluir="
				+ setorExcluir + ", categoriaCadastro=" + categoriaCadastro + ", categoriaNovo=" + categoriaNovo
				+ ", categoriaEditar=" + categoriaEditar + ", categoriaExcluir=" + categoriaExcluir + ", produtoNovo="
				+ produtoNovo + ", produtoEditar=" + produtoEditar + ", produtoExcluir=" + produtoExcluir
				+ ", movimentacao=" + movimentacao + "]";
	}

}
