package mx.gob.bansefi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 10/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResGralDTO {

    @Getter @Setter
    private String estatus;
    @Getter @Setter
    private String codigo;
    @Getter @Setter
    private String mensaje;

    public void heredarDatos(ResGralDTO resGralDTO){
        this.estatus = resGralDTO.getEstatus();
        this.codigo = resGralDTO.getCodigo();
        this.mensaje = resGralDTO.getMensaje();
    }

}
