package mx.gob.bansefi.dto.bsfOperador.transports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.bsfOperador.DatosAnterioresGenericDTO;
import mx.gob.bansefi.dto.bsfOperador.datosAnteriores.DatosAnterioresModRelacionesDTO;

/**
 * Created by AppWIns on 03/08/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class TransportModRelacionesDTO {

    @Getter @Setter
    private String URLACTION;
    @Getter @Setter
    private String MenuDinamico;
    @Getter @Setter
    private String TITULO;
    @Getter @Setter
    private String IDINTERNOPE;
    @Getter @Setter
    private String RAZONSOCIAL;
    @Getter @Setter
    private DatosAnterioresModRelacionesDTO DATOSANTERIORES;

}
