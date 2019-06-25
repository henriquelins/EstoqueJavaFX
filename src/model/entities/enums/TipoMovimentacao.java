package model.entities.enums;

public enum TipoMovimentacao {
	
	ENTRADA_DE_PRODUTO ("Entrada do produtos"),
	AJUSTE_DE_ENTRADA ("Ajuste de entrada"),
	SAIDA_DE_PRODUTO ("Sa�da de produtos"),
	AJUSTE_DE_SAIDA ("Ajuste de sa�da");
	
	private String nomeMovimentacao;

	TipoMovimentacao(String nomeMovimentacao) {
		this.nomeMovimentacao = nomeMovimentacao;
	}

	 @Override
	 public String toString() {
		return nomeMovimentacao;
	}
	
}
