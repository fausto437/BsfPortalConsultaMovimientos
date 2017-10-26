package mx.gob.bansefi.dto.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.CabeceraDTO;
import mx.gob.bansefi.dto.NombreDTO;




@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaNombreDTO{
	@Getter @Setter
	private CabeceraDTO cabecera;
	@Getter @Setter
	private String nombre;
	@Getter @Setter
	private String idInternoPe;
}