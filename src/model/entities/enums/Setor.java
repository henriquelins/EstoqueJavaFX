package model.entities.enums;

public enum Setor {

	CRACHAS("Crach�s"), SUPORTE("Suporte");

	private String nomeSETOR;

	Setor(String nomeSETOR) {
		this.nomeSETOR = nomeSETOR;
	}
	
	 @Override
	 public String toString() {
		return nomeSETOR;
	}

}
