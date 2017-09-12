package mx.gob.bansefi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 17/04/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResEncryptDecryptDTO {

    @Getter @Setter
    private Integer codRet;
    @Getter @Setter
    private String error;
    @Getter @Setter
    private String respuesta;

}
