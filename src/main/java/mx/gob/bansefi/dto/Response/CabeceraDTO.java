package mx.gob.bansefi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class CabeceraDTO extends ResGralDTO{

	@Getter @Setter
	private String mensaje;
	@Getter @Setter
	private String status;
	@Getter @Setter
	private String errores;
}