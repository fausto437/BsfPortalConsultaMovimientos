package mx.gob.bansefi.dto.Response.DocumentosPM;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by app on 01/08/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaDocumentosTCB {
    @Setter @Getter
    private String number_OF_RECORDS;
    @Setter @Getter
    private String mensaje;
    @Setter @Getter
    private String codigo;
    @Setter @Getter
    private String estatus;
    @Setter @Getter
    private List<DocumentosRelacionadosDTO> listaDocumentosRelacionados;
}
