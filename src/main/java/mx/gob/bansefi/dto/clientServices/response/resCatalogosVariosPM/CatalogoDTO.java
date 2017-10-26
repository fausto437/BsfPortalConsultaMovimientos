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
public class CatalogoDTO {

    @Getter @Setter
    private String CLAVE;
    @Getter @Setter
    private String DESCRIPCION;

}
