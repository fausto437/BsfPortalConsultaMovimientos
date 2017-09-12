package mx.gob.bansefi.dto;

import lombok.Getter;
import lombok.Setter;

public class DatosEncryptDigitaliza {

	private String BSFOPERADOR;
	private String idInternoPe;
	private String tipoDoc;
	@Getter @Setter
	private String descDoc;
	@Getter @Setter
	private String alta;
	@Getter @Setter
	private String nombre;

	public DatosEncryptDigitaliza() {
		super();
	}
	public String getBSFOPERADOR() {
		return BSFOPERADOR;
	}
	public void setBSFOPERADOR(String bSFOPERADOR) {
		BSFOPERADOR = bSFOPERADOR;
	}
	public String getIdInternoPe() {
		return idInternoPe;
	}
	public void setIdInternoPe(String idInternoPe) {
		this.idInternoPe = idInternoPe;
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
}
