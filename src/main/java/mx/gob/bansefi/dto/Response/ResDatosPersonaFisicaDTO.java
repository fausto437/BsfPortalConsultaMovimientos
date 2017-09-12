package mx.gob.bansefi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class ResDatosPersonaFisicaDTO extends ResGralDTO{

	@Getter @Setter
	private String id_INTERNO_PE;
	@Getter @Setter
	private String cod_ID_EXT_PERS;
	@Getter @Setter
	private String id_EXT_PE;
	@Getter @Setter
	private String nomb_50;
	@Getter @Setter
	private String fec_NCTO_CONST_PE;
	@Getter @Setter
	private String num_DIR_PRAL;
	@Getter @Setter
	private String id_RFC;
}