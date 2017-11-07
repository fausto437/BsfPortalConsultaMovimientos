package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 06/10/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class LiquidacionDTO {
    @Getter	@Setter
    String importeFacturado;
    @Getter	@Setter
    String importePendiente;
    @Getter	@Setter
    String importeCondonado;
    @Getter	@Setter
    String importeAjustado;
    @Getter	@Setter
    String codigoOrigen;
}
