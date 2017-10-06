package mx.gob.bansefi.dto.Response.Documentos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.ObjTipoIdDTO;

/**
 * Created by app on 01/08/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaTipoDocumento {
    @Setter @Getter
    private ObjTipoIdDTO IdIdentificacion;
}
