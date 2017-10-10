package mx.gob.bansefi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
public class AuditoriaDTO{
	@Getter @Setter
	private String terminal;
	@Getter @Setter
	private String fechaContable;
	@Getter @Setter
	private String fechaOperacion;
	@Getter @Setter
	private String codigo;
	@Getter @Setter
	private String centro;
	@Getter @Setter
	private String empleado;
	@Getter	@Setter
	private String autorizador;
	@Getter	@Setter
	private String estado;
	@Getter	@Setter
	private String horaOperacion;
}