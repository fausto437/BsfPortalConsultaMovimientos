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
	private String fechaOperacion;
	@Getter @Setter
	private String fechaValor;
	@Getter @Setter
	private String ofiTerminal;
	@Getter @Setter
	private String signo;
	@Getter @Setter
	private String importe;
	@Getter	@Setter
	private String saldo;
	@Getter	@Setter
	private String codigoError;
	@Getter	@Setter
	private String descripcionError;
	@Getter	@Setter
	private String detalle;
	@Getter	@Setter
	private String codcuenta;
	@Getter	@Setter
	private String codorigen;
	@Getter	@Setter
	private String codapunte;
	@Getter	@Setter
	private String idOrigen;
}
