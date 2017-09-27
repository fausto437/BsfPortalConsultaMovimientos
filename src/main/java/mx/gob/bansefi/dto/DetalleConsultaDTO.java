package mx.gob.bansefi.dto;

import java.util.List;
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
	//DATOS DE MOVIMIENTO
	@Getter	@Setter
	private String titular;
	@Getter	@Setter
	private String prodVendible;
	@Getter	@Setter
	private String tipoCuenta;
	@Getter	@Setter
	private String idTipoCuenta;
	@Getter	@Setter
	private String numero;
	@Getter	@Setter
	private String fechaContable;
	@Getter	@Setter
	private String fechaValor;
	@Getter	@Setter
	private String importe;
	@Getter	@Setter
	private String estatus;
	@Getter	@Setter
	private String moneda;
	@Getter	@Setter
	private List<SituacionApunteDTO> sitApunte;
	//DATOS DE BLOQUEO
	@Getter	@Setter
	private String numAcuerdo;
	@Getter	@Setter
	private String centro;
	@Getter	@Setter
	private String idTipoBloqueo;
	@Getter	@Setter
	private String tipoBloqueo;
	@Getter	@Setter
	private String fechaAlta;
	@Getter	@Setter
	private String fechaVto;
	@Getter	@Setter
	private String motivo;
	@Getter	@Setter
	private String situacion;
	//DATOS DE RETENCION
	@Getter	@Setter
	private String idTipoRetencion;
	@Getter	@Setter
	private String tipoRetencion;
	@Getter	@Setter
	private String codRetencion;
	@Getter	@Setter
	private String origen;
}
