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
public class ReqConsultaDocumentosTCB {
    @Setter @Getter
    private String usuario;
    @Setter @Getter
    private String password;
    @Setter @Getter
    private String entidad;
    @Setter @Getter
    private String idInternoPe;
    @Setter @Getter
    private String terminal;
}
