package model.entities.enums;

public enum Categoria {

	TINTAS_CRACHAS("Tintas crachás"), INSUMOS_CRACHAS("Insumos crachás"), INSUMOS_LAMINACAO("Insumos Laminação"),
	EQUIPAMENTOS_SUPORTE("Equipamentos Suporte"), INSUMOS_SUPORTE("Insumos suporte"),
	MATERIAL_ESCRITORIO("Material escritório");

	private String nomeCategoria;

	Categoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	 @Override
	 public String toString() {
		return nomeCategoria;
	}

}
