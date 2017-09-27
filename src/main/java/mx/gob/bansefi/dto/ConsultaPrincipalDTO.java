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
public class ConsultaPrincipalDTO {
	@Getter @Setter
	private String numAcuerdo;
	@Getter @Setter
	private String cliente;
	@Getter @Setter
	private List<ApunteDTO> apuntes;
	@Getter @Setter
	private List<GralBloqueoDTO> bloqueos;
	@Getter @Setter
	private List<GralRetencionDTO> retenciones;
}
