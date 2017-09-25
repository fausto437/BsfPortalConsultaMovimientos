package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 4/28/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class GralApunteDTO {
	@Getter @Setter
	private String concepto;
	@Getter @Setter
	private String fecOperacion;
	@Getter @Setter
	private String fecValor;
	@Getter @Setter
	private String ofTerminal;
	@Getter @Setter
	private String signo;
	@Getter @Setter
	private String importe;
	@Getter	@Setter
	private String saldo;
}
