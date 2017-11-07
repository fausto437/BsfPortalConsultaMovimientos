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
    @Getter	@Setter
    String anotacion;
    @Getter	@Setter
    String descripcion;
    @Getter	@Setter
    String codAnt;
    @Getter	@Setter
    String fechaActivacion;
    @Getter	@Setter
    String prioridad;
    @Getter	@Setter
    String codSubAnt;
}
