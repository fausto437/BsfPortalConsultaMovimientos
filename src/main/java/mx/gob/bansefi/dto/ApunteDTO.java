package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class ApunteDTO{

	@Getter @Setter
	private String mensaje;
	@Getter @Setter
	private String status;
	@Getter @Setter
	private String errores;
}