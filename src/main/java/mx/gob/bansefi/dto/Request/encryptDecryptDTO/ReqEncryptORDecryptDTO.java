package mx.gob.bansefi.dto.Request.encryptDecryptDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWhere on 04/10/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReqEncryptORDecryptDTO {
	@Setter @Getter
    private String text;
}
