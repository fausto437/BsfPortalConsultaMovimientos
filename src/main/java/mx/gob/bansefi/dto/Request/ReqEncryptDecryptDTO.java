package mx.gob.bansefi.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 15/05/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReqEncryptDecryptDTO {

    @Getter @Setter
    private String text;

}
