package mx.gob.bansefi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class CabeceraDTO{

	@Getter @Setter
	private String mensaje;
	@Getter @Setter
	private String status;
	@Getter @Setter
	private List<ErrorDTO> errores;
}