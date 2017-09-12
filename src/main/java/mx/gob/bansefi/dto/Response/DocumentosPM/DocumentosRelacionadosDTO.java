package mx.gob.bansefi.dto.Response.DocumentosPM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by app on 01/08/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class DocumentosRelacionadosDTO {

    @Setter
    @Getter
    private String fecha_ENT_DC;
    @Setter
    @Getter
    private String fecha_REV_DC;
    @Setter
    @Getter
    private String id_INTERNO_DC;
    @Setter
    @Getter
    private String txt_DC;
    @Setter
    @Getter
    private String cod_DOC;
    @Setter
    @Getter
    private String ind_VIGEN_DC;
    @Setter
    @Getter
    private String cod_ECV_DOC_DC;
    @Setter
    @Getter
    private String fecha_EMISION_DC;
    @Setter
    @Getter
    private String fecha_CDCDAD_DC;
}
