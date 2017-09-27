package mx.gob.bansefi.dto.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.CabeceraDTO;
import mx.gob.bansefi.dto.GralRetencionDTO;
import mx.gob.bansefi.dto.RetencionesDTO;




@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaRetencionDTO{
	@Getter @Setter
	private CabeceraDTO cabecera;
	@Getter @Setter
	private List<GralRetencionDTO> retenciones;
}