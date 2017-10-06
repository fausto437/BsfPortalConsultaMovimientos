package mx.gob.bansefi.dto.Response.Documentos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by app on 01/08/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResAltaDocumentoTCB {
	@Setter @Getter
    private String STATUS;
    @Setter @Getter
    private String ID_INTERNO_DC;
    @Setter @Getter
    private String ERROR_DESC;
}
