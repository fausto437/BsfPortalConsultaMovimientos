package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.Response.ResGralDTO;




@AllArgsConstructor
@NoArgsConstructor
public class RetencionesDTO extends ResGralDTO{

	@Getter @Setter
	private String tipo;
	@Getter @Setter
	private String estado;
	@Getter @Setter
	private String fechaAlta;
	@Getter @Setter
	private String fechaVTO;
	@Getter @Setter
	private String concepto;
	@Getter @Setter
	private String empleado;
	@Getter @Setter
	private String origen;
	@Getter @Setter
	private String importe;
}