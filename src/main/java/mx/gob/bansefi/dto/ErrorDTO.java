package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO{

	@Getter @Setter
	private String codigo;
	@Getter @Setter
	private String mensaje;
}