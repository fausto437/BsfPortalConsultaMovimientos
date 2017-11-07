package mx.gob.bansefi.dto.Response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.CabeceraDTO;
import mx.gob.bansefi.dto.GralAnotacionDTO;
import mx.gob.bansefi.dto.GralApunteDTO;
import mx.gob.bansefi.dto.LiquidacionDTO;

@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaLiquidacionDTO extends CabeceraDTO{
	@Getter @Setter
	private String fechaLiquidacion;
	@Getter @Setter
	private String acuerdo;
	@Getter @Setter
	private String fechaDesde;
	@Getter @Setter
	private String fechaHasta;
	@Getter @Setter
	private String situacion;
	@Getter @Setter
	private String fechaUltimoCobro;
	@Getter @Setter
	private String codigoOperacionLiquidacion;
	@Getter @Setter
	private String moneda;
	@Getter @Setter
	private String importeTotal;
	@Getter @Setter
	private String importePendiente;
	@Getter @Setter
	private ArrayList<LiquidacionDTO> liquidaciones;
}