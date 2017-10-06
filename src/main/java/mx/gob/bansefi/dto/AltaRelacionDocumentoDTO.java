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
public class AltaRelacionDocumentoDTO {
    @Getter	@Setter
    String idInternoPe;
    @Getter	@Setter
    String codTipoDoc;
    @Getter	@Setter
    String descDoc;
    @Getter	@Setter
    String fechaCaducidadDoc;
    @Getter	@Setter
    String entidad;
    @Getter	@Setter
    String centro;
    @Getter	@Setter
    String terminal;
}
