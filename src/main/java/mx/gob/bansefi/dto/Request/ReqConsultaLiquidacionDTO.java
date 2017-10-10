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
public class ReqConsultaLiquidacionDTO extends ReqGralDTO{

    @Getter @Setter
    public String acuerdo;
    @Getter @Setter
    public String detalle;
    @Getter @Setter
    public String fechaLiquidacion;
    @Getter @Setter
    public String liqOpcion;

}
