package mx.gob.bansefi.dto;

public class BsfOperadorDTO {

	private BsfOperadorIDTO BSFOPERADOR;

	
	public BsfOperadorDTO() {
		super();
		BSFOPERADOR = new BsfOperadorIDTO();
	}

	public BsfOperadorIDTO getBSFOPERADOR() {
		return BSFOPERADOR;
	}

	public void setBSFOPERADOR(BsfOperadorIDTO bSFOPERADOR) {
		BSFOPERADOR = bSFOPERADOR;
	}
}
