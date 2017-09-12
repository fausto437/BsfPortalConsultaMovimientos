package mx.gob.bansefi.dto.Modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class DocumentosMODEL {

	@Setter @Getter
	private String bsfOperador;
	@Setter @Getter
	private String bsfOperadorDTO;
	@Setter @Getter
	private String idIntPe;
	@Setter @Getter
	private String txtTipoCedula;
	@Setter @Getter
	private String titulo;
	@Setter @Getter
	private String identificacionPM;

}
