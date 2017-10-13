package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO{

	@Getter @Setter
	private String oficina;
	@Getter @Setter
	private String plaza;
	@Getter @Setter
	private String numacuerdo;
	@Getter @Setter
	private String titular;
	@Getter @Setter
	private String saldo;
	@Getter @Setter
	private String hasta;
	@Getter @Setter
	private String desde;
	
}