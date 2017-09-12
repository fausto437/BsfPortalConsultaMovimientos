package mx.gob.bansefi.dto.clientServices.response.resCatalogosVariosPM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by AppWIns on 08/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class RegistrosCatalogoDTO {

    @Getter @Setter
    private ArrayList<CatalogoDTO> CATALOGO;

}
