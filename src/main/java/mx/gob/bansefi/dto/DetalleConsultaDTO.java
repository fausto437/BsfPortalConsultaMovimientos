package mx.gob.bansefi.dto;

import java.util.ArrayList;
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
	//DATOS DE APUNTES
	@Getter @Setter
	private String tipoOperacion;
	@Getter @Setter
	private String concepto;
	@Getter @Setter
	private String conceptoCorto;
	@Getter	@Setter
	private String idOrigen;
	//MAS DATOS DEL APUNTE
	@Getter	@Setter
	private String originadoPor;
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
	//DATOS DE AUDITORIA
	@Getter	@Setter
	private String terminal;
	@Getter	@Setter
	private String fechaOperacion;
	@Getter	@Setter
	private String codAuditoria;
	@Getter	@Setter
	private String autorizador;
	@Getter	@Setter
	private String horaOperacion;
	@Getter	@Setter
	private String nombreAutorizador;
	//MAS AUDITORIA
	@Getter	@Setter
	private String codCentro;
	@Getter	@Setter
	private String descCentro;
	@Getter	@Setter
	private String codCentroActual;
	@Getter	@Setter
	private String descCentroActual;
	@Getter	@Setter
	private String nombEnt;
	@Getter	@Setter
	private String entidad;
	@Getter	@Setter
	private String descEntidad;
	@Getter	@Setter
	private String codEmpleadoAutorizador;
	@Getter	@Setter
	private String descEmpleadoAutorizador;
	@Getter	@Setter
	private String codTransaccion;
	@Getter	@Setter
	private String descTransaccion;
	//DATOS DE LIQUIDACION
	@Getter	@Setter
	private String fechaLiquidacion;
	@Getter	@Setter
	private String fechaDesde;
	@Getter	@Setter
	private String fechaHasta;
	@Getter	@Setter
	private String fechaUltimoCobro;
	@Getter	@Setter
	private String codigoOperacionLiquidacion;
	@Getter	@Setter
	private String importeTotal;
	@Getter	@Setter
	private String importePendiente;
	@Getter	@Setter
	private ArrayList<LiquidacionDTO> liquidaciones;
	//DATOS DE EMISION
	@Getter	@Setter
	private String tipoTalonario;
	@Getter	@Setter
	private String numeroTalonario;
	@Getter	@Setter
	private String nombre;
	@Getter	@Setter
	private String reqNumeroCheque;
	@Getter	@Setter
	private String numeroCheque;
	@Getter	@Setter
	private String codigoCaja;
	@Getter	@Setter
	private String conforma;
	@Getter	@Setter
	private String disposicion;
	@Getter	@Setter
	private String pagoCheque;
	@Getter	@Setter
	private String pagoPendiente;
	@Getter	@Setter
	private String estado;
	@Getter	@Setter
	private String situacionPago;
}
