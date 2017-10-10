package mx.gob.bansefi.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 20/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReqConsultaEmisionDTO extends ReqGralDTO{

    @Getter @Setter
    public String codigoCaja;
    @Getter @Setter
    public String numCheque;
    @Getter @Setter
    public String codSeguridad;
    @Getter @Setter
    public String acuerdo;

}
