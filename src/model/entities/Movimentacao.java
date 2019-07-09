package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Movimentacao implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idMovimentacao;
	private Produto produto;
	private Usuario usuario;
	private String tipo;
	private int valorMovimento;
	private String observacoesMovimentacao;
	private int quantidadeAnterior;
	private Date dataDaTransacao;
	private int estoqueAtual;

	public Movimentacao() {
	}

	public Movimentacao(int idMovimentacao, Produto produto, Usuario usuario, String tipo, int valorMovimento,
			String observacoesMovimentacao, int quantidadeAnterior, Date dataDaTransacao, int estoqueAtual) {
		this.idMovimentacao = idMovimentacao;
		this.produto = produto;
		this.usuario = usuario;
		this.tipo = tipo;
		this.valorMovimento = valorMovimento;
		this.observacoesMovimentacao = observacoesMovimentacao;
		this.quantidadeAnterior = quantidadeAnterior;
		this.dataDaTransacao = dataDaTransacao;
		this.estoqueAtual = estoqueAtual;
	}

	public int getIdMovimentacao() {
		return idMovimentacao;
	}

	public void setIdMovimentacao(int idMovimentacao) {
		this.idMovimentacao = idMovimentacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getValorMovimento() {
		return valorMovimento;
	}

	public void setValorMovimento(int valorMovimento) {
		this.valorMovimento = valorMovimento;
	}

	public String getObservacoesMovimentacao() {
		return observacoesMovimentacao;
	}

	public void setObservacoesMovimentacao(String observacoesMovimentacao) {
		this.observacoesMovimentacao = observacoesMovimentacao;
	}

	public int getQuantidadeAnterior() {
		return quantidadeAnterior;
	}

	public void setQuantidadeAnterior(int quantidadeAnterior) {
		this.quantidadeAnterior = quantidadeAnterior;
	}

	public Date getDataDaTransacao() {
		return dataDaTransacao;
	}

	public void setDataDaTransacao(Date dataDaTransacao) {
		this.dataDaTransacao = dataDaTransacao;
	}

	public int getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(int estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDaTransacao == null) ? 0 : dataDaTransacao.hashCode());
		result = prime * result + estoqueAtual;
		result = prime * result + idMovimentacao;
		result = prime * result + ((observacoesMovimentacao == null) ? 0 : observacoesMovimentacao.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + quantidadeAnterior;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + valorMovimento;
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
		Movimentacao other = (Movimentacao) obj;
		if (dataDaTransacao == null) {
			if (other.dataDaTransacao != null)
				return false;
		} else if (!dataDaTransacao.equals(other.dataDaTransacao))
			return false;
		if (estoqueAtual != other.estoqueAtual)
			return false;
		if (idMovimentacao != other.idMovimentacao)
			return false;
		if (observacoesMovimentacao == null) {
			if (other.observacoesMovimentacao != null)
				return false;
		} else if (!observacoesMovimentacao.equals(other.observacoesMovimentacao))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (quantidadeAnterior != other.quantidadeAnterior)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (valorMovimento != other.valorMovimento)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movimentacao [idMovimentacao=" + idMovimentacao + ", produto=" + produto + ", usuario=" + usuario
				+ ", tipo=" + tipo + ", valorMovimento=" + valorMovimento + ", observacoesMovimentacao="
				+ observacoesMovimentacao + ", quantidadeAnterior=" + quantidadeAnterior + ", dataDaTransacao="
				+ dataDaTransacao + ", estoqueAtual=" + estoqueAtual + "]";
	}

}
