package mx.gob.bansefi.dto.clientServices.response.resCatalogosVariosPM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 08/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResCatalogosVariosPMDTO {

    @Getter @Setter
    private String STATUS;
    @Getter @Setter
    private RegistrosCatalogoDTO CATALAGOS;
    @Getter @Setter
    private String ERROR_DESC;

}
