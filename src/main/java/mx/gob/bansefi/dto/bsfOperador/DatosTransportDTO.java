package mx.gob.bansefi.dto.bsfOperador;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 4/28/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class DatosTransportDTO {
	@Getter	@Setter
	private String BSFOPERADORINICIO;
	@Getter	@Setter
	private int IDINTERNOPE;
	@Getter	@Setter
	private String DESCDOC;
	@Getter	@Setter
	private String ACUERDO;
	@Getter	@Setter
	private String CODDOC;
	@Getter	@Setter
	private String TITULAR;
}
