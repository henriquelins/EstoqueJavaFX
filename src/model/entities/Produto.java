package model.entities;

import java.io.Serializable;

public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idProduto;
	private String nome;
	private String descricao;
	private String setor;
	private String categoria;
	private int quantidade;

	public Produto() {
	}

	public Produto(Integer idProduto, String nome, String descricao, String setor, String categoria, int quantidade) {

		this.idProduto = idProduto;
		this.nome = nome;
		this.descricao = descricao;
		this.setor = setor;
		this.categoria = categoria;
		this.quantidade = quantidade;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "Produto [idProduto=" + idProduto + ", nome=" + nome + ", descricao=" + descricao + ", setor=" + setor
				+ ", categoria=" + categoria + ", quantidade=" + quantidade + "]";
	}

}
