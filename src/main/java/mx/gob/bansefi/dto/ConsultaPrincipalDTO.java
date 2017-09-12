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
	private String numCuenta;
	@Getter @Setter
	private String cliente;
	//DATOS DE ANOTACIONES
	@Getter @Setter
	private List<GralMovimientoDTO> acuerdos;
}
