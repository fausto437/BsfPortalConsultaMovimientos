package mx.gob.bansefi.dto.Response;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.AnotacionesDTO;
import mx.gob.bansefi.dto.CabeceraDTO;

/**
 * Created by AppWIns on 10/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaAnotacionesDTO extends CabeceraDTO {

    @Getter @Setter
    private String titular;
    @Getter @Setter
    private String txtIdPe;
    @Getter @Setter
    private ArrayList<AnotacionesDTO> anotaciones;

}
