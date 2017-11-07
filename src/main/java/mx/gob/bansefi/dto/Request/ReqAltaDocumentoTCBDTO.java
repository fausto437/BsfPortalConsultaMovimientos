package mx.gob.bansefi.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.AltaDocumentoTCBDTO;

@AllArgsConstructor
@NoArgsConstructor
public class ReqAltaDocumentoTCBDTO {
	@Setter @Getter
    private AltaDocumentoTCBDTO AltaDocumentosPersona;
	
}