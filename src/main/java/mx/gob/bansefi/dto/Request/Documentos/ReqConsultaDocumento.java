package mx.gob.bansefi.dto.Request.Documentos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by app on 01/08/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReqConsultaDocumento {
    @Setter @Getter
    private String tipo_documento;
    @Setter @Getter
    private int id_interno_pe;
}
