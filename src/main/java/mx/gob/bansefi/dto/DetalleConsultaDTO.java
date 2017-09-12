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
public class DetalleConsultaDTO {
	@Getter @Setter
	private String tipoDetalle;
	@Getter @Setter
	private String titulo;
	//DATOS DE ANOTACIONES
	@Getter @Setter
	private String tipo;
	@Getter @Setter
	private String subcodigo;
	@Getter @Setter
	private String area;
	@Getter @Setter
	private String fecha;
	@Getter	@Setter
	private String descripcion;
	@Getter	@Setter
	private String codEmpleado;
	@Getter	@Setter
	private String nombreEmpleado;
	@Getter	@Setter
	private String nota;
}
