package mx.gob.bansefi.dto.bsfOperador;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 4/28/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class TransportDTO {
	@Getter	@Setter
	private String ACTIONBACK;
	@Getter	@Setter
	private String TITULO;
	@Getter	@Setter
	private String TIPODOCUMENTO;
	@Getter	@Setter
	private String TARGET;
	@Getter	@Setter
	private DatosTransportDTO IDINTERNOPE;
	@Getter	@Setter
	private DatosTransportDTO MenuDinamico;
}
