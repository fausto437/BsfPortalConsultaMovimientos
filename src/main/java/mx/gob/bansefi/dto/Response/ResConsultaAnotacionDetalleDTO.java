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
public class ResConsultaAnotacionDetalleDTO extends CabeceraDTO{
	@Getter @Setter
	private String idEmpleado;
	@Getter @Setter
	private String fechaPlanificacion;
	@Getter @Setter
	private String fechaUltimaModificacion;
}