package model.entities.enums;

public enum Categoria {

	TINTAS_CRACHAS("Tintas crach�s"), INSUMOS_CRACHAS("Insumos crach�s"), INSUMOS_LAMINACAO("Insumos Lamina��o"),
	EQUIPAMENTOS_SUPORTE("Equipamentos Suporte"), INSUMOS_SUPORTE("Insumos suporte"),
	MATERIAL_ESCRITORIO("Material escrit�rio");

	private String nomeCategoria;

	Categoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	 @Override
	 public String toString() {
		return nomeCategoria;
	}

}
