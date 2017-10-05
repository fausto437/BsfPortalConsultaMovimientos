package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 23/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class AnotacionesDTO {

    @Getter
    @Setter
    String noanotacion;
    @Getter
    @Setter
    String descr;
    @Getter
    @Setter
    String tipoanotacion;
    @Getter
    @Setter
    String fechactivacion;
    @Getter
    @Setter
    String prioridad;
}
