package model.entities.enums;

public enum TipoMovimentacao {
	
	ENTRADA_DE_PRODUTO ("Entrada do produtos"),
	AJUSTE_DE_ENTRADA ("Ajuste de entrada"),
	SAIDA_DE_PRODUTO ("Saída de produtos"),
	AJUSTE_DE_SAIDA ("Ajuste de saída");
	
	private String nomeMovimentacao;

	TipoMovimentacao(String nomeMovimentacao) {
		this.nomeMovimentacao = nomeMovimentacao;
	}

	 @Override
	 public String toString() {
		return nomeMovimentacao;
	}
	
}
