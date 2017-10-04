package mx.gob.bansefi.dto.Response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.CabeceraDTO;
import mx.gob.bansefi.dto.GralApunteDTO;

@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaApunteDetalleDTO{

	@Getter @Setter
	private CabeceraDTO cabecera;
	@Getter @Setter
	private String tipoOperacion;
	@Getter @Setter
	private String tipoDescripcion;
	@Getter @Setter
	private String nombre;
	@Getter @Setter
	private String producto;
	@Getter @Setter
	private String numero;
	@Getter @Setter
	private String fechaContable;
	@Getter @Setter
	private String fechaValor;
	@Getter @Setter
	private String importe;
	@Getter @Setter
	private String estado;
	@Getter @Setter
	private String moneda;
	@Getter @Setter
	private String indicador1;
	@Getter @Setter
	private String indicador2;
	@Getter @Setter
	private String indicador3;
	@Getter @Setter
	private String indicador4;
	@Getter @Setter
	private String indicador5;
	@Getter @Setter
	private String indicador6;
	@Getter @Setter
	private String indicador7;
	@Getter @Setter
	private String indicador8;
	@Getter @Setter
	private String indicador9;
	@Getter @Setter
	private String indicador10;
	@Getter @Setter
	private String concepto;
}