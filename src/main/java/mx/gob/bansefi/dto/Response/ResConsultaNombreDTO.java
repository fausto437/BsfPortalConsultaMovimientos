package mx.gob.bansefi.dto.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaNombreDTO{

	@Getter @Setter
	private CabeceraDTO cabecera;
	@Getter @Setter
	private String nombre;
}