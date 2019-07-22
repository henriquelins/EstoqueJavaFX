package model.entities;

import java.io.Serializable;
import java.util.Arrays;

public class Foto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idFoto;
	private byte[] foto;
	private String local;

	public Foto() {

	}

	public Foto(int idFoto, byte[] foto, String local) {

		this.idFoto = idFoto;
		this.foto = foto;
		this.local = local;

	}

	public int getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getLocal() {
		return local;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(foto);
		result = prime * result + idFoto;
		result = prime * result + ((local == null) ? 0 : local.hashCode());
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
		Foto other = (Foto) obj;
		if (!Arrays.equals(foto, other.foto))
			return false;
		if (idFoto != other.idFoto)
			return false;
		if (local == null) {
			if (other.local != null)
				return false;
		} else if (!local.equals(other.local))
			return false;
		return true;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@Override
	public String toString() {
		return "Foto [idFoto=" + idFoto + ", foto=" + Arrays.toString(foto) + ", local=" + local + "]";
	}

}
