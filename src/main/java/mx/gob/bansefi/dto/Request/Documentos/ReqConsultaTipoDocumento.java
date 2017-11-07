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
public class ReqConsultaTipoDocumento {
    @Setter @Getter
    private String idIdentificacion;
}
