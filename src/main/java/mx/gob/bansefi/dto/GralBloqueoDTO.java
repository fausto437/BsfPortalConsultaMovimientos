package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 12/09/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class GralBloqueoDTO {
	@Getter @Setter
	private String tipo;
	@Getter @Setter
	private String concepto;
	@Getter @Setter
	private String estado;
	@Getter @Setter
	private String fechaAlta;
	@Getter @Setter
	private String fechaVTO;
	@Getter @Setter
	private String motivo;
	@Getter @Setter
	private String empleado;
	@Getter	@Setter
	private String centro;
	@Getter	@Setter
	private String importe;
}
