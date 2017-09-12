package mx.gob.bansefi.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class ReqAltaRelacionDocumentoDTO extends ReqGralDTO {
	
	@Setter @Getter
	private String idInternoPe;
	
	@Setter @Getter
	private String codDoc;
	
	@Setter @Getter
	private String centro;
	
	@Setter @Getter
	private String descDoc;
	
}