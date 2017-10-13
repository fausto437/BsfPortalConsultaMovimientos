package mx.gob.bansefi.dto.Response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.CabeceraDTO;
import mx.gob.bansefi.dto.GralApunteDTO;

@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaApuntesDTO{

	@Getter @Setter
	private CabeceraDTO cabecera;
	@Getter @Setter
	private String cantidad;
	@Getter @Setter
	private String fechaOperacion;
	@Getter @Setter
	private String horaOperacion;
	@Getter @Setter
	private ArrayList<GralApunteDTO> lista;
}