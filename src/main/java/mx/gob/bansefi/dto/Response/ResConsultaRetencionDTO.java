package mx.gob.bansefi.dto.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaRetencionDTO extends CabeceraDTO{

	@Getter @Setter
	private List<RetencionesDTO> retenciones;
}