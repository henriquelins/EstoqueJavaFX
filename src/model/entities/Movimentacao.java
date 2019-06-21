package model.entities;


import java.sql.Date;


public class Movimentacao {

	private int idMovimentacao;
	private int tipoDaMovimentacao;
	private Date dataDaTransacao;
	private int quantidade;
	private int estoqueAnterior;
	private int estoqueAtual;
	private int idProduto;
	private int idUsuario;
	
	public Movimentacao() {}
	
	public Movimentacao(int idMovimentacao, int tipoDaMovimentacao, Date dataDaTransacao, int quantidade,
			int estoqueAnterior, int estoqueAtual, int idProduto, int idUsuario) {
		
		this.idMovimentacao = idMovimentacao;
		this.tipoDaMovimentacao = tipoDaMovimentacao;
		this.dataDaTransacao = dataDaTransacao;
		this.quantidade = quantidade;
		this.estoqueAnterior = estoqueAnterior;
		this.estoqueAtual = estoqueAtual;
		this.idProduto = idProduto;
		this.idUsuario = idUsuario;
	}

	public int getIdMovimentacao() {
		return idMovimentacao;
	}

	public void setIdMovimentacao(int idMovimentacao) {
		this.idMovimentacao = idMovimentacao;
	}

	public int getTipoDaMovimentacao() {
		return tipoDaMovimentacao;
	}

	public void setTipoDaMovimentacao(int tipoDaMovimentacao) {
		this.tipoDaMovimentacao = tipoDaMovimentacao;
	}

	public Date getDataDaTransacao() {
		return dataDaTransacao;
	}

	public void setDataDaTransacao(Date dataDaTransacao) {
		this.dataDaTransacao = dataDaTransacao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getEstoqueAnterior() {
		return estoqueAnterior;
	}

	public void setEstoqueAnterior(int estoqueAnterior) {
		this.estoqueAnterior = estoqueAnterior;
	}

	public int getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(int estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDaTransacao == null) ? 0 : dataDaTransacao.hashCode());
		result = prime * result + estoqueAnterior;
		result = prime * result + estoqueAtual;
		result = prime * result + idMovimentacao;
		result = prime * result + idProduto;
		result = prime * result + idUsuario;
		result = prime * result + quantidade;
		result = prime * result + tipoDaMovimentacao;
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
		if (estoqueAnterior != other.estoqueAnterior)
			return false;
		if (estoqueAtual != other.estoqueAtual)
			return false;
		if (idMovimentacao != other.idMovimentacao)
			return false;
		if (idProduto != other.idProduto)
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		if (quantidade != other.quantidade)
			return false;
		if (tipoDaMovimentacao != other.tipoDaMovimentacao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movimentacao [idMovimentacao=" + idMovimentacao + ", tipoDaMovimentacao=" + tipoDaMovimentacao
				+ ", dataDaTransacao=" + dataDaTransacao + ", quantidade=" + quantidade + ", estoqueAnterior="
				+ estoqueAnterior + ", estoqueAtual=" + estoqueAtual + ", idProduto=" + idProduto + ", idUsuario="
				+ idUsuario + "]";
	}

	
}

