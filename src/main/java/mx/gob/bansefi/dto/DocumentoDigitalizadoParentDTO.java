package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.Response.Documentos.ResConsultaDocumento;

@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDigitalizadoParentDTO{
	@Setter @Getter
    private DocumentoDigitalizadoDTO DocumentoDigitalizado;
}