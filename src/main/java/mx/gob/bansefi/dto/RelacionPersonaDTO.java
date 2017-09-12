package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class RelacionPersonaDTO {

	@Getter @Setter
    public Long ID_INTERNO_PE_1;
    @Getter @Setter
    public Long ID_INTERNO_PE_2;
    @Getter @Setter
    public String TIPO_RELACION;
    @Getter @Setter
    public String NOMBRE;
    @Getter @Setter
    public String PORCENTAJE;
}
