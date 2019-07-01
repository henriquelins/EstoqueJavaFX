package model.entities;


import java.io.Serializable;
import java.sql.Date;


public class Movimentacao implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idMovimentacao;
	private int idProduto;
	private int idUsuario;
	private String tipo;
	private int valorMovimento;
	private String observacoesMovimentacao;
	private int quantidadeAnterior;
	private Date dataDaTransacao;
	
	public Movimentacao() {}

	public Movimentacao(int idMovimentacao, int idProduto, int idUsuario, String tipo, int valorMovimento,
			String observacoesMovimentacao, int quantidadeAnterior, Date dataDaTransacao) {
	
		this.idMovimentacao = idMovimentacao;
		this.idProduto = idProduto;
		this.idUsuario = idUsuario;
		this.tipo = tipo;
		this.valorMovimento = valorMovimento;
		this.observacoesMovimentacao = observacoesMovimentacao;
		this.quantidadeAnterior = quantidadeAnterior;
		this.dataDaTransacao = dataDaTransacao;
	}

	public int getIdMovimentacao() {
		return idMovimentacao;
	}

	public void setIdMovimentacao(int idMovimentacao) {
		this.idMovimentacao = idMovimentacao;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDaTransacao == null) ? 0 : dataDaTransacao.hashCode());
		result = prime * result + idMovimentacao;
		result = prime * result + idProduto;
		result = prime * result + idUsuario;
		result = prime * result + ((observacoesMovimentacao == null) ? 0 : observacoesMovimentacao.hashCode());
		result = prime * result + quantidadeAnterior;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		if (idMovimentacao != other.idMovimentacao)
			return false;
		if (idProduto != other.idProduto)
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		if (observacoesMovimentacao == null) {
			if (other.observacoesMovimentacao != null)
				return false;
		} else if (!observacoesMovimentacao.equals(other.observacoesMovimentacao))
			return false;
		if (quantidadeAnterior != other.quantidadeAnterior)
			return false;
		if (tipo != other.tipo)
			return false;
		if (valorMovimento != other.valorMovimento)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movimentacao [idMovimentacao=" + idMovimentacao + ", idProduto=" + idProduto + ", idUsuario="
				+ idUsuario + ", tipo=" + tipo + ", valorMovimento=" + valorMovimento + ", observacoesMovimentacao="
				+ observacoesMovimentacao + ", quantidadeAnterior=" + quantidadeAnterior + ", dataDaTransacao="
				+ dataDaTransacao + "]";
	}

	
}

