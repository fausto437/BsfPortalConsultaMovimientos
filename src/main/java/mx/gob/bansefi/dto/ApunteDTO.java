package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class ApunteDTO{
	@Getter @Setter
	private String concepto;
	@Getter @Setter
	private String fechaoperacion;
	@Getter @Setter
	private String fechavalor;
	@Getter @Setter
	private String ofterminal;
	@Getter @Setter
	private String signo;
	@Getter @Setter
	private String importe;
	@Getter	@Setter
	private String saldo;
	@Getter	@Setter
	private String codigoerror;
	@Getter	@Setter
	private String descripcionerror;
	@Getter	@Setter
	private String detalle;
	@Getter	@Setter
	private String codcuenta;
	@Getter	@Setter
	private String codorigen;
	@Getter	@Setter
	private String codapunte;
	@Getter	@Setter
	private String idorigen;
}