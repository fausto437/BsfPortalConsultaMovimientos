package mx.gob.bansefi.dto.bsfOperador;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 03/08/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class BsfOperadorGenericDTO {

    @Getter @Setter
    private String ENTIDAD;
    @Getter @Setter
    private String CENTRO;
    @Getter @Setter
    private String TERMINAL;
    @Getter @Setter
    private String USERTCB;
    @Getter @Setter
    private String SESSIONID;
    @Getter @Setter
    private String PASSTCB;
    @Getter @Setter
    private String NOMBREEMPLEADO;
    @Getter @Setter
    private String NOMBRECENTRO;
    @Getter @Setter
    private TransportDTO TRANSPORT;

}
