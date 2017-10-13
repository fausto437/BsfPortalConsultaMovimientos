package mx.gob.bansefi.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AppWIns on 10/07/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
public class ReqGralDTO {

	@Getter	@Setter
	private String usuario;
	@Getter	@Setter
	private String password;
	@Getter	@Setter
	private String entidad;
	@Getter	@Setter
	private String terminal;
	@Getter	@Setter
	private String sucursal;

	public void heredarDatos(ReqGralDTO reqGralDTO) {
		this.usuario = reqGralDTO.getUsuario();
		this.password = reqGralDTO.getPassword();
		this.entidad = reqGralDTO.getEntidad();
		this.terminal = reqGralDTO.getTerminal();
		this.sucursal = reqGralDTO.getSucursal();
	}
}
