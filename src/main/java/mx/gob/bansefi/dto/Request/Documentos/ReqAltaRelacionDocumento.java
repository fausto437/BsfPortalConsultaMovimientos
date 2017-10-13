package mx.gob.bansefi.dto.Request.Documentos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.AltaDocumentoTCBDTO;

/**
 * Created by app on 06/10/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReqAltaRelacionDocumento {
    @Setter @Getter
    private String IdDocumento;
    @Setter @Getter
    private String IdTipoOperacion;
    @Setter @Getter
    private String IdOperacion;
}
