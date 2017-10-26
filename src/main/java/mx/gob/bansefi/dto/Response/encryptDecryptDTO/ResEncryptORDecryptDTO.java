package mx.gob.bansefi.dto.Response.encryptDecryptDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 04/10/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ResEncryptORDecryptDTO {
	@Getter @Setter
    private Integer codRet;
    @Getter @Setter
    private String error;
    @Getter @Setter
    private String respuesta;
}
