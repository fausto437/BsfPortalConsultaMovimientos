package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 4/28/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class GralAnotacionDTO {
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
    @Getter	@Setter
    String tipoAnotacion;
    @Getter	@Setter
    String textSubAnt;
    @Getter	@Setter
    String area;
}
