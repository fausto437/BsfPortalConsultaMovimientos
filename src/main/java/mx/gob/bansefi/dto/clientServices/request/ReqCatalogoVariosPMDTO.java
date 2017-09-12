package mx.gob.bansefi.dto.clientServices.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 08/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReqCatalogoVariosPMDTO {

    @Getter @Setter
    private String CodAplicacion;
    @Getter @Setter
    private String CodSubAplicacion;

}
