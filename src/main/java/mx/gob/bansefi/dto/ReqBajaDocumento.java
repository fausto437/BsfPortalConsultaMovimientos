package mx.gob.bansefi.dto;

import lombok.Getter;
import lombok.Setter;

public class ReqBajaDocumento {
	@Getter @Setter
    public String idInternoPe;
	@Getter @Setter
    public String idInternoDoc;
	@Getter @Setter
    public String entidad;
	@Getter @Setter
    public String codigoDocumento;
	@Getter @Setter
    public String terminal;
	@Getter @Setter
    public String bsfOperador;
}

