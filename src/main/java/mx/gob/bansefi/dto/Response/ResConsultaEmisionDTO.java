package mx.gob.bansefi.dto.Response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.dto.CabeceraDTO;
import mx.gob.bansefi.dto.GralApunteDTO;

@AllArgsConstructor
@NoArgsConstructor
public class ResConsultaEmisionDTO extends CabeceraDTO{
	@Getter @Setter
	private String tipoTalonario;
	@Getter @Setter
	private String numeroTalonario;
	@Getter @Setter
	private String nombre;
	@Getter @Setter
	private String numeroCheque;
	@Getter @Setter
	private String codigoCaja;
	@Getter @Setter
	private String conforma;
	@Getter @Setter
	private String disposicion;
	@Getter @Setter
	private String pagoCheque;
	@Getter @Setter
	private String pagoPendiente;
	@Getter @Setter
	private String centro;
	@Getter @Setter
	private String acuerdo;
	@Getter @Setter
	private String estado;
	@Getter @Setter
	private String situacionPago;
}