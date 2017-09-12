package mx.gob.bansefi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 4/28/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class EncriptarResponseDTO {
    
	@Getter     @Setter
    private String codRet;
    
	@Getter     @Setter
    private String error;
	
    @Getter     @Setter
    private String respuesta;
}
