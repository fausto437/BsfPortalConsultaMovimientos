package mx.gob.bansefi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Created by AppWIns on 28/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaDatosPersonaMoralDTO extends ResGralDTO {
    @Getter @Setter
    private String ESTATUS;
    @Getter @Setter
    private String CODIGO;
    @Getter @Setter
    private String MENSAJE;
    @Getter @Setter
    private DatosAdicionalesDTO datosAdicionales;

}
