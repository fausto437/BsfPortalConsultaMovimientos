package mx.gob.bansefi.dto.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.CabeceraDTO;
import mx.gob.bansefi.dto.RetencionesDTO;




@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaRetencionDTO extends CabeceraDTO{

	@Getter @Setter
	private List<RetencionesDTO> retenciones;
}