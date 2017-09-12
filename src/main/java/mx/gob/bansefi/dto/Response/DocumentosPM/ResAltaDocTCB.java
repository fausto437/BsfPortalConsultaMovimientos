package mx.gob.bansefi.dto.Response.DocumentosPM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.Response.ResGralDTO;

/**
 * Created by app on 02/08/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResAltaDocTCB extends ResGralDTO{
    @Setter @Getter
    private String id_INTERNO_DC;
}
